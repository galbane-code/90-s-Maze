package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * the object that represents the row and the col of the maze so we can access it easily and manipulate the maze.
 */
public class Position implements Serializable{

    private int row;
    private int col;
    private int id; //helps in Kruskals algorithm for the disjoint sets.
    private Position father; //Parent Position.
    private int rank; //helps in Kruskals algorithm for the disjoint sets.
    private ArrayList<Position> neighbors; //Each Position neighbors set.

    public Position(int row, int col)
    {
        this.row = row;
        this.col = col;
        this.neighbors = new ArrayList<Position>();
        this.father = this;
    }

    /**
     * Getters and Setters
     */
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
