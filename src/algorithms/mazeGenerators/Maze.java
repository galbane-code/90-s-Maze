package algorithms.mazeGenerators;

//import javax.swing.text.Position;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Maze {

    private String name = "new maze";
    private Position entry; // the position we start from in the maze
    private Position exit; // the position we need to leave the maze
    boolean isSolved = false; // a bool that confirm as if the maze is solved or not
    int [][] data; // the field that we keep the print of the maze

    Character [] characters; // the characters that play in the maze
    Position [][] PositionMatrix; // a field that we keep in all the position and there neighbors updated to create and solved the maze



    public Maze(int [][] data, Position entry, Position exit) {
        this.entry = entry;
        this.exit = exit;
        //this.characters = characters;
        this.data = data;
    }

    public Maze(byte [] arr )

    {

        int rowSizeInt;
        int colSizeInt;
        int entryRowInt;
        int entryColInt;
        int exitRowInt;
        int exitColInt;

       //int x = ByteBuffer.wrap(arr).getInt();
        byte [] RowSizeBytes = Arrays.copyOfRange(arr, 0, 4);
        byte [] ColSizeBytes = Arrays.copyOfRange(arr, 4, 8);

        // first we initial the start and end position of the maze in the four bits and set those values into  four values in the arr bit
        byte [] StartRowBytes = Arrays.copyOfRange(arr, 8, 12);
        byte [] StartColBytes = Arrays.copyOfRange(arr, 12, 16);

        byte [] EndRowBytes = Arrays.copyOfRange(arr, 16, 20);
        byte [] EndColBytes = Arrays.copyOfRange(arr, 20, 24);

        rowSizeInt = ByteBuffer.wrap(RowSizeBytes).getInt();
        colSizeInt = ByteBuffer.wrap(ColSizeBytes).getInt();
        entryRowInt = ByteBuffer.wrap(StartRowBytes).getInt();
        entryColInt = ByteBuffer.wrap(StartColBytes).getInt();
        exitRowInt = ByteBuffer.wrap(EndRowBytes).getInt();
        exitColInt = ByteBuffer.wrap(EndColBytes).getInt();

        this.data = new int [rowSizeInt][colSizeInt];
        int byteArrIndex = 24;

        for(int i = 0; i < rowSizeInt; i++)
        {
            for(int j = 0; j < colSizeInt; j++)
            {
                this.data[i][j] = arr[byteArrIndex++];
            }
        }

        this.data[entryRowInt*2][entryColInt*2] = 83;
        this.data[exitRowInt*2][exitColInt*2] = 69;

        this.entry = new Position(entryRowInt, entryColInt);
        this.exit = new Position(exitRowInt, exitColInt);
    }



    //name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //entry

    public Position getStartPosition() {
        return entry;
    }

    public void setStartPosition(Position entry) {
        this.entry = entry;
    }

    //exit

    public Position getGoalPosition() {
        return exit;
    }

    public void setGoalPosition(Position exit) {
        this.exit = exit;
    }

    //isSolved

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved){}

    // character
    public Character[] getCharacters() {
        return characters;
    }

    public void setCharacters(Character[] characters) {
        this.characters = characters;
    }

    // PositionMatrix getter
    public Position[][] getPositionMatrix() {
        return PositionMatrix;
    }

    //defines the Position matrix of the maze
    public void setPositionMatrix(Position[][] positionMatrix) {
        PositionMatrix = positionMatrix;
        this.entry = positionMatrix[entry.getRowIndex()][entry.getColumnIndex()];
        this.exit = positionMatrix[exit.getRowIndex()][exit.getColumnIndex()];
    }

    //printFunction to print the maze as an int maze with its start and goal positions
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


    public byte[] toByteArray() {

        int rowSize = data.length;
        int colSize = data[0].length;
        byte [] byteArr = new byte[rowSize * colSize + 24]; // 24 for the entry, exit, size of row, size of col
        int byteCounter = 24;

        //TODO: add the first 24 bytes!

        // first we initial the size of the maze in the four bits and set those values into  values in the arr bit
        byte [] RowSizeBytes = ByteBuffer.allocate(4).putInt(rowSize).array();
        byte [] ColSizeBytes = ByteBuffer.allocate(4).putInt(colSize).array();

        // first we initial the start and end position of the maze in the four bits and set those values into  four values in the arr bit
        byte [] StartRowBytes = ByteBuffer.allocate(4).putInt(this.entry.getRowIndex()).array();
        byte [] StartColBytes = ByteBuffer.allocate(4).putInt(this.entry.getColumnIndex()).array();

        byte [] EndRowBytes = ByteBuffer.allocate(4).putInt(this.exit.getRowIndex()).array();
        byte [] EndColBytes = ByteBuffer.allocate(4).putInt(this.exit.getColumnIndex()).array();

        //
        System.arraycopy(RowSizeBytes, 0, byteArr, 0, 4);
        System.arraycopy(ColSizeBytes, 0, byteArr, 4, 4);
        System.arraycopy(StartRowBytes, 0, byteArr, 8, 4);
        System.arraycopy(StartColBytes, 0, byteArr, 12, 4);
        System.arraycopy(EndRowBytes, 0, byteArr, 16, 4);
        System.arraycopy(EndColBytes, 0, byteArr, 20, 4);


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
}
