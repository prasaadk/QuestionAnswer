package org.qa.answer.extraction.finder.impl;

import org.qa.answer.extraction.finder.FactFinderException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgeFinderTest {

    @Test
    public void testLivingPerson() throws Exception {
        assertEquals("61", new AgeFinder().get("Tony Blair"));
        assertEquals("48", new AgeFinder().get("David Cameron"));
        assertEquals("82", new AgeFinder().get("Manmohan Singh"));
    }

    @Test
    public void testDeceasedPerson() throws Exception {
        assertEquals("95", new AgeFinder().get("Nelson Mandela"));
        assertEquals("87", new AgeFinder().get("Margaret Thatcher"));
        assertEquals("90", new AgeFinder().get("Winston Churchill"));
        assertEquals("40", new AgeFinder().get("Franz Kafka"));
    }

    @Test
    public void testNotAPerson() throws Exception {
        assertNull(new AgeFinder().get("Yoda"));
        assertNull(new AgeFinder().get("Han Solo"));
        assertNull(new AgeFinder().get("Sherlock Holmes"));
    }

    @Test (expected = FactFinderException.class)
    public void testWrongName() throws Exception {
        new AgeFinder().get("asdfasdf");
    }
}