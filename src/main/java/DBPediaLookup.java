import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mapping.ArrayOfResult;
import mapping.Result;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.util.Literals;
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
public class DBPediaLookup {

    public static final String DBPEDIA_URL = "http://dbpedia.org/sparql";

    public static final String AGE_QUERY = "PREFIX dbpedia-res: <http://dbpedia.org/resource/>\n" +
            "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
            "\n" +
            "SELECT ?age\n" +
            "WHERE\n" +
            "{\n" +
            "?subject dbpedia-owl:birthDate ?birthDate .\n" +
            "\n" +
            "BIND ( year(?currentDate)-year(?birthDate) as ?age)\n" +
            "\n" +
            "}";

    public static final String BIRTH_PLACE_QUERY = "PREFIX dbpedia-res: <http://dbpedia.org/resource/>\n" +
            "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
            "PREFIX dbprop: <http://dbpedia.org/property/>\n" +
            "\n" +
            "SELECT distinct ?city ?country \n" +
            "WHERE\n" +
            "{\n" +
            "\n" +
            "\t{\n" +
            "\n" +
            "\t\t?subject dbpedia-owl:birthPlace ?birthPlace .\n" +
            "                \n" +
            "                # Filter out anything that's not a place. \n" +
            "                ?birthPlace rdf:type dbpedia-owl:Place .\n" +
            "\n" +
            "\t\tOPTIONAL { \n" +
            "\t\t    #This is an optional filter to ignore County's\n" +
            "\t\t    ?birthPlace dbprop:centre ?centre \n" +
            "\t\t}\n" +
            "\n" +
            "\t\t?birthPlace rdfs:label ?city .\n" +
            "\n" +
            "\n" +
            "\t\tOPTIONAL {\n" +
            "\t\t    ?birthPlace dbpedia-owl:country [rdfs:label ?country] .\n" +
            "\t\t}\n" +
            "\n" +
            "\t\tFILTER(!bound(?centre))\n" +
            "\t\tFILTER (langMatches(lang(?city),\"en\"))\n" +
            "\t\tFILTER (langMatches(lang(?country),\"en\"))\n" +
            "\n" +
            "\t} \n" +
            "\tUNION\n" +
            "\t{\n" +
            "\t\t?subject dbprop:birthPlace ?city .\n" +
            "                FILTER(!isURI(?city))\n" +
            "\t}\n" +
            "        UNION\n" +
            "        {\n" +
            "                ?subject dbprop:birthPlace [rdfs:label ?city] .\n" +
            "                FILTER (langMatches(lang(?city),\"en\")) \n" +
            "        }\n" +
            "\n" +
            "}";

