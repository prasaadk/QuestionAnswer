package org.qa.answer.extraction.finder.impl;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import org.qa.answer.extraction.finder.FactFinder;
import org.qa.answer.extraction.finder.FactFinderException;
import org.qa.utils.DBPediaLookupException;
import org.qa.utils.DBPediaUtils;
import org.qa.utils.QueryException;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.openrdf.model.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by prasad on 15/12/2014.
 */
public class AgeFinder implements FactFinder {

    public static final String SUBJECT = "subject";
    public static final String AGE = "age";
    public static final String AGE_QUERY_QUERY_PATH = "/AgeQuery.sparql";

    @Override
    public String get(String focus) throws FactFinderException {
        URI uri = lookup(focus);
        if(uri == null) {
            throw new FactFinderException("Unable to find a reference URI for '"+focus+"' using DBPedia lookup.");
        }
        return findAge(uri);
    }

    private static String findAge(Resource person) throws FactFinderException {
        Map<String, Value> params = Maps.newHashMap();
        params.put(SUBJECT, person);

        Collection<Map<String, String>> result = null;
        try {
            result = DBPediaUtils.queryDBPedia(
                    IOUtils.toString(AgeFinder.class.getResourceAsStream(AGE_QUERY_QUERY_PATH)), params);
        } catch (IOException e) {
            throw new FactFinderException("Unable to find age for '"+person+"'", e);
        } catch (QueryException e) {
            throw new FactFinderException("Unable to find age for '"+person+"'", e);
        }

        for(Map<String, String> value: result) {
            return value.get(AGE);
        }
        return null;
    }

    private URI lookup(String concept) throws FactFinderException {
        try {
            return DBPediaUtils.lookup(concept);
        } catch (DBPediaLookupException e) {
            throw new FactFinderException("Unable to lookup for '"+concept+"'", e);
        }
    }
}
