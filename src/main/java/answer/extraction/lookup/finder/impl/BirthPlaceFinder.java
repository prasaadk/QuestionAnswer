package answer.extraction.lookup.finder.impl;

import org.openrdf.model.Resource;
import org.openrdf.model.URI;
import utils.DBPediaLookupException;
import utils.DBPediaUtils;
import utils.QueryException;
import utils.RDFUtils;
import answer.extraction.lookup.finder.FactFinder;
import answer.extraction.lookup.finder.FactFinderException;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.openrdf.model.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by prasad on 15/12/2014.
 */
public class BirthPlaceFinder implements FactFinder {

    public static final String BIRTH_PLACE_QUERY_PATH = "/BirthPlaceQuery.sparql";

    public static final String SUBJECT = "subject";
    public static final String CITY = "city";
    public static final String COUNTRY = "country";

    @Override
    public String get(String focus) throws FactFinderException {
        return findPlaceOfBirth(lookup(focus));
    }

    private static String findPlaceOfBirth(Resource person) throws FactFinderException {
        Map<String, Value> params = Maps.newHashMap();
        params.put(SUBJECT, person);

        Collection<Map<String, String>> result = null;
        try {
            result = DBPediaUtils.queryDBPedia(
                    IOUtils.toString(BirthPlaceFinder.class.getResourceAsStream(BIRTH_PLACE_QUERY_PATH)), params);
        } catch (IOException e) {
            throw new FactFinderException("Unable to find birth place for '"+person+"'", e);
        } catch (QueryException e) {
            throw new FactFinderException("Unable to find birth place for '"+person+"'", e);
        }

        for(Map<String, String> value: result) {
            String city = value.get(CITY);
            String country = value.get(COUNTRY);
            if(StringUtils.isBlank(country)) {
                return city;
            } else {
                return city + ", " + country;
            }
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
