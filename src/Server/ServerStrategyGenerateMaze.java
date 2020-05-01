package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy

{
    private MyCompressorOutputStream compressorOutputStream;
    private InputStream in;/*ToDelete*/

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        try
        {
            compressorOutputStream = new MyCompressorOutputStream(outputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.compressorOutputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);



            int [] maze_size = new int [2];
            maze_size = (int[]) (objectInputStream.readObject());

            AMazeGenerator mazegen = new MyMazeGenerator();
            Maze maze = mazegen.generate(maze_size[0],maze_size[1]);

            /////////////
            System.out.println("server: ");
            maze.print();
            System.out.println("------------");
            /////////////

            //compressorOutputStream.write(maze.toByteArray());
            objectOutputStream.writeObject(maze.toByteArray());
            objectOutputStream.flush();
            compressorOutputStream.close();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
