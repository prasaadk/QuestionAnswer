package org.qa.answer.extraction;

import org.qa.answer.extraction.finder.FactFinder;
import org.qa.answer.extraction.finder.FactFinderException;
import org.qa.answer.extraction.finder.FactFinderFactory;
import org.qa.parser.QuestionCharacteristics;

/**
 * Created by prasad on 15/12/2014.
 */
public class DBPediaAnswerExtractor implements AnswerExtractor {

    private FactFinderFactory m_factFinderFactory;

    public DBPediaAnswerExtractor(FactFinderFactory factFinderFactory) {
        m_factFinderFactory = factFinderFactory;
    }

    @Override
    public String findAnswer(QuestionCharacteristics questionCharacteristics) throws AnswerExtractorException {
        FactFinder finder = findFactFinder(questionCharacteristics);
        try {
            return finder.get(questionCharacteristics.getFocus());
        } catch (FactFinderException e) {
            throw new AnswerExtractorException("Unable to find answer", e);
        }
    }

    private FactFinder findFactFinder(QuestionCharacteristics questionCharacteristics) {
        String term = questionCharacteristics.getTerm();
        return m_factFinderFactory.getFactFinderFor(term);
    }
}
