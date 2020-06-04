package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class AMazeGenerator implements IMazeGenerator{

    protected Position [][] positionArr;
    protected int counter = 0;

    /**
     * function for the config file
     * @param type
     * @return
     */
    public static AMazeGenerator generateType(String type)
    {
        AMazeGenerator toReturn;

        if (type.equals("EmptyMazeGenerator")) {
            toReturn = new EmptyMazeGenerator();
            return toReturn;
        } else if (type.equals("SimpleMazeGenerator")) {
            toReturn = new SimpleMazeGenerator();
            return toReturn;
        } else {
            toReturn = new MyMazeGenerator();
            return toReturn;
        }
    }

    @Override
    /**
     * function that measures the time of the maze generation
     */
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long start = System.currentTimeMillis();
        generate(rows, cols);
        long end = System.currentTimeMillis();

        return (end - start);
    }

    /**
     * Creates a position matrix, and assy to each Position its neighbors
     * @param rows
     * @param cols
     */
    public void createPositioinMatrix(int rows, int cols)
    {
        positionArr = new Position[rows][cols];

        for(int i = 0; i< rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                positionArr[i][j] = new Position(i,j);
                positionArr[i][j].setRank(0);
                positionArr[i][j].setId(counter);
                counter++;
            }
        }

        for(int i = 0; i< rows; i++){
            for (int j = 0; j < cols; j++){
                if(i == 0 && j == 0)//up left corner
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i+1][j]);
                    positionArr[i][j].getNeighbors().add(positionArr[i][j+1]);
                }

                else if(i == 0 && j == cols - 1)//up right corner
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i+1][j]);
                    positionArr[i][j].getNeighbors().add(positionArr[i][j-1]);
                }

                else if(i == rows - 1 && j == 0)//down left corner
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i-1][j]);
                    positionArr[i][j].getNeighbors().add(positionArr[i][j+1]);
                }

                else if(i == rows - 1 && j == cols - 1)// down right corner
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i-1][j]);
                    positionArr[i][j].getNeighbors().add(positionArr[i][j-1]);
                }

                else if(i == 0  && j != cols-1)// up side no corners
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i+1][j]);//down
                    positionArr[i][j].getNeighbors().add(positionArr[i][j-1]);//left
                    positionArr[i][j].getNeighbors().add(positionArr[i][j+1]);//right
                }

                else if(i == rows - 1 && j != 0 && j != cols-1)// down side no corners
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i-1][j]);//up
                    positionArr[i][j].getNeighbors().add(positionArr[i][j-1]);//left
                    positionArr[i][j].getNeighbors().add(positionArr[i][j+1]);//right
                }

                else if(j == 0 && i != rows-1)//left side no corners
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i-1][j]);//up
                    positionArr[i][j].getNeighbors().add(positionArr[i+1][j]);//down
                    positionArr[i][j].getNeighbors().add(positionArr[i][j+1]);//right
                }

                else if(j == cols - 1 && i != 0 && i != rows-1)//right side no corners
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i-1][j]);//up
                    positionArr[i][j].getNeighbors().add(positionArr[i+1][j]);//down
                    positionArr[i][j].getNeighbors().add(positionArr[i][j-1]);//left
                }

                else
                {
                    positionArr[i][j].getNeighbors().add(positionArr[i-1][j]);//up
                    positionArr[i][j].getNeighbors().add(positionArr[i+1][j]);//down
                    positionArr[i][j].getNeighbors().add(positionArr[i][j-1]);//left
                    positionArr[i][j].getNeighbors().add(positionArr[i][j+1]);//right
                }

            }

        }

    }

    /**
     * this function chooses random Entry and Goal of the Maze.
     * @param rows
     * @param cols
     * @param intMaze
     * @param entryExitArr
     * @return
     */
    public int [][] intMazeRandom(int rows, int cols, int [][] intMaze, Position [] entryExitArr)
    {
        Random rand = new Random();

        int rowIndex = 0;
        int colIndex = 0;
        int rowIndex2 = 0;
        int colIndex2 = 0;

        while((rowIndex == 0 && rowIndex2 == 0) || (rowIndex == rows - 1 && rowIndex2 == rows - 1) ||
                ((colIndex == 0 && colIndex2 == 0) || (colIndex == cols - 1 && colIndex2 == cols - 1)))
        {
            rowIndex = rand.nextInt(rows);
            colIndex = rand.nextInt(cols);
            rowIndex2 = rand.nextInt(rows);
            colIndex2 = rand.nextInt(cols);


            int[] list1 = {0, colIndex};
            int[] list2 = {rows - 1, colIndex2};
            int[] list3 = {rowIndex, 0};
            int[] list4 = {rowIndex2, cols - 1};

            ArrayList<int[]> entryExist = new ArrayList<>(Arrays.asList(list1, list2, list3, list4));
            int possibleEntry = rand.nextInt(4);//random start
            int[] entryInt = entryExist.remove(possibleEntry);
            int possibleExit = rand.nextInt(3);//random exit, different sides
            int[] exitInt = entryExist.remove(possibleExit);

            rowIndex = entryInt[0];
            colIndex = entryInt[1];
            rowIndex2 = exitInt[0];
            colIndex2 = exitInt[1];

            entryExitArr[0] = positionArr[rowIndex][colIndex];
            entryExitArr[1] = positionArr[rowIndex2][colIndex2];

        }
        intMaze[rowIndex * 2][colIndex * 2] = 83;
        intMaze[rowIndex2 * 2][colIndex2 * 2] = 69;

        return intMaze;
    }

}
