package algorithms.search;

import java.util.Comparator;

class The_Comparator implements Comparator<AState>
{
 // a comparator for the best first search priority queue

public int compare (AState o1, AState o2){
        if (o1.getCost() < o2.getCost())
        {
        return -1;
        }
        else if (o1.getCost() > o2.getCost())
        {
        return 1;
        }
        else
        {
        return 0;
        }
    }
}
