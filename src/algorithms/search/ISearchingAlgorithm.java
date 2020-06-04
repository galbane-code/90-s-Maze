package algorithms.search;

/**
 * Interface to be implemented by all Searching Algorithms
 */
public interface ISearchingAlgorithm
{
    /**
     * the function that checks whether there is a solution, and returns it if exists
     */
    Solution solve(ISearchable searchable);
}
