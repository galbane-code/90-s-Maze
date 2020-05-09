package algorithms.mazeGenerators;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
public class Position implements Serializable{
    // the object that represents the row and the col of the maze so we can access its easily and manipulate the maze
    //

    private int row;
    private int col;
    private int id; // help us in "kroskal" algorithm for the disjoint sets .
    private Position father; // a pointer has a role in the build algorithm .
    private int rank; // help us in "kroskal" algorithm for the disjoint sets.
    private ArrayList<Position> neighbors; // the positions that connected to this position in our maze.


    public Position(int row, int col) {
        this.row = row;
        this.col = col;
        this.neighbors = new ArrayList<Position>();
        this.father = this;
    }

    //row
    public int getRowIndex() {return row;}

    public void setRow(int row) {
        this.row = row;
    }

    //col
    public int getColumnIndex() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    //rank
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    //visited
    public void setId(int visited) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    // father pointer
    public Position getFather() {
        return father;
    }

    public void setFather(Position father) {
        this.father = father;
    }

    // neighbors
    public ArrayList<Position> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Position> neighbors) {
        this.neighbors = neighbors;
    }

    //print
    @Override
    public String toString() {
        return "{" + row +
                "," + col +
                "}";
    }
}
