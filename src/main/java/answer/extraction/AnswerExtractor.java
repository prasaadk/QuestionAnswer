package answer.extraction;

import answer.extraction.lookup.finder.FactFinderException;
import parser.QuestionCharacteristics;
import utils.DBPediaLookupException;

/**
 * Created by prasad on 15/12/2014.
 */
public interface AnswerExtractor {

    /**
     * Finds answer for the given question characteristics
     */
    String findAnswer(QuestionCharacteristics questionCharacteristics) throws AnswerExtractorException;
}
