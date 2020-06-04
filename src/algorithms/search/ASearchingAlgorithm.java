package algorithms.search;

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * The class contains a  builds solution function that is relevant for all searching algorithms
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm
{
    private String name;
    protected PriorityQueue<AState> openList;
    private int visitedNodes; // the amount of nodes that have been checked during the search algorithm

    /**
     * function for the config file
     * @param  type
     * @return toReturn
     */
    public static ISearchingAlgorithm algorithmType(String type)
    {
        ASearchingAlgorithm toReturn;

        if(type.equals("BestFirstSearch"))
        {
            toReturn = new BestFirstSearch();
            return toReturn;
        }
        else if (type.equals("BreadthFirstSearch"))
        {
            toReturn = new BreadthFirstSearch();
            return toReturn;
        }
        else
        {
            toReturn = new DepthFirstSearch();
            return toReturn;
        }

    }

    /**
     * builds the solution after the search
     * @param searchable
     * @return
     */
    public Solution solutionBuild(ISearchable searchable)
    {
        AState solutionTemp = searchable.getGoalState();
        Solution solution = new Solution();

        while(solutionTemp != searchable.getStartState())
        {
            if(solutionTemp.getParent() == solutionTemp ) // checks whether we can continue to the root node or not
            {
                solution.getSolutionPath().clear();
                break;
            }
            else
            {
                solution.getSolutionPath().add(solutionTemp);
                solutionTemp = solutionTemp.getParent();
            }
        }

        if(solution.getSolutionPath().size() != 0)
        {
            solution.getSolutionPath().add(solutionTemp);
        }


        searchable.SetSolution(solution);
        searchable.resetBool(); //resets the boolean for future searches
        Collections.reverse(solution.getSolutionPath());
        return solution;
    }

    /**
     * Getters and Setters
     * @return
     */
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    //number of nodes evaluated during the search algorithm
    public int getNumberOFNodesEvaluated()
    {
        return visitedNodes;
    }
    public void setNumberOFNodesEvaluated(int visitedNodes)
    {
        this.visitedNodes = visitedNodes;
    }
}
