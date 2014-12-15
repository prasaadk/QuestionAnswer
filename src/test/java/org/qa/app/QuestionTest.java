package org.qa.app;

import org.junit.Test;
import org.qa.app.Question;

import static org.junit.Assert.*;

public class QuestionTest {

    /**
     * Tests, as required according to the interview test specification
     */

    @Test
    public void testWhatQuestion() throws Exception {
        assertEquals("birth place should be correct", "London, United Kingdom",
                Question.ask("what is the birth place of David Cameron?"));
    }

    @Test
    public void testHowQuestion() throws Exception {
        assertEquals("age should be correct","61", Question.ask("how old is Tony Blair?"));
    }

    /**
     * Other tests to improve solution coverage
     */

    @Test
    public void testBirthPlaceCoverage() throws Exception {
        assertEquals("birth place should be correct", "Edinburgh, Scotland", Question.ask("what is the birth place of Tony Blair?"));
        assertEquals("birth place should be correct", "Govan, United Kingdom", Question.ask("what is the birth place of Gordon Brown?"));
        assertEquals("birth place should be correct", "Hawaii, United States", Question.ask("what is the birth place of Barack Obama?"));
        assertEquals("birth place should be correct", "Grantham, United Kingdom", Question.ask("what is the birth place of Margaret Thatcher?"));
    }

    @Test
    public void testHowAgeQuestions() throws Exception {
        assertEquals("age should be correct","48",Question.ask("how old is David Cameron?"));
        assertEquals("age should be correct","63",Question.ask("how old is Gordon Brown?"));
        assertEquals("age should be correct","53",Question.ask("how old is Barack Obama?"));
        assertEquals("age should be correct","47",Question.ask("how old is Nick Clegg?"));
    }

    @Test
    public void testWhatAgeQuestion() throws Exception {
        assertEquals("age should be correct","48",Question.ask("what is age of David Cameron?"));
        assertEquals("age should be correct","63",Question.ask("what is age of Gordon Brown?"));
        assertEquals("age should be correct","53",Question.ask("what is age of Barack Obama?"));
        assertEquals("age should be correct","47",Question.ask("what is age of Nick Clegg?"));
    }
}