package algorithms.mazeGenerators;

/**
 * Interface for all kings of Maze generating algorithms
 */
public interface IMazeGenerator {

    Maze generate(int rows, int cols); // generates the maze
    long measureAlgorithmTimeMillis(int rows, int cols); // total maze creation time

}
