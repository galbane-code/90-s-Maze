package test;

import IO.MyCompressorOutputStream;
//import IO.MyDecompressorInputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Aviadjo on 3/26/2017.
 */
public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        String mazeFileName = "savedMaze.maze";
        AMazeGenerator mazeGenerator = new MyMazeGenerator();
        Maze maze = mazeGenerator.generate(3, 3); //Generate new maze

        try {
            // save maze to a file
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            maze.print();

            //////////////TODO:: delete later (bottom section)
            /*System.out.println(maze.toByteArray().length);
            int [] intArr = new int [5];
            byte byteNum = (byte)(213);
            System.out.println(byteNum );
            int intNum = byteNum & 0xFF;
            System.out.println(intNum);
            intArr [0] = byteNum & 0xFF;
            System.out.println(intArr[0]);
            byte num = (byte) (Integer.parseInt("00111111", 2) );
            String gal = "10101";
            int numInt = num & 0xff;
            byte [] byteStr = gal.getBytes();
            byte [] copyArr = Arrays.copyOfRange(byteStr, 0, 2);
            String newStr = new String(copyArr);
            byte num2 = (byte) Integer.parseInt(newStr, 2);
            System.out.println(num2);*/
            /////////////////////////////

            out.write(maze.toByteArray());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte savedMazeBytes[] = new byte[0];
        try {
            //read maze from file
            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            savedMazeBytes = new byte[maze.toByteArray().length];
            in.read(savedMazeBytes);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Maze loadedMaze = new Maze(savedMazeBytes);
        System.out.println("--------");
        loadedMaze.print();
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals)); //maze should be equal to loadedMaze
    }
}