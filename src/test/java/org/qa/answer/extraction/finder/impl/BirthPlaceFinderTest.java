package org.qa.answer.extraction.finder.impl;

import org.qa.answer.extraction.finder.FactFinder;
import org.qa.answer.extraction.finder.FactFinderException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BirthPlaceFinderTest {
    @Test
    public void testPeopleFromDifferentGeographies() throws Exception {
        Assert.assertEquals("London, United Kingdom", birthPlaceFinder().get("David Cameron"));
        Assert.assertEquals("Mvezo, South Africa", birthPlaceFinder().get("Nelson Mandela"));
        Assert.assertEquals("Grantham, United Kingdom", birthPlaceFinder().get("Margaret Thatcher"));
        Assert.assertEquals("Bohemia, Czech Republic", birthPlaceFinder().get("Franz Kafka"));
        Assert.assertEquals("Edinburgh, Scotland", birthPlaceFinder().get("Tony Blair"));
        Assert.assertEquals("Woodstock, Oxfordshire, United Kingdom", birthPlaceFinder().get("Winston Churchill"));
        Assert.assertEquals("Vadnagar, India", birthPlaceFinder().get("Narendra Modi"));
    }

    @Test
    public void testFictionalCharacter() throws Exception {
        assertNull(birthPlaceFinder().get("Sherlock Holmes"));
    }

    @Test
    public void testNotAPerson() throws Exception {
        assertNull(birthPlaceFinder().get("Eiffel Tower"));
        assertNull(birthPlaceFinder().get("London"));
    }

    @Test (expected = FactFinderException.class)
    public void testWrongName() throws Exception {
        birthPlaceFinder().get("asdfasdf");
    }

    private FactFinder birthPlaceFinder() {
        return new BirthPlaceFinder();
    }
}