package org.qa.answer.extraction;

import org.qa.parser.QuestionCharacteristics;

/**
 * Created by prasad on 15/12/2014.
 */
public interface AnswerExtractor {

    /**
     * Finds answer for the given question characteristics
     */
    String findAnswer(QuestionCharacteristics questionCharacteristics) throws AnswerExtractorException;
}
