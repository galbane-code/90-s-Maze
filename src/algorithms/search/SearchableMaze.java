package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.*;

public class SearchableMaze implements ISearchable
{
    //An object adapter between the Maze and The Searchable problem

    private Maze maze;
    private MazeState goalState;
    private MazeState startState;
    private ArrayList<MazeState> states; //stores the maze states after we transform the Position to Astate type
    private HashMap<Position, MazeState> poseToStateMap; //two hash maps inorder to help us to find
    private HashMap<MazeState, Position > stateToPoseMap; //  position by a mazestate and to find mazestate by a position
    protected Solution solution;


    public SearchableMaze(Maze maze) {

        this.maze = maze;
        this.states = new ArrayList<MazeState>();
        this.poseToStateMap = new HashMap<Position,MazeState>();
        this.stateToPoseMap = new HashMap<MazeState, Position>();
        positionTostate(this.states,this.poseToStateMap, this.stateToPoseMap, this.maze.getPositionMatrix(), startState, goalState);
        this.startState = poseToStateMap.get(this.maze.getStartPosition());
        this.goalState = poseToStateMap.get(this.maze.getGoalPosition());
        this.solution = new Solution();
        setCost(startState, stateToPoseMap, states);

    }

    // Set and Get methods
    public Solution getSolution() {
        return solution;
    }

    public ArrayList<MazeState> getStates() {
        return states;
    }

    public  ArrayList<AState> getAllPossibleStates(MazeState state)
    {
        return state.getSuccessors();
    }

    @Override
    public void SetSolution(Solution solution) {
        this.solution = solution;
    }

    @Override
    public AState getStartState() {
        return startState;
    }

    @Override
    public AState getGoalState() {
        return goalState;
    }


    // The Function that wraps a mazze Position in a MazeState

    public void positionTostate(ArrayList<MazeState> states, HashMap<Position,MazeState> poseToStateMap, HashMap<MazeState, Position> stateToPoseMap, Position[][] positions,
    MazeState startState, MazeState goalState)
    {
        MazeState newState;
        for(int i=0; i < positions.length; i++)
        {
            for(int j=0; j < positions[0].length; j++)
            {
                if(poseToStateMap.containsKey(positions[i][j]))
                {
                    newState = poseToStateMap.get(positions[i][j]);
                }
                else
                {
                    newState = new MazeState(0,positions[i][j]);
                    poseToStateMap.put(positions[i][j],newState);
                    stateToPoseMap.put(newState, positions[i][j]);
                }

                for(int h=0; h < positions[i][j].getNeighbors().size(); h++)
                {
                    MazeState newState1;

                    if(poseToStateMap.containsKey(positions[i][j].getNeighbors().get(h)))
                    {
                        newState1 = poseToStateMap.get(positions[i][j].getNeighbors().get(h));
                        newState.getSuccessors().add(newState1);

                    }
                    else
                    {
                        newState1 = new MazeState(0,positions[i][j].getNeighbors().get(h));
                        newState.getSuccessors().add(newState1);
                        poseToStateMap.put(((MazeState) newState1).getSelf(),newState1);
                        stateToPoseMap.put(newState1, ((MazeState)(newState1)).getSelf());

                    }

                }
                states.add(newState);

            }
        }

        addDiagonal(states,stateToPoseMap,positions.length,positions[0].length);


    }

