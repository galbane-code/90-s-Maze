package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerStrategyGenerateMaze implements IServerStrategy
{
    public static String mazeGeneratorString;//config file data member
    private MyCompressorOutputStream compressorOutputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte [] compressedArr = new byte[0];

    private Lock lock = new ReentrantLock();

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
            lock.lock();
            //AMazeGenerator mazeGenerator = AMazeGenerator.generateType(mazeGeneratorString);
            AMazeGenerator mazeGenerator = new MyMazeGenerator();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            byteArrayOutputStream = new ByteArrayOutputStream();
            compressorOutputStream = new MyCompressorOutputStream(byteArrayOutputStream);

            int [] maze_size = (int[]) (objectInputStream.readObject());
            Maze maze = mazeGenerator.generate(maze_size[0],maze_size[1]);


            byte[] bytesMaze = maze.toByteArray();
            compressorOutputStream.write(bytesMaze);
            compressorOutputStream.flush();

            compressedArr = byteArrayOutputStream.toByteArray();
            //Thread.sleep(1000);
            objectOutputStream.writeObject(compressedArr);

            objectOutputStream.flush();
            byteArrayOutputStream.close();
            compressorOutputStream.close();
            lock.unlock();
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
