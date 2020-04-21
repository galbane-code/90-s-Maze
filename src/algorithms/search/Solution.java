package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;

public class Solution
{
    private ArrayList<AState> solution; // A field that keeps the route from the goal state to the start state

    public Solution()
    {
        this.solution =  new  ArrayList<AState>();
    }

    public ArrayList<AState> getSolutionPath()
    {
        return solution;
    }




}