    // The function checks if there are diagonal neighbors that can be add to each MazeState
    public void addDiagonal(ArrayList<MazeState> states,HashMap<MazeState, Position> stateToPoseMap, int sizerow,int sizecol)
    {
        for(int i = 0; i<states.size(); i++)
        {
            MazeState current = states.get(i);

            int currentrow = ((MazeState) current).getSelf().getRowIndex();
            int currentcol = ((MazeState) current).getSelf().getColumnIndex();

            for(int j = 0; j < states.get(i).getSuccessors().size(); j++)
            {
                AState currentson = current.getSuccessors().get(j);
                int currentsonrow = stateToPoseMap.get(currentson).getRowIndex();
                int currentsoncol = stateToPoseMap.get(currentson).getColumnIndex();

                for(int h=0; h < states.get(i).getSuccessors().get(j).getSuccessors().size(); h++)
                {
                    AState currentsonchild = currentson.getSuccessors().get(h);

                    int currentsonchildrow = stateToPoseMap.get(currentsonchild).getRowIndex();
                    int currentsonchildcol = stateToPoseMap.get(currentsonchild).getColumnIndex();

                    if( (currentrow < sizerow - 1 && currentcol < sizecol - 1) &&( currentcol + 1 == currentsoncol && currentrow == currentsonrow)
                            &&  ( currentsonrow + 1 == currentsonchildrow && currentsonchildcol == currentsoncol) ) // checks right down cross
                    {
                        current.getSuccessors().add(currentsonchild);
                        currentsonchild.getSuccessors().add(current);
                    }

                    else if((currentrow < sizerow - 1 && currentcol < sizecol - 1) &&( currentcol  == currentsoncol && currentrow + 1 == currentsonrow)
                            &&  ( currentsonrow  == currentsonchildrow && currentsonchildcol == currentsoncol + 1) ) // checks down right cross
                    {
                        current.getSuccessors().add(currentsonchild);
                        currentsonchild.getSuccessors().add(current);
                    }

                    else if((currentrow > 0 && currentcol < sizecol - 1) &&( currentcol + 1 == currentsoncol && currentrow == currentsonrow)//checks left up cross
                            &&  ( currentsonrow - 1 == currentsonchildrow && currentsonchildcol == currentsoncol) )
                    {
                        current.getSuccessors().add(currentsonchild);
                        currentsonchild.getSuccessors().add(current);
                    }

                    else if((currentrow > 0 && currentcol < sizecol - 1) &&( currentcol  == currentsoncol && currentrow - 1 == currentsonrow)//checks up left cross
                            &&  ( currentsonrow  == currentsonchildrow && currentsonchildcol == currentsoncol + 1) )
                    {
                        current.getSuccessors().add(currentsonchild);
                        currentsonchild.getSuccessors().add(current);
                    }



                }
            }
        }
    }

    // The function sets the cost for each Maze state from the StartState.
    // 10 points for a straight neighbor, 15 for a diagonal neighbor
    public void setCost(MazeState startState, HashMap<MazeState,Position> stateToPoseMap, ArrayList<MazeState> states)
    {
        Queue<MazeState> queue = new ArrayDeque<MazeState>();
        startState.setCost(0);
        queue.add(startState);
        MazeState tempState = queue.peek();
        int succesorsSize;

        int tempRow = stateToPoseMap.get(tempState).getRowIndex();
        int tempCol = stateToPoseMap.get(tempState).getColumnIndex();

        int sonRow;
        int sonCol;


        while(!queue.isEmpty())
        {
            tempState = queue.poll();
            succesorsSize = getAllPossibleStates(tempState).size();

            if(!tempState.isVisited())
            {
                tempState.setVisited(true);
                for (int i = 0; i < succesorsSize; i++)
                {
                    AState son = tempState.getSuccessors().get(i);
                    sonRow = stateToPoseMap.get(son).getRowIndex();
                    sonCol = stateToPoseMap.get(son).getColumnIndex();


                    if(!son.isVisited())
                    {
                        if(son.getCost() == Integer.MAX_VALUE)
                        {
                            if (tempRow != sonRow && tempCol != sonCol)
                            {
                                son.setCost(tempState.getCost() + 15);
                            }
                            else
                            {
                                son.setCost(tempState.getCost() + 10);
                            }
                        }

                        queue.add((MazeState) son);
                    }
                }
            }
        }

        for(int i = 0; i < states.size(); i++)
        {
            states.get(i).setVisited(false);
        }

    }

    @Override
    // resets the IsVisited field of a State to false, necessary after searching
    public void resetBool()
    {
        for(int i = 0; i < states.size(); i++)
        {
            states.get(i).setVisited(false);
        }
    }


}
