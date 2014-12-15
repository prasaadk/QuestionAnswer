package parser;

import org.junit.Before;
import org.junit.Test;
import parser.impl.SyntacticQuestionParser;

import static org.junit.Assert.*;

public class SyntacticQuestionParserTest {

    private SyntacticQuestionParser questionParser;

    @Before
    public void setUp() throws Exception {
        questionParser = new SyntacticQuestionParser();
    }

    @Test (expected=ParseException.class)
    public void testInvalidQuestions() throws Exception {
        questionParser.parse("invalid question");
    }

    @Test (expected=ParseException.class)
    public void testUnsupportedQuestions() throws Exception {
        questionParser.parse("where is London");
    }

    @Test
    public void testHowQuestions() throws Exception {
        String question = "how far away is Moon";
        QuestionCharacteristics expected = buildExpected("how far away", "moon");

        assertEquals("return values should be equal", expected, questionParser.parse(question));
    }

    @Test
    public void testWhatQuestions() throws Exception {
        String question = "what is the birth place of tony blair";
        QuestionCharacteristics expected = buildExpected("birth place", "tony blair");

        assertEquals("return values should be equal", expected, questionParser.parse(question));
    }

    private QuestionCharacteristics buildExpected(String term, String focus) {
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, term);
        expected.put(QuestionCharacteristics.FOCUS, focus);
        return expected;
    }

}