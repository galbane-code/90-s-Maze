package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

// an abstract class to represent one state/move in a searching problem.

public abstract class AState   {

    private AState parent;
    private String State;
    private ArrayList<AState> Successors;
    private double cost;
    private boolean IsVisited;

    public AState()
    {
        this.parent = this;
        this.cost = Integer.MAX_VALUE;
        this.Successors = new ArrayList<AState>();
        this.IsVisited = false;
    }

    //Visited
    public boolean isVisited() {
        return IsVisited;
    }
    public void setVisited(boolean visited) {
        IsVisited = visited;
    }

    // Parent
    public AState getParent() {
        return parent;
    }
    public void setParent(AState parent) {
        this.parent = parent;
    }

    //Cost
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    // Successors
    public ArrayList<AState> getSuccessors() {
        return Successors;
    }


    public void print(){return;} // why

    @Override
    public String toString() {
        return "AState{" +
                "parent=" + parent +
                ", State='" + State + '\'' +
                ", Successors=" + Successors +
                ", cost=" + cost +
                ", IsVisited=" + IsVisited +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void setState(String state) {
        State = state;
    }

    public void setSuccessors(ArrayList<AState> successors) {
        Successors = successors;
    }
}
