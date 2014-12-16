package org.qa.question.parser;

/**
 * Created by prasad on 15/12/2014.
 */
public interface QuestionParser {

    /**
     * This method parses a question string and returns question characteristics
     */
    QuestionCharacteristics parse(String question) throws ParseException;
}
