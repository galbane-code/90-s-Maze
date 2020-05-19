package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
public class MyMazeGenerator extends AMazeGenerator {


    @Override
    public Maze generate(int rows, int cols) {

        //Objects to be used
        int[][] data;
        ArrayList<Edge> mazeEdges;
        Position [] entryExitArr = new Position[2];
        Position entry;
        Position exit;

        //functions
        createPositioinArray(rows, cols);
        totalEdges = createEdges(positionArr, rows, cols);
        mazeEdges = kruskal(totalEdges, rows, cols);
        data = positionToMaze(mazeEdges, rows, cols, entryExitArr);

        //perfect maze neighbors creation
        positionArr = perfectMazeEdges(positionArr, rows, cols, mazeEdges);

        Maze maze = new Maze(data, entryExitArr[0], entryExitArr[1]);
        maze.setPositionMatrix(positionArr);

        return maze;
    }


    //perfectMazeEdges initializes edge between every two positions (a complete graph)
    //to be sent later to Kruskal's algorithm
    public Position[][] perfectMazeEdges(Position[][] positionArr, int rows, int cols, ArrayList<Edge> mazeEdges) {
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


    //finds the ancestor of every position in order to decide whether to unify or not
    public Position find(Position position) {
        Position temp = position.getFather();
        if (temp == position)
            return temp;

        position.setFather(find(position.getFather()));
        return position.getFather();


    }

    //unifies two positions from two different "sets"
    public void unite(Position n1, Position n2)
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


    //The main algorithm of the maze creation. Kruskal's algorithm
    //when the algorithm ends, we have an MST of Position connected with V-1 Edges
    //that represent the maze itself
    public ArrayList<Edge> kruskal(ArrayList<Edge> totalEdges, int rows, int cols) {
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

    //from the edges we got from kruskal we now create an int maze twice the size in order to represent "walls"
    // only for visualization
    //than we randomize an entry and an exit on the frame of the maze (intMazeRandom function belongs to the abstract class)
    public int[][] positionToMaze(ArrayList<Edge> mazeEdges, int rows, int cols, Position[] entryExitArr) {
        int[][] intMaze = new int[rows * 2 - 1][cols * 2 - 1];
        Edge edge;
        Position x;
        Position y;
        Random rand = new Random();
        int ix;
        int jx;
        int iy;
        int jy;

        for (int i = 0; i < rows * 2 - 1; i++) {

            for (int j = 0; j < cols * 2 - 1; j++) {
                intMaze[i][j] = 1;

            }
        }

        int i = 0;
        while (i < mazeEdges.size()) {
            edge = mazeEdges.get(i);
            x = edge.getX();//extract positions
            y = edge.getY();


            ix = x.getRowIndex() * 2;//
            jx = x.getColumnIndex() * 2;
            iy = y.getRowIndex() * 2;
            jy = y.getColumnIndex() * 2;

            if (ix - iy == 0) {
                intMaze[ix][jx] = 0;
                intMaze[ix][jx + 1] = 0;
                intMaze[ix][jx + 2] = 0;
            } else {
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


