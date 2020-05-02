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
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;/*ToDelete*/

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        try
        {
            //compressorOutputStream = new MyCompressorOutputStream(outputStream);
            //ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.compressorOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);



            int [] maze_size;
            maze_size = (int[]) (objectInputStream.readObject());

            AMazeGenerator mazegen = new MyMazeGenerator();
            Maze maze = mazegen.generate(maze_size[0],maze_size[1]);

            /////////////@TODO: test section
            System.out.println("server: ");
            maze.print();
            System.out.println("------------");
            byte [] compressedArr;
            out = new ByteArrayOutputStream();
            compressorOutputStream = new MyCompressorOutputStream(out);
            compressorOutputStream.write(maze.toByteArray());
            compressedArr = out.toByteArray();
            /////////////
            objectOutputStream.writeObject(compressedArr);
            objectOutputStream.flush();
            compressorOutputStream.close();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
