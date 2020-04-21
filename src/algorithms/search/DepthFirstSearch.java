package algorithms.search;

import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm
{
    // Depth First Search for the solution

    @Override
    public String getName() {
        return "Depth First Search";
    }

    @Override
    public  Solution solve(ISearchable searchable) {

        //stack creation, push the StartState
        //runs the DFS algorithm
        if(searchable != null) {
            Stack<AState> stack = new Stack<AState>();
            stack.push(searchable.getStartState());
            this.setNumberOFNodesEvaluated(1);
            AState temp;
            int j = 0;

            while (!stack.empty()) {
                // check if temp State is the Goal
                temp = stack.peek();
                if (temp == searchable.getGoalState())
                    break;
                stack.pop();


                // if the State has not been visited we check with his successors
                if (!temp.isVisited()) {
                    this.setNumberOFNodesEvaluated(this.getNumberOFNodesEvaluated() + 1);
                    temp.setVisited(true);
                    for (int i = 0; i < temp.getSuccessors().size(); i++) {
                        AState child = temp.getSuccessors().get(i);

                        if (!child.isVisited()) {
                            child.setParent(temp);
                            stack.push(child);
                        }
                    }
                }
            }

            //builds the solution after the DFS search if exists
            return CheckifExist(searchable);
        }
        return null;
    }

}
