package algorithms.mazeGenerators;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.ByteBuffer;

/**
 * Represents the Maze itself.
 */
public class Maze implements Serializable {

    private String name = "new maze";
    private Position entry; // Maze entry
    private Position exit; // Maze exit
    private boolean isSolved = false; //If the maze was solved -> true
    private int [][] data; // Visualisation of the maze
    private Position [][] PositionMatrix; //Each cell represented as a Position
    private Character [] characters;

    /**
     * "Regular' constructor
     * @param data
     * @param entry
     * @param exit
     */
    public Maze(int [][] data, Position entry, Position exit)
    {
        this.entry = entry;
        this.exit = exit;
        //this.characters = characters;
        this.data = data;
    }

    /**
     * A byte array constructor
     * @param arr
     */
    public Maze(byte[] arr)
    {
        try {

            int rowSizeInt;
            int colSizeInt;
            int entryRowInt;
            int entryColInt;
            int exitRowInt;
            int exitColInt;

            byte[] RowSizeBytes = Arrays.copyOfRange(arr, 0, 4);
            byte[] ColSizeBytes = Arrays.copyOfRange(arr, 4, 8);

            byte[] StartRowBytes = Arrays.copyOfRange(arr, 8, 12);
            byte[] StartColBytes = Arrays.copyOfRange(arr, 12, 16);

            byte[] EndRowBytes = Arrays.copyOfRange(arr, 16, 20);
            byte[] EndColBytes = Arrays.copyOfRange(arr, 20, 24);

            rowSizeInt = ByteBuffer.wrap(RowSizeBytes).getInt();
            colSizeInt = ByteBuffer.wrap(ColSizeBytes).getInt();
            entryRowInt = ByteBuffer.wrap(StartRowBytes).getInt();
            entryColInt = ByteBuffer.wrap(StartColBytes).getInt();
            exitRowInt = ByteBuffer.wrap(EndRowBytes).getInt();
            exitColInt = ByteBuffer.wrap(EndColBytes).getInt();

            this.data = new int[rowSizeInt][colSizeInt];

            //Sets the visual int matrix
            int byteArrIndex = 24;
            for (int i = 0; i < rowSizeInt; i++)
            {
                for (int j = 0; j < colSizeInt; j++)
                {
                    this.data[i][j] = arr[byteArrIndex++];
                }
            }

            this.PositionMatrix = new Position[rowSizeInt / 2 + 1][colSizeInt / 2 + 1];
            int counter = 0;
            for (int i = 0; i < rowSizeInt / 2 + 1; i++)
            {
                for (int j = 0; j < colSizeInt / 2 + 1; j++)
                {
                    this.PositionMatrix[i][j] = new Position(i, j);
                    this.PositionMatrix[i][j].setId(counter);
                    counter++;
                }
            }

            this.data[entryRowInt * 2][entryColInt * 2] = 0;
            this.data[exitRowInt * 2][exitColInt * 2] = 0;

            intToPositionArr(this.PositionMatrix, data);

            this.data[entryRowInt * 2][entryColInt * 2] = 83;
            this.data[exitRowInt * 2][exitColInt * 2] = 69;

            this.entry = PositionMatrix[entryRowInt][entryColInt];
            this.exit = PositionMatrix[exitRowInt][exitColInt];
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * PrintFunction to print the maze as an int maze with its start and goal positions
     */
    public void print()
    {
        for(int i = 0; i < this.data.length; i++)
        {
            for(int j = 0; j<this.data[0].length; j++)
            {
                if(this.data[i][j] == 83)
                {
                    System.out.print("S");
            }

                else if(this.data[i][j] == 69)
                {
                    System.out.print("E");
                }

                else
                {
                    System.out.print(this.data[i][j]);
                }
            }
            System.out.println();
        }
    }


    /**
     * converts a maze into a byte array.
     * relevant when using Output\Input Stream
     * @return byteArr
     */
    public byte[] toByteArray() {

        int rowSize = data.length;
        int colSize = data[0].length;
        byte [] byteArr = new byte[rowSize * colSize + 24]; // 24 for the entry, exit, size of row, size of col
        int byteCounter = 24;

        //Converts the RowSize, ColSize, Start, Exit of the maze into four bytes data each.
        byte [] RowSizeBytes = ByteBuffer.allocate(4).putInt(rowSize).array();
        byte [] ColSizeBytes = ByteBuffer.allocate(4).putInt(colSize).array();

        byte [] StartRowBytes = ByteBuffer.allocate(4).putInt(this.entry.getRowIndex()).array();
        byte [] StartColBytes = ByteBuffer.allocate(4).putInt(this.entry.getColumnIndex()).array();

        byte [] EndRowBytes = ByteBuffer.allocate(4).putInt(this.exit.getRowIndex()).array();
        byte [] EndColBytes = ByteBuffer.allocate(4).putInt(this.exit.getColumnIndex()).array();

        //Copies the data to the main byte array.
        System.arraycopy(RowSizeBytes, 0, byteArr, 0, 4);
        System.arraycopy(ColSizeBytes, 0, byteArr, 4, 4);
        System.arraycopy(StartRowBytes, 0, byteArr, 8, 4);
        System.arraycopy(StartColBytes, 0, byteArr, 12, 4);
        System.arraycopy(EndRowBytes, 0, byteArr, 16, 4);
        System.arraycopy(EndColBytes, 0, byteArr, 20, 4);

        //Copies the int maze to the byte array
        for (int i = 0; i < data.length; i++)
        {
            for (int j = 0; j < data[0].length; j++)
            {
                byteArr[byteCounter] = (byte) data[i][j];
                byteCounter++;
            }
        }

        return byteArr;
    }

    /**
     * Converts the visual (int) data into a Position matrix
     * @param poseArr
     * @param intData
     */
    public void intToPositionArr(Position [][] poseArr, int [][] intData)
    {
        int intMazeRow = 0;
        int intMazecol;
        for(int i = 0; i< poseArr.length - 1; i++)
        {
            intMazecol = 0;
            for(int j = 0; j< poseArr[0].length - 1; j++)
            {
                if(intData[intMazeRow][intMazecol] == 0 && intData[intMazeRow][intMazecol+1] == 0 && intData[intMazeRow][intMazecol+2] == 0)
                {
                    poseArr[i][j].getNeighbors().add(poseArr[i][j+1]);
                    poseArr[i][j+1].getNeighbors().add(poseArr[i][j]);
                }
                if(intData[intMazeRow][intMazecol] == 0 && intData[intMazeRow+1][intMazecol] == 0 && intData[intMazeRow+2][intMazecol] == 0)
                {
                    poseArr[i][j].getNeighbors().add(poseArr[i+1][j]);
                    poseArr[i+1][j].getNeighbors().add(poseArr[i][j]);
                }

                //last column
                if(j == poseArr[0].length - 2)
                {
                    if(intData[intMazeRow][intMazecol + 2] == 0 && intData[intMazeRow+1][intMazecol + 2] == 0 && intData[intMazeRow+2][intMazecol + 2] == 0)
                    {
                        poseArr[i][j+1].getNeighbors().add(poseArr[i+1][j+1]);
                        poseArr[i+1][j+1].getNeighbors().add(poseArr[i][j+1]);
                    }

                }

                ///last row
                if(i == poseArr.length - 2)
                {
                    if(intData[intMazeRow + 2][intMazecol] == 0 && intData[intMazeRow + 2][intMazecol+1] == 0 && intData[intMazeRow + 2][intMazecol+2] == 0)
                    {
                        poseArr[i+1][j].getNeighbors().add(poseArr[i+1][j+1]);
                        poseArr[i+1][j+1].getNeighbors().add(poseArr[i+1][j]);
                    }
                }
                intMazecol += 2;
            }
            intMazeRow += 2;
        }
    }


    /**
     * OutputStream use.
     * @param outputStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream outputStream) throws IOException
    {
        byte [] byteArr = this.toByteArray();
        outputStream.writeObject(byteArr);
    }

    /**
     * InputStream use.
     * @param inputStream
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException, InterruptedException
    {
        byte [] arr = (byte [])inputStream.readObject();
        Maze maze = new Maze(arr);
        this.exit = maze.getGoalPosition();
        this.entry = maze.getStartPosition();
        this.PositionMatrix = maze.getPositionMatrix();
        this.data = maze.data;
    }

    /**
     * Getters and Setters.
     */
    //name
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    //entry
    public Position getStartPosition()
    {
        return entry;
    }
    public void setStartPosition(Position entry)
    {
        this.entry = entry;
    }

    //exit
    public Position getGoalPosition()
    {
        return exit;
    }
    public void setGoalPosition(Position exit)
    {
        this.exit = exit;
    }

    //isSolved
    public boolean isSolved()
    {
        return isSolved;
    }
    public void setSolved(boolean solved){}

    // character
    public Character[] getCharacters()
    {
        return characters;
    }
    public void setCharacters(Character[] characters)
    {
        this.characters = characters;
    }

    // PositionMatrix getter
    public Position[][] getPositionMatrix() {
        return PositionMatrix;
    }

    //defines the Position matrix of the maze
    public void setPositionMatrix(Position[][] positionMatrix)
    {
        PositionMatrix = positionMatrix;
        this.entry = positionMatrix[entry.getRowIndex()][entry.getColumnIndex()];
        this.exit = positionMatrix[exit.getRowIndex()][exit.getColumnIndex()];
    }

}
