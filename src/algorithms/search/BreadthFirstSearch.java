package algorithms.search;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm
{

    protected Queue<AState> queue;

    public BreadthFirstSearch() {
        this.queue = new ArrayDeque<AState>();
    } //Constructor

    public String getName() {
        return "Breadth First Search";
    }



    @Override
    public Solution solve(ISearchable searchable)
    {
        if (searchable != null) {
            // runs the bfs algorithm
            this.queue.add(searchable.getStartState());
            searchable.getStartState().setVisited(true);
            this.setNumberOFNodesEvaluated(1);

            AState temp;

            while (this.queue.size() != 0) {

                temp = this.queue.poll();
                this.setNumberOFNodesEvaluated(this.getNumberOFNodesEvaluated() + 1);

                // check if the temp is the Goal we stop the search
                if (temp == searchable.getGoalState())
                    break;

                for (int i = 0; i < temp.getSuccessors().size(); i++) {
                    AState tempson = temp.getSuccessors().get(i);

                    // if the State has not been visited we check with his successors
                    if (!tempson.isVisited()) {
                        tempson.setParent(temp);
                        this.queue.add(tempson);
                        tempson.setVisited(true);

                    }
                }

            }

            //builds the solution after the Best first search/ Breath first search if exists
            return CheckifExist(searchable);
        }

        return null;
    }

    //for later change of the queue to priority queue (BestFirstSearch)
    public void setQueue(Queue<AState> queue) {
        this.queue = queue;
    }
}
