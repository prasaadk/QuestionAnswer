package org.qa.answer.extraction;

import org.qa.answer.extraction.finder.FactFinder;
import org.qa.answer.extraction.finder.FactFinderException;
import org.qa.answer.extraction.finder.FactFinderFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.qa.question.parser.QuestionCharacteristics;

import static org.junit.Assert.*;

public class DBPediaAnswerExtractorTest {

    private DBPediaAnswerExtractor m_dbPediaAnswerExtractor;
    private FactFinderFactory m_factFinderFactory;

    @Before
    public void setUp() throws Exception {
        m_factFinderFactory = Mockito.mock(FactFinderFactory.class);
        m_dbPediaAnswerExtractor = new DBPediaAnswerExtractor(m_factFinderFactory);
    }

    @Test
    public void testFindAnswer() throws Exception {
        FactFinder heightFinder = Mockito.mock(FactFinder.class);
        Mockito.doReturn("really long").when(heightFinder).get("Eiffel");

        Mockito.doReturn(heightFinder).when(m_factFinderFactory).getFactFinderFor("how long");

        QuestionCharacteristics questionCharacteristics = new QuestionCharacteristics();
        questionCharacteristics.put(QuestionCharacteristics.FOCUS, "Eiffel");
        questionCharacteristics.put(QuestionCharacteristics.TERM, "how long");

        assertEquals("really long", m_dbPediaAnswerExtractor.findAnswer(questionCharacteristics));
    }

    @Test (expected = AnswerExtractorException.class)
    public void testException() throws Exception {
        FactFinder heightFinder = Mockito.mock(FactFinder.class);
        Mockito.doReturn(heightFinder).when(m_factFinderFactory).getFactFinderFor("how long");
        Mockito.doThrow(new FactFinderException("some thing wrong")).when(heightFinder).get("Eiffel");

        QuestionCharacteristics questionCharacteristics = new QuestionCharacteristics();
        questionCharacteristics.put(QuestionCharacteristics.FOCUS, "Eiffel");
        questionCharacteristics.put(QuestionCharacteristics.TERM, "how long");

        m_dbPediaAnswerExtractor.findAnswer(questionCharacteristics);
    }
}