    /**
     * This method looks up the searchString on DBPedia and returns the best results
     */
    public static String lookup(String searchString) {

        try {
            URL lookupURI = new URL("http://lookup.dbpedia.org/api/search.asmx/PrefixSearch?" +
                    "QueryClass=&MaxHits=5&" +
                    "QueryString=" + URLEncoder.encode(searchString,"UTF-8"));

            JAXBContext jaxbContext = JAXBContext.newInstance(ArrayOfResult.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ArrayOfResult arrayOfResult = (ArrayOfResult) jaxbUnmarshaller.unmarshal(
                    new StringReader(IOUtils.toString(lookupURI)));

            List<Result> results = arrayOfResult.getResults();
            if(results==null || results.size() == 0) {
                return null;
            }
            Result topResult = results.get(0);
            return topResult.getURI();

        } catch (MalformedURLException e) {
            //TODO: Handle exceptions
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Map<String, String>> queryDBPedia(String query, Map<String, Value> params) {
        Repository repository = new SPARQLRepository(DBPEDIA_URL);
        try {
            repository.initialize();
        } catch (RepositoryException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        } catch (MalformedQueryException e) {
            e.printStackTrace();
        } catch (QueryEvaluationException e) {
            e.printStackTrace();
        } finally {
            try {
                if(connection != null) {
                    connection.close();
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String [] args) {

        System.out.println("URI for Tony Blair is: " + lookup("Tony Blair"));
        System.out.println("URI for David Cameron is: " + lookup("David Cameron"));
        System.out.println("URI for Gordon Brown is: " + lookup("Gordon Brown"));
        System.out.println("URI for Barack Obama is: " + lookup("Barack Obama"));
        System.out.println("URI for Boris Johnson is: " + lookup("Boris Johnson"));
        System.out.println("URI for Manmohan Singh is: " + lookup("Manmohan singh"));


        System.out.println("Tony Blair's age is: " + findAge(lookup("Tony Blair")));
        System.out.println("Tony Blair's place of birth is: " + findPlaceOfBirth(lookup("Tony Blair")));

        System.out.println("Premiership of Tony Blair's age is: " + findAge("http://dbpedia.org/resource/Premiership_of_Tony_Blair"));
        System.out.println("Premiership of Tony Blair's place of birth is: " + findPlaceOfBirth("http://dbpedia.org/resource/Premiership_of_Tony_Blair"));

        System.out.println("David Cameron's age is: " + findAge(lookup("David Cameron")));
        System.out.println("David Cameron's place of birth is: " + findPlaceOfBirth(lookup("David Cameron")));

        System.out.println("Gordon Brown's age is: " + findAge(lookup("Gordon Brown")));
        System.out.println("Gordon Brown's place of birth is: " + findPlaceOfBirth(lookup("Gordon Brown")));

        System.out.println("Barack Obama's age is: " + findAge(lookup("Barack Obama")));
        System.out.println("Barack Obama's place of birth is: " + findPlaceOfBirth(lookup("Barack Obama")));

        System.out.println("Ed Milliband's age is: " + findAge(lookup("Ed Milliband")));
        System.out.println("Ed Milliband's place of birth is: " + findPlaceOfBirth(lookup("Ed Milliband")));

        System.out.println("Boris Johnson's age is: " + findAge(lookup("Boris Johnson")));
        System.out.println("Boris Johnson's place of birth is: " + findPlaceOfBirth(lookup("Boris Johnson")));

        System.out.println("Manmohan Singh's age is: " + findAge(lookup("Manmohan Singh")));
        System.out.println("Manmohan Singh's place of birth is: " + findPlaceOfBirth(lookup("Manmohan Singh")));

    }

    private static String findAge(String person) {
        Map<String, Value> params = Maps.newHashMap();
        params.put("currentDate", literal(new Date()));
        params.put("subject", uri(person));

        Collection<Map<String, String>> result = null;
        try {
            result = queryDBPedia(IOUtils.toString(DBPediaLookup.class.getResourceAsStream("/AgeQuery.sparql")), params);
        } catch (IOException e) {
            //TODO: Unhandled exception
            e.printStackTrace();
        }

        for(Map<String, String> value: result) {
            return value.get("age");
        }
        return null;
    }

    private static String findPlaceOfBirth(String person) {
        Map<String, Value> params = Maps.newHashMap();
        params.put("subject", uri(person));

        Collection<Map<String, String>> result = null;
        try {
            result = queryDBPedia(IOUtils.toString(DBPediaLookup.class.getResourceAsStream("/BirthPlaceQuery.sparql")), params);
        } catch (IOException e) {
            //TODO: Unhandled exception
            e.printStackTrace();
        }

        for(Map<String, String> value: result) {
            String city = value.get("city");
            String country = value.get("country");
            if(StringUtils.isBlank(country)) {
                return city;
            } else {
                return city + ", " + country;
            }
        }
        return null;
    }

    public static Literal literal(Date date) {
        return Literals.createLiteral(ValueFactoryImpl.getInstance(), date);
    }

    public static URI uri(String uri) {
        return ValueFactoryImpl.getInstance().createURI(uri);
    }


}
