package org.qa.answer.extraction.finder;

/**
 * Created by prasad on 15/12/2014.
 */

public interface FactFinder {

    /**
     * Finds appropriate fact for the subject in focus.
     */
    String get(String focus) throws FactFinderException;

}
