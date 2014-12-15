package app;

import answer.extraction.AnswerExtractorException;
import answer.extraction.DBPediaAnswerExtractor;
import answer.extraction.lookup.finder.FactFinderFactory;
import answer.extraction.lookup.finder.impl.FactFinderFactoryImpl;
import parser.ParseException;
import parser.QuestionCharacteristics;
import parser.impl.SyntacticQuestionParser;

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
