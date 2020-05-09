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


    private void writeObject(ObjectOutputStream outputStream) throws IOException
    {
        int size = this.solution.size();
        outputStream.writeObject(size);
        for(int i=0; i < this.solution.size(); i++)
        {
            outputStream.writeObject(this.solution.get(i));
        }


    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException
    {
        int size = (int)inputStream.readObject();
        ArrayList<AState> Mazestates = new ArrayList<AState>();
        while(size != 0)
        {
            Mazestates.add((MazeState)inputStream.readObject());
            size--;
        }

        this.solution = Mazestates;

    }

}
