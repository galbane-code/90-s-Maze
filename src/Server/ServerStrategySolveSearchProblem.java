package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class ServerStrategySolveSearchProblem implements IServerStrategy
{

    private HashMap<byte[], Solution> SolutionsMap = new HashMap<byte[], Solution>();
    private ISearchingAlgorithm bfs = new BestFirstSearch();
    private static Semaphore mutex = new Semaphore(1);

    /**
    file creation for maze solutions
     */
    private String tempDirectoryPath = System.getProperty("java.io.tmpdir");
    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private File file;



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
            byte[] mazeByteArr = maze.toByteArray();

            mutex.acquire();
            byte[] returned = null;
            if(file != null)
            {
                readFromFile();
                ArrayList<byte[]> keysArrayList = new ArrayList<byte[]>(this.SolutionsMap.keySet());
                returned = isExist(keysArrayList, mazeByteArr);
            }
            mutex.release();


            if( returned != null )
            {
                solved = SolutionsMap.get(returned);
            }
            else
            {
                ISearchable searchablemaze = new SearchableMaze(maze);
                solved = bfs.solve(searchablemaze);
                SolutionsMap.put(mazeByteArr, solved);
                writeToFile();
            }


            objectOutputStream.writeObject(solved);
            objectOutputStream.flush();
        }


        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private byte[] isExist(ArrayList<byte[]> mazeSolutions , byte[] tocheck)
    {

        for(int i=0; i < mazeSolutions.size(); i++)
        {
            if(Arrays.equals(tocheck,mazeSolutions.get(i)))
            {
                return mazeSolutions.get(i);
            }
        }
        return null;
    }

    public void readFromFile() throws IOException, ClassNotFoundException {
        try
        {

            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            SolutionsMap = (HashMap<byte[], Solution>) ois.readObject();
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void writeToFile() throws IOException
    {
        try
        {
            file = File.createTempFile(tempDirectoryPath, null);
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(SolutionsMap);
            oos.flush();
            oos.close();
            fos.close();

        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
