package algorithms.search;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Runs a BreadthFirstFirst on the Maze
 */
public class BreadthFirstSearch extends ASearchingAlgorithm
{

    protected Queue<AState> queue;

    public BreadthFirstSearch()
    {
        this.queue = new ArrayDeque<AState>();
    }

    public String getName()
    {
        return "Breadth First Search";
    }


    @Override
    public Solution solve(ISearchable searchable)
    {
        if (searchable != null) {
            this.queue.add(searchable.getStartState());
            searchable.getStartState().setVisited(true);
            this.setNumberOFNodesEvaluated(1);

            AState temp;

            while (this.queue.size() != 0)
            {

                temp = this.queue.poll();
                this.setNumberOFNodesEvaluated(this.getNumberOFNodesEvaluated() + 1);

                // check if the temp is the Goal, we stop the search
                if (temp == searchable.getGoalState())
                    break;

                for (int i = 0; i < temp.getSuccessors().size(); i++)
                {
                    AState tempson = temp.getSuccessors().get(i);

                    // if the State has not been visited we check with his successors
                    if (!tempson.isVisited())
                    {
                        tempson.setParent(temp);
                        this.queue.add(tempson);
                        tempson.setVisited(true);

                    }
                }

            }

            //builds the solution after the Best first search/ Breath first search if exists
            return solutionBuild(searchable);
        }
        return null;
    }

    /**
     * for later change of the queue to priority queue (BestFirstSearch)
     */
    public void setQueue(Queue<AState> queue) {
        this.queue = queue;
    }
}
