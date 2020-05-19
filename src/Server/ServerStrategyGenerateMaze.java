package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy
{
    public static String mazeGeneratorString;//config file data member
    private MyCompressorOutputStream compressorOutputStream;
    private ByteArrayOutputStream byteArrayOutputStreamout;

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        /**
         * generates a maze using an Input from the client, representing the maze dimensions.
         * the maze is compressed by an original compressing algorithm
         * the compressed maze is written to the client.
         */
        try
        {
            AMazeGenerator mazeGenerator = AMazeGenerator.generateType(mazeGeneratorString);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            int [] maze_size = (int[]) (objectInputStream.readObject());
            Maze maze = mazeGenerator.generate(maze_size[0],maze_size[1]);

            byte [] compressedArr = new byte[0];
            byteArrayOutputStreamout = new ByteArrayOutputStream();
            compressorOutputStream = new MyCompressorOutputStream(byteArrayOutputStreamout);
            compressorOutputStream.write(maze.toByteArray());
            while(compressedArr.length == 0)
            {
                compressedArr = byteArrayOutputStreamout.toByteArray();
            }
            objectOutputStream.writeObject(compressedArr);

            objectOutputStream.flush();
            byteArrayOutputStreamout.close();
            compressorOutputStream.close();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
