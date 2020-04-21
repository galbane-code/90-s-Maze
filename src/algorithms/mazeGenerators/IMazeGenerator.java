package algorithms.mazeGenerators;

public interface IMazeGenerator {

    public Maze generate(int rows, int cols); // generates the maze
    public long measureAlgorithmTimeMillis(int rows, int cols); // total maze creation time

}
