package utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import answer.extraction.lookup.mapping.ArrayOfResult;
import answer.extraction.lookup.mapping.Result;
import org.apache.commons.io.IOUtils;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.query.*;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sparql.SPARQLRepository;

import javax.xml.bind.*;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by prasad on 30/11/14.
 */
public class DBPediaUtils {

    public static final String DBPEDIA_SPARQL_ENDPOINT_URL = "http://dbpedia.org/sparql";
    public static final String DBPEDIA_LOOKUP_URL = "http://lookup.dbpedia.org/api/search.asmx/KeywordSearch?" +
            "QueryClass=&MaxHits=5&QueryString=";

    /**
     * This method looks up the searchString on DBPedia and returns the best results
     */
    public static URI lookup(String searchString) throws DBPediaLookupException {

        try {
            URL lookupURI = new URL(DBPEDIA_LOOKUP_URL + URLEncoder.encode(searchString,"UTF-8"));

            JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfResult.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ArrayOfResult arrayOfResult = (ArrayOfResult) jaxbUnmarshaller.unmarshal(
                    new StringReader(IOUtils.toString(lookupURI)));

            List<Result> results = arrayOfResult.getResults();
            if(results==null || results.size() == 0) {
                return null;
            }
            Result topResult = results.get(0);
            return RDFUtils.uri(topResult.getURI());

        } catch (MalformedURLException e) {
            throw new DBPediaLookupException("Invalid Lookup URI. Failed to perform DBPedia lookup for '"+searchString+"'", e);
        } catch (IOException e) {
            throw new DBPediaLookupException("Unable to look up DBpedia for search string '"+searchString+"'", e);
        } catch (MarshalException e) {
            throw new DBPediaLookupException("Unable to marshal DBPedia lookup response '"+searchString+"'", e);
        } catch (ValidationException e) {
            throw new DBPediaLookupException("Unable to look up dbpedia for search string '"+searchString+"'", e);
        } catch (JAXBException e) {
            throw new DBPediaLookupException("Unable to look up dbpedia for search string '"+searchString+"'", e);
        }
    }

    public static Collection<Map<String, String>> queryDBPedia(String query, Map<String, Value> params)
            throws QueryException {
        Repository repository = new SPARQLRepository(DBPEDIA_SPARQL_ENDPOINT_URL);
        try {
            repository.initialize();
        } catch (RepositoryException e) {
            throw new QueryException("Unable to initialise DBPedia sparql endpoint");
        }
        RepositoryConnection connection = null;
        try {
            connection = repository.getConnection();
            TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, query);
            for(Map.Entry<String, Value> entry: params.entrySet()) {
                tupleQuery.setBinding(entry.getKey(), entry.getValue());
            }

            TupleQueryResult result = tupleQuery.evaluate();
            List<Map<String, String>> rows = Lists.newArrayList();
            if(result.hasNext()) {
                Map<String, String> returnMap = Maps.newHashMap();
                BindingSet next = result.next();
                Iterator<Binding> bindingIterator = next.iterator();

                while(bindingIterator.hasNext()) {
                    Binding binding = bindingIterator.next();
                    returnMap.put(binding.getName(), binding.getValue().stringValue());
                }
                rows.add(returnMap);
            }
            return rows;
        } catch (RepositoryException e) {
            throw new QueryException("Failed to query DBPedia", e);
        } catch (MalformedQueryException e) {
            throw new QueryException("Failed to query DBPedia, due to invalid DBPedia URL", e);
        } catch (QueryEvaluationException e) {
            throw new QueryException("Failed to query DBPedia", e);
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
    }
}
