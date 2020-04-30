package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

public class ServerStrategyGenerateMaze implements IServerStrategy

{
    private MyCompressorOutputStream compressorOutputStream;


    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            int [] maze_size = (int[]) (objectInputStream.readObject());

            AMazeGenerator mazegen = new MyMazeGenerator();
            Maze maze = mazegen.generate(maze_size[0],maze_size[1]);

            compressorOutputStream = new MyCompressorOutputStream(outputStream);
            compressorOutputStream.write(maze.toByteArray());
            compressorOutputStream.flush();
            compressorOutputStream.close();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
