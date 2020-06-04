package algorithms.search;

import java.util.ArrayList;

/**
 *The interface for all "future problems" we would like to solve
 */
public interface ISearchable
{
     AState getStartState();
     AState getGoalState();

     ArrayList<AState> getAllPossibleStates(MazeState state);
     void SetSolution(Solution solution); // sets the solution of the searchable object
     void resetBool(); // resets the IsVisited field of a State to false, necessary after searching
}
