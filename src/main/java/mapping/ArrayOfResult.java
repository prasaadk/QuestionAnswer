package mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by prasad on 30/11/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement( name = "ArrayOfResult", namespace="http://lookup.dbpedia.org/" )
public class ArrayOfResult {

    @XmlElement(name = "Result", type = Result.class)
    private List<Result> Results;

    public List<Result> getResults() {
        return Results;
    }

    public void setResults(List<Result> results) {
        this.Results = results;
    }
}
