package test;

import IO.MyCompressorOutputStream;
//import IO.MyDecompressorInputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
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

        ////////////////////
        Maze loadedMaze = new Maze(savedMazeBytes);

        /*SearchableMaze searchableMaze = new SearchableMaze(loadedMaze);
        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BreadthFirstSearch());*/
        ///////////////////
        System.out.println("--------");
        loadedMaze.print();
        boolean areMazesEquals = Arrays.equals(loadedMaze.toByteArray(),maze.toByteArray());
        System.out.println(String.format("Mazes equal: %s",areMazesEquals)); //maze should be equal to loadedMaze
    }

    private static void solveProblem(ISearchable domain, ASearchingAlgorithm searcher)
    {
        Solution solution = searcher.solve(domain);

        System.out.println(String.format("'%s', algorithm - nodes evaluated: %s",
                searcher.getName(), searcher.getNumberOFNodesEvaluated()));

        System.out.println("Solution path:");
        ArrayList<AState> solutionPath = solution.getSolutionPath();
        for(int i = 0; i < solutionPath.size(); i++)
        {
            System.out.println(String.format("%s.%s", i, solutionPath.get(i)));
        }
    }
}