package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Solution implements Serializable
{
    //public byte[] toWriteObject;

    private ArrayList<AState> solution; // A field that keeps the route from the goal state to the start state

    public Solution()
    {
        this.solution =  new  ArrayList<AState>();
    }

    public ArrayList<AState> getSolutionPath()
    {
        return solution;
    }

    public void setSolution(ArrayList<AState> solution) {
        this.solution = solution;
    }

    /*
    private void writeObject(ObjectOutputStream outputStream) throws IOException
    {
        int size = this.solution.size();
        outputStream.writeObject(size);
        for(int i=0; i < this.solution.size(); i++)
        {
            outputStream.writeObject(this.solution.get(i));
        }

        outputStream.writeObject(this.solution);
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException
    {
        int size = (int)inputStream.readObject();
        ArrayList<AState> Mazestates = new ArrayList<AState>();
        while(size != 0)
        {
            MazeState MS = (MazeState)inputStream.readObject();
            Mazestates.add(MS);
            size--;
        }

        this.solution = Mazestates;

        ArrayList<AState> arr= (ArrayList<AState>)inputStream.readObject();

        this.solution = arr;
    }*/

}
