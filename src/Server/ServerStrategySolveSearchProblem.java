package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class ServerStrategySolveSearchProblem implements IServerStrategy
{

    private ArrayList<byte[]> MazesSolved = new ArrayList<byte[]>();
    private HashMap<byte[], Solution> Solutions = new HashMap<byte[], Solution>();
    private String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private FileOutputStream fos;
    private ISearchingAlgorithm bfs = new BestFirstSearch();
    private static Semaphore mutex = new Semaphore(1);

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException, InterruptedException {

        //generating a maze
        //converting it into a searchable maze and then solves it with best first search
        try
        {
            Solution solved;
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Maze maze = (Maze)objectInputStream.readObject();
            byte[] mazeArr = maze.toByteArray();

            mutex.acquire();
            byte[] returned = isExist(this.MazesSolved,mazeArr);
            mutex.release();


            if( returned != null )
            {
                solved = Solutions.get(returned);
            }
            else
            {
                ISearchable searchablemaze = new SearchableMaze(maze);
                solved = bfs.solve(searchablemaze);
                MazesSolved.add(mazeArr);
                Solutions.put(mazeArr,solved);
            }


            objectOutputStream.writeObject(solved);
            objectOutputStream.flush();
        }


        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private byte[] isExist(ArrayList<byte[]> maze , byte[] tocheck)
    {

        for(int i=0; i < maze.size(); i++)
        {
            if(Arrays.equals(tocheck,maze.get(i)))
            {
                return maze.get(i);
            }
        }
        return null;
    }
}
