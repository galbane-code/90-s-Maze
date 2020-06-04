package algorithms.mazeGenerators;

/**
 * The class creates an EmptyMaze, maze with value zero in each cell.
 */
public class EmptyMazeGenerator extends AMazeGenerator
{
    @Override
    /**
     * Main function of the maze generation
     */
    public Maze generate(int rows, int cols)
    {
        createPositioinMatrix(rows, cols); // calls the function that creates the Position matrix in the base class

        // intializes the cells in the maze to zero in the EmptyMaze
        int [][] data = new int[rows*2-1][cols*2-1];
        for(int i = 0; i < rows*2-1; i++)
        {
            for(int j = 0; j < cols*2-1; j++)
            {
                data[i][j] = 0;
            }
        }

        Position[] entryExitArr = new Position[2]; // an array of the entry and the exit positions
        data = intMazeRandom(rows, cols, data, entryExitArr); // generates the final maze data with the random entry and exit
        Maze maze = new Maze(data, entryExitArr[0], entryExitArr[1]);
        maze.setPositionMatrix(positionArr);

        return maze;
    }
}
