package algorithms.mazeGenerators;

//import javax.swing.text.Position;
//import java.util.Arrays;

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
        byte [] byteArr = new byte[rowSize * colSize + 16];//16 for the entry, exit, size of row, size of col
        int byteCounter = 16;

        //TODO: add the first 16 bytes!

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
