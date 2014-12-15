package org.qa.question.pattern;

import org.qa.parser.QuestionCharacteristics;

/**
 * Created by prasad on 15/12/2014.
 */
public interface QuestionPattern {

    /**
     * Maps
     * @param utterance
     * @return
     */
    QuestionCharacteristics match(String utterance);

}
