package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy
{

    private ISearchingAlgorithm bfs = new BestFirstSearch();

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException, ClassNotFoundException {

        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Maze maze = (Maze)objectInputStream.readObject();
            ISearchable searchablemaze = new SearchableMaze(maze);
            Solution solved = bfs.solve(searchablemaze);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(solved);
            objectOutputStream.flush();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
