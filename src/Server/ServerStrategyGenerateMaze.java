package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy

{
    public static String AMazeGenerator;
    private MyCompressorOutputStream compressorOutputStream;
    private AMazeGenerator mazegen = algorithms.mazeGenerators.AMazeGenerator.GeneratertingType(AMazeGenerator);
    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;

    @Override
    public void handleClient(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        try
        {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);



            int [] maze_size;
            maze_size = (int[]) (objectInputStream.readObject());

            Maze maze = mazegen.generate(maze_size[0],maze_size[1]);

            byte [] compressedArr;
            out = new ByteArrayOutputStream();
            compressorOutputStream = new MyCompressorOutputStream(out);
            compressorOutputStream.write(maze.toByteArray());
            compressedArr = out.toByteArray();
            objectOutputStream.writeObject(compressedArr);
            objectOutputStream.flush();
            compressorOutputStream.close();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
