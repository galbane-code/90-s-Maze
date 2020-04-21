package algorithms.mazeGenerators;
import java.util.Random;
public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int cols) {


        // randomly selects walls in the maze
        Random randInt = new Random();
        int [][] data = new int[rows*2-1][cols*2-1];
        for(int i = 0; i < rows*2-1; i++)
        {
            for(int j = 0; j < cols*2-1; j++)
            {
                data[i][j] =  randInt.nextInt()% 2;
                if(data[i][j] == -1)
                    data[i][j] = 1;
            }
        }

        // sets the frame of the maze to zero

        for(int i = 0; i < 1; i++)
        {
            for(int j = 0; j < cols*2-1; j++)
            {
                data[i][j] =  0;
                data[rows*2-2][j] =  0;
            }
        }

        for(int i = 0; i < rows*2-1; i++)
        {
            for(int j = cols*2 - 2; j < cols*2-1; j++)
            {
                data[i][j] =  0;
                data[i][0] = 0;
            }
        }

        createPositioinArray(rows, cols); // calls the function that creates the positionArr in the base class

        Position[] entryExitArr = new Position[2]; // an array of the start and the end positions
        data = intMazeRandom(rows, cols, data, entryExitArr); // generates the final maze data with the random start and goal


        Maze maze = new Maze(data, entryExitArr[0], entryExitArr[1]); //maze constructor call
        maze.setPositionMatrix(positionArr); // sets the position into the maze to be use in the search and solved of Maze

        return maze;
    }

    @Override

    // Here we Override the method beacuse we create a path only on the frame unlike the other mazes

    public void createPositioinArray(int rows, int cols) {
        positionArr = new Position[rows][cols];


        for(int i = 0; i< rows; i++){
            for (int j = 0; j < cols; j++){
                positionArr[i][j] = new Position(i,j);
                positionArr[i][j].setRank(0);
                positionArr[i][j].setId(counter);
                counter++;

            }
        }

        for(int i = 0; i< cols - 1; i++)
        {
            positionArr[0][i].getNeighbors().add(positionArr[0][i+1]);
            positionArr[rows-1][i].getNeighbors().add(positionArr[rows-1][i+1]);

            positionArr[0][i+1].getNeighbors().add(positionArr[0][i]);
            positionArr[rows-1][i+1].getNeighbors().add(positionArr[rows-1][i]);
        }

        for(int i = 0; i < rows - 1; i++)
        {
            positionArr[i][0].getNeighbors().add(positionArr[i+1][0]);
            positionArr[i][cols-1].getNeighbors().add(positionArr[i+1][cols-1]);

            positionArr[i+1][0].getNeighbors().add(positionArr[i][0]);
            positionArr[i+1][cols-1].getNeighbors().add(positionArr[i][cols-1]);

        }

    }
}
