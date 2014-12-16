package org.qa.question.pattern.impl;

import org.junit.Before;
import org.junit.Test;
import org.qa.question.parser.QuestionCharacteristics;
import org.qa.question.pattern.QuestionPattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HowTermIsFocusQuestionTest {

    private QuestionPattern questionPattern;

    @Before
    public void setUp() throws Exception {
        questionPattern = new HowTermIsFocusQuestion();
    }

    @Test
    public void testBlank() throws Exception {
        assertTrue("Characteristics should be empty", questionPattern.match("").isEmpty());
    }

    @Test
    public void testInvalidString() throws Exception {
        assertTrue("Characteristics should be empty", questionPattern.match("invalid string this is invalid").isEmpty());
    }

    @Test
    public void testQuestionWithQuestionMark() throws Exception {
        String question = "how old is Madonna?";
        QuestionCharacteristics expected = buildExpected("how old", "madonna");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithSingleFocusWord() throws Exception {
        String question = "how old is Madonna";
        QuestionCharacteristics expected = buildExpected("how old", "madonna");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithMultiwordFocusWord() throws Exception {
        String question = "how old is David van cameroon";
        QuestionCharacteristics expected = buildExpected("how old", "david van cameroon");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithMultiwordTerm() throws Exception {
        String question = "how far away is Moon";
        QuestionCharacteristics expected = buildExpected("how far away", "moon");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithAllCaps() throws Exception {
        String question = "HOW FAR AWAY IS MOON";
        QuestionCharacteristics expected = buildExpected("how far away", "moon");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    private QuestionCharacteristics buildExpected(String term, String focus) {
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, term);
        expected.put(QuestionCharacteristics.FOCUS, focus);
        return expected;
    }
}