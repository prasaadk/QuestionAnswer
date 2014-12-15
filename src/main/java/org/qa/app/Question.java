package org.qa.app;

import org.qa.answer.extraction.AnswerExtractorException;
import org.qa.answer.extraction.DBPediaAnswerExtractor;
import org.qa.answer.extraction.finder.FactFinderFactory;
import org.qa.answer.extraction.finder.impl.FactFinderFactoryImpl;
import org.qa.parser.ParseException;
import org.qa.parser.QuestionCharacteristics;
import org.qa.parser.impl.SyntacticQuestionParser;

/**
 * Created by prasad on 15/12/2014.
 */
public class Question {

    public static String ask(String question) throws ParseException, AnswerExtractorException {
        QuestionCharacteristics questionCharacteristics = new SyntacticQuestionParser().parse(question);
        FactFinderFactory factFinderFactory = new FactFinderFactoryImpl();

        return new DBPediaAnswerExtractor(factFinderFactory).findAnswer(questionCharacteristics);
    }
}
