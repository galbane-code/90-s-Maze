package test;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.*;

import java.util.ArrayList;

public class RunSearchOnMaze {
    public static void main(String[] args) {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(7, 10);
        SearchableMaze searchableMaze = new SearchableMaze(maze);

        solveProblem(searchableMaze, new BreadthFirstSearch());
        solveProblem(searchableMaze, new DepthFirstSearch());
        solveProblem(searchableMaze, new BreadthFirstSearch());

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
