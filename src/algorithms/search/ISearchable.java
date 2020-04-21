package algorithms.search;

import java.util.ArrayList;

public interface ISearchable
{
     // the interface for all the "problems" we would like to solve
     AState getStartState();
     AState getGoalState();

     ArrayList<AState> getAllPossibleStates(MazeState state);
     void SetSolution(Solution solution); // sets the solution of the searchable object
     void resetBool(); // resets the IsVisited field of a State to false, necessary after searching
}
