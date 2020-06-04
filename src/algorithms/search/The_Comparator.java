package algorithms.search;

import java.util.Comparator;

/**
 * A comparator for the best first search priority queue
 */
class The_Comparator implements Comparator<AState>
{
        public int compare (AState o1, AState o2)
        {
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
