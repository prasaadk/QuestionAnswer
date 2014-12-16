package org.qa.answer.extraction.finder.impl;

import org.qa.answer.extraction.finder.FactFinderException;
import org.junit.Test;

import static org.junit.Assert.*;

public class AgeFinderTest {

    @Test
    public void testLivingPerson() throws Exception {
        assertEquals("61", getAgeFinder().get("Tony Blair"));
        assertEquals("48", getAgeFinder().get("David Cameron"));
        assertEquals("82", getAgeFinder().get("Manmohan Singh"));
    }

    @Test
    public void testDeceasedPerson() throws Exception {
        assertEquals("95", getAgeFinder().get("Nelson Mandela"));
        assertEquals("87", getAgeFinder().get("Margaret Thatcher"));
        assertEquals("90", getAgeFinder().get("Winston Churchill"));
        assertEquals("40", getAgeFinder().get("Franz Kafka"));
    }

    @Test
    public void testNotAPerson() throws Exception {
        assertNull(getAgeFinder().get("Yoda"));
        assertNull(getAgeFinder().get("Han Solo"));
        assertNull(getAgeFinder().get("Sherlock Holmes"));
    }

    @Test (expected = FactFinderException.class)
    public void testWrongName() throws Exception {
        getAgeFinder().get("asdfasdf");
    }

    private AgeFinder getAgeFinder() {
        return new AgeFinder();
    }
}