package algorithms.search;

import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch{

    public BestFirstSearch() { // sets the Queue to a priority Queue in the base class (BreadthFirstSearch uses a regular queue)
        super();
        openList = new PriorityQueue<AState>(new The_Comparator());
        setQueue(openList);
    }

    @Override
    public String getName() {
        return "Best First Search";
    }

    @Override
    public Solution solve(ISearchable searchable) {

        // uses the BreadthFirstSearch "solve" method.
        //the only difference is the queue kind
        return super.solve(searchable);
    }
}

