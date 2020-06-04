package algorithms.search;

import java.util.PriorityQueue;

/**
 * Runs a BestFirstSearch on the Maze
 */
public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch()// sets the Queue to a priority Queue in the base class (BreadthFirstSearch uses a regular queue)
    {    super();
        openList = new PriorityQueue<AState>(new The_Comparator());
        setQueue(openList);
    }

    @Override
    public String getName() {
        return "Best First Search";
    }

    @Override
    /**
     * uses the BreadthFirstSearch "solve" method.
     * the only difference is the queue kind
     */
    public Solution solve(ISearchable searchable)
    {
        return super.solve(searchable);
    }
}

