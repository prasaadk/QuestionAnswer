package org.qa.question.pattern.impl;

import org.junit.Before;
import org.junit.Test;
import org.qa.parser.QuestionCharacteristics;
import org.qa.question.pattern.QuestionPattern;

import static org.junit.Assert.*;

public class WhatIsTermOfFocusQuestionTest {

    private QuestionPattern questionPattern;

    @Before
    public void setUp() throws Exception {
        questionPattern = new WhatIsTermOfFocusQuestion();
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
        String question = "what is the birth place of Madonna?";
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, "birth place");
        expected.put(QuestionCharacteristics.FOCUS, "madonna");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithSingleFocusWord() throws Exception {
        String question = "what is the birth place of Madonna";
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, "birth place");
        expected.put(QuestionCharacteristics.FOCUS, "madonna");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithMultiwordFocusWord() throws Exception {
        String question = "what is the birth place of David van cameroon";
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, "birth place");
        expected.put(QuestionCharacteristics.FOCUS, "david van cameroon");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithoutOptional() throws Exception {
        String question = "what is place of birth of tony blair";
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, "place of birth");
        expected.put(QuestionCharacteristics.FOCUS, "tony blair");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }

    @Test
    public void testQuestionWithAllCaps() throws Exception {
        String question = "WHAT IS PLACE OF BIRTH OF TONY BLAIR";
        QuestionCharacteristics expected = new QuestionCharacteristics();
        expected.put(QuestionCharacteristics.TERM, "place of birth");
        expected.put(QuestionCharacteristics.FOCUS, "tony blair");

        assertEquals("return values should be equal", expected, questionPattern.match(question));
    }
}