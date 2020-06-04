package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
/**
 * The class is responsible for maze generation by Kruskal's algorithm for finding MST of a given graph
 */
public class MyMazeGenerator extends AMazeGenerator {

    private ArrayList<Edge> totalEdges; // keeps all the possible Edges and then we use an algorithm that returns only the ones we need.

    @Override
    /**
     * Main function of the maze generation
     */
    public Maze generate(int rows, int cols)
    {
        int[][] data;
        ArrayList<Edge> mazeEdges;
        Position [] entryExitArr = new Position[2];

        //Functions
        createPositioinMatrix(rows, cols);
        totalEdges = createEdges(positionArr, rows, cols);
        mazeEdges = kruskal(totalEdges, rows, cols);
        data = positionToMaze(mazeEdges, rows, cols, entryExitArr);

        //Final maze neighbors creation
        positionArr = perfectMazeEdges(positionArr, rows, cols, mazeEdges);

        Maze maze = new Maze(data, entryExitArr[0], entryExitArr[1]);
        maze.setPositionMatrix(positionArr);

        return maze;
    }


    /**
     * Creates the maze edges only for the MyMazeGenrator (uses Kruskal algorithm)
     * @param positionArr
     * @param rows
     * @param cols
     * @return
     */
    public  ArrayList<Edge> createEdges(Position [][] positionArr, int rows, int cols)
    {
        Edge edgeRight;
        Edge edgeDown;
        ArrayList <Edge> tempEdges = new ArrayList<Edge>();
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0;  j < cols; j++)
            {
                if(i != rows - 1 && j != cols - 1) {
                    edgeRight = new Edge(positionArr[i][j], positionArr[i][j + 1]);
                    edgeDown = new Edge(positionArr[i][j], positionArr[i + 1][j]);
                    tempEdges.add(edgeRight);
                    tempEdges.add(edgeDown);
                }

                else if(i == rows -1 && j != cols -1)
                {
                    edgeRight = new Edge(positionArr[i][j], positionArr[i][j + 1]);
                    tempEdges.add(edgeRight);
                }

                else if (j == cols - 1 && i != rows  - 1)
                {
                    edgeDown = new Edge(positionArr[i][j], positionArr[i + 1][j]);
                    tempEdges.add(edgeDown);
                }
            }
        }
        return tempEdges;
    }

    /**
     * The main algorithm of the maze creation. Kruskal's algorithm
     * when the algorithm ends, we have an MST of Position connected with V-1 Edges
     * that represent the maze itself
     * @param totalEdges
     * @param rows
     * @param cols
     * @return
     */
    public ArrayList<Edge> kruskal(ArrayList<Edge> totalEdges, int rows, int cols)
    {
        ArrayList<Edge> toReturn = new ArrayList<Edge>();
        Random rand = new Random();
        int amount = 0;
        Edge toReplace;
        while (toReturn.size() < (rows * cols - 1)) {

            int index = rand.nextInt(totalEdges.size());
            Edge temp = totalEdges.get(index);
            Position x_set = find(temp.getX());
            Position y_set = find(temp.getY());

            if (x_set != y_set) {
                toReturn.add(temp);
                amount++;
                toReplace = totalEdges.get(index);
                totalEdges.set(index, totalEdges.get(totalEdges.size() - 1));
                totalEdges.set(totalEdges.size() - 1, toReplace);
                totalEdges.remove(totalEdges.size() - 1);
                unite(x_set, y_set);
            }
            amount++;
        }

        return toReturn;
    }

    /**
     * finds the ancestor of every position in order to decide whether to unify or not
     * @param position
     * @return
     */
    private Position find(Position position) {
        Position temp = position.getFather();
        if (temp == position)
            return temp;

        position.setFather(find(position.getFather()));
        return position.getFather();
    }

    /**
     * unifies two positions from two different "sets"
     * @param n1
     * @param n2
     */
    private void unite(Position n1, Position n2)
    {
        Position x_set_parent = find(n1);
        Position y_set_parent = find(n2);

        if (x_set_parent.getRank() > y_set_parent.getRank())
        {
            y_set_parent.setFather(x_set_parent);
        }
        else if (x_set_parent.getId() == y_set_parent.getId())
        {
            y_set_parent.setFather(x_set_parent);
            int newRank = x_set_parent.getRank();
            x_set_parent.setRank(newRank + 1);
        }
        else
        {
            x_set_parent.setFather(y_set_parent);
        }
    }

    /**
     * mazeEdges represents the MST of the Position matrix (the maze itself).
     * sets each Position the final "legal" neighbors
     * @param positionArr
     * @param rows
     * @param cols
     * @param mazeEdges
     * @return
     */
    private Position[][] perfectMazeEdges(Position[][] positionArr, int rows, int cols, ArrayList<Edge> mazeEdges)
    {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                positionArr[i][j].getNeighbors().clear();
            }
        }

        for (int h = 0; h < mazeEdges.size(); h++) {
            Position x = mazeEdges.get(h).getX();
            Position y = mazeEdges.get(h).getY();
            x.getNeighbors().add(y);
            y.getNeighbors().add(x);
        }

        return positionArr;
    }

    /**
     * From the edges we got from kruskal we now create an int maze twice the size in order to represent "walls".
     * only for visualization
     * than we randomize an entry and an exit on the frame of the maze (intMazeRandom function belongs to the abstract class)
     * @param mazeEdges
     * @param rows
     * @param cols
     * @param entryExitArr
     * @return
     */
    private int[][] positionToMaze(ArrayList<Edge> mazeEdges, int rows, int cols, Position[] entryExitArr)
    {
        int[][] intMaze = new int[rows * 2 - 1][cols * 2 - 1];
        Edge edge;
        Position x;
        Position y;
        Random rand = new Random();
        int ix;
        int jx;
        int iy;

        for (int i = 0; i < rows * 2 - 1; i++)
        {
            for (int j = 0; j < cols * 2 - 1; j++)
            {
                intMaze[i][j] = 1;
            }
        }

        int i = 0;
        while (i < mazeEdges.size())
        {
            edge = mazeEdges.get(i);
            x = edge.getX();//extract positions
            y = edge.getY();

            ix = x.getRowIndex() * 2;//
            jx = x.getColumnIndex() * 2;
            iy = y.getRowIndex() * 2;

            if (ix - iy == 0)
            {
                intMaze[ix][jx] = 0;
                intMaze[ix][jx + 1] = 0;
                intMaze[ix][jx + 2] = 0;
            }
            else
            {
                intMaze[ix][jx] = 0;
                intMaze[ix + 1][jx] = 0;
                intMaze[ix + 2][jx] = 0;
            }
            i++;
        }
        intMaze = intMazeRandom(rows, cols, intMaze, entryExitArr);

        return intMaze;
    }
}


