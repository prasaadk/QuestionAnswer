package org.qa.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.openrdf.model.Value;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.qa.utils.RDFUtils.uri;

public class DBPediaUtilsTest {

    @Test
    public void testDBPediaLookup() throws Exception {
        assertEquals(uri("http://dbpedia.org/resource/David_Cameron"),DBPediaUtils.lookup("David Cameron"));
        assertEquals(uri("http://dbpedia.org/resource/David_Cameron"),DBPediaUtils.lookup("david cameron"));
        assertEquals(uri("http://dbpedia.org/resource/Tony_Blair"),DBPediaUtils.lookup("blair tony"));
        assertEquals(uri("http://dbpedia.org/resource/Tony_Blair"),DBPediaUtils.lookup("Tony Blair"));
        assertEquals(uri("http://dbpedia.org/resource/Boris_Johnson"),DBPediaUtils.lookup("Boris Johnson"));
        assertEquals(uri("http://dbpedia.org/resource/Margaret_Thatcher"),DBPediaUtils.lookup("Margaret Thatcher"));
        assertEquals(uri("http://dbpedia.org/resource/Convergent_evolution"),DBPediaUtils.lookup("Convergent Evolution"));
    }

    @Test
    public void testQueryDBPedia() throws Exception {
        String ageQuery = IOUtils.toString(DBPediaUtils.class.getResourceAsStream("/AgeQuery.sparql"));

        Map<String, Value> params = Maps.newHashMap();
        params.put("subject", DBPediaUtils.lookup("Margaret Thatcher"));

        Collection<Map<String, String>> expected = Lists.newArrayList();
        expected.add(Collections.singletonMap("age","87"));
        assertEquals("should be equal", expected, DBPediaUtils.queryDBPedia(ageQuery, params));
    }

}