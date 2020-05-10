package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class MazeState extends AState implements Serializable

    // a derived class of AState, used for the maze problem
{
    private int id;
    private Position self; // the maze position

    public MazeState(int id,Position self)
    {
        super();
        this.id = id;
        this.self = self;
    }

    //self use printing function
    public void print() {
        System.out.println("self: " + this.self);
        System.out.println("neighbors: ");
        for (AState neighbor : this.getSuccessors())
        {
            System.out.println(neighbor);
        }
        System.out.println("--------");
    }

    @Override
    public String toString()
    {
        return self.toString();
    }

    //Self
    public Position getSelf() {
        return self;
    }

    public void setSelf(Position self) {
        this.self = self;
    }

    //Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] MazeStateTobyteArr() // TODO: change the maze state to byte arr
    {
        byte [] arr = new byte[8];
        byte [] RowBytes = ByteBuffer.allocate(4).putInt(this.self.getRowIndex()).array();
        byte [] ColBytes = ByteBuffer.allocate(4).putInt(this.self.getColumnIndex()).array();
        System.arraycopy(RowBytes, 0, arr, 0, 4);
        System.arraycopy(ColBytes, 0, arr, 4, 4);
        return arr;
    }


    public static MazeState ByteArrToMazeState (byte [] arr) // TODO: CHANGE the byte arr back to maze PositIon
    {
        byte [] RowSizeBytes = Arrays.copyOfRange(arr, 0, 4);
        byte [] ColSizeBytes = Arrays.copyOfRange(arr, 4, 8);
        int rowInt = ByteBuffer.wrap(RowSizeBytes).getInt();
        int colInt = ByteBuffer.wrap(ColSizeBytes).getInt();
        Position pos = new Position(rowInt,colInt);
        MazeState state = new MazeState(0,pos);
        return state;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException
    {
        byte [] arr = this.MazeStateTobyteArr();
        outputStream.writeObject(arr); // first we write this maze state
        byte SuccesorsSize = (byte)(this.getSuccessors().size());
        outputStream.writeObject(SuccesorsSize); // then the size of his successors
        for(int i=0; i < this.getSuccessors().size(); i++) // then we write all his successors seperatley
        {
            outputStream.writeObject(((MazeState)(this.getSuccessors().get(i))).MazeStateTobyteArr());
        }

        outputStream.writeObject(((MazeState)(this.getParent())).MazeStateTobyteArr()); // write his parent
        outputStream.writeObject(this.getCost()); // write the cost of the node
        /*outputStream.writeInt(this.self.getRowIndex());
        outputStream.writeInt(this.self.getColumnIndex());*/

    }


    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException
    {

        byte[] arr = (byte[]) inputStream.readObject();

        byte[] RowSizeBytes = Arrays.copyOfRange(arr, 0, 4);
        byte[] ColSizeBytes = Arrays.copyOfRange(arr, 4, 8);
        int rowInt = ByteBuffer.wrap(RowSizeBytes).getInt();
        int colInt = ByteBuffer.wrap(ColSizeBytes).getInt();
        Position pos = new Position(rowInt, colInt);
        MazeState state = new MazeState(0, pos);
        MazeState newState = MazeState.ByteArrToMazeState(arr);
        byte successorsSize = (byte)inputStream.readObject();

        MazeState temp;
        for(int i = 0; i < successorsSize; i++)
        {
            byte [] tempArr = (byte [])inputStream.readObject();
            temp = MazeState.ByteArrToMazeState(tempArr);
            newState.getSuccessors().add(temp);
        }

        arr = (byte [])inputStream.readObject();
        MazeState parent = MazeState.ByteArrToMazeState(arr);
        newState.setParent(parent);

        double cost = (double)inputStream.readObject();
        newState.setCost(cost);

        this.setSelf(state.self);
        this.id = newState.id;
        this.setParent(newState.getParent());
        this.setSuccessors(newState.getSuccessors());
        this.setCost(newState.getCost());

        /*int row = inputStream.readInt();
        int col = inputStream.readInt();
        this.self = new Position(row, col);*/

    }

}
