package answer.extraction;

import answer.extraction.lookup.finder.FactFinder;
import answer.extraction.lookup.finder.FactFinderException;
import answer.extraction.lookup.finder.FactFinderFactory;
import parser.QuestionCharacteristics;
import utils.DBPediaLookupException;
import utils.DBPediaUtils;

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
