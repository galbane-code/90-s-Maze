package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int cols) {


        createPositioinArray(rows, cols); // calls the function that creates the positionArr in the base class

        // intializes the postions in the maze to zero in the EmptyMaze

        int [][] data = new int[rows*2-1][cols*2-1];
        for(int i = 0; i < rows*2-1; i++)
        {
            for(int j = 0; j < cols*2-1; j++)
            {
                data[i][j] = 0;

            }
        }


        Position[] entryExitArr = new Position[2]; // an array of the start and the end positions
        data = intMazeRandom(rows, cols, data, entryExitArr); // generates the final maze data with the random entry and exit


        Maze maze = new Maze(data, entryExitArr[0], entryExitArr[1]);  // maze constructor call
        maze.setPositionMatrix(positionArr); // set the position into the maze to be used in the search and solution of Maze

        return maze;
    }
}
