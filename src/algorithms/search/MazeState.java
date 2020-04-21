package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState

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

}
