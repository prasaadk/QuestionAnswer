package org.qa.answer.extraction.finder;

import org.qa.answer.extraction.finder.impl.FactFinderFactoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FactFinderFactoryImplTest {

    private FactFinderFactory m_factory;
    @Before
    public void setUp() throws Exception {
        m_factory = new FactFinderFactoryImpl();
    }

    @Test
    public void testGetFactFinderFor() throws Exception {
        assertEquals("age should match","61",m_factory.getFactFinderFor("how old").get("Tony Blair"));
        assertEquals("birth place should match","Edinburgh, Scotland",
                m_factory.getFactFinderFor("birth place").get("Tony Blair"));
    }
}