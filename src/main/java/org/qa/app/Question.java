package org.qa.app;

import org.qa.answer.extraction.AnswerExtractorException;
import org.qa.answer.extraction.DBPediaAnswerExtractor;
import org.qa.answer.extraction.finder.FactFinderFactory;
import org.qa.answer.extraction.finder.impl.FactFinderFactoryImpl;
import org.qa.question.parser.ParseException;
import org.qa.question.parser.QuestionCharacteristics;
import org.qa.question.parser.impl.SyntacticQuestionParser;

/**
 * Created by prasad on 15/12/2014.
 */
public class Question {

    public static String ask(String question) throws ParseException, AnswerExtractorException {
        QuestionCharacteristics questionCharacteristics = new SyntacticQuestionParser().parse(question);
        FactFinderFactory factFinderFactory = FactFinderFactoryImpl.getInstance();

        return new DBPediaAnswerExtractor(factFinderFactory).findAnswer(questionCharacteristics);
    }

    public static void main(String args[]) throws ParseException, AnswerExtractorException {
        if (args.length > 0) {
            String question = args[0];
            System.out.println("Question: "+ question);
            System.out.println("Answer  : "+ Question.ask(question));
        }
        System.exit(0);
    }
}
