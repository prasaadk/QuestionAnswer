package answer.extraction.lookup.finder;

/**
 * Created by prasad on 15/12/2014.
 */
public interface FactFinderFactory {

    /**
     * Returns appropriate FactFinder for the given term
     */
    FactFinder getFactFinderFor(String term);
}
