package answer.extraction.lookup.mapping;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by prasad on 30/11/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement( name = "ArrayOfResult", namespace="http://lookup.dbpedia.org/" )
public class ArrayOfResult {

    @XmlElement(name = "Result", type = Result.class, namespace="http://lookup.dbpedia.org/")
    private List<Result> Results = Lists.newArrayList();

    public List<Result> getResults() {
        return this.Results;
    }

    public void setResults(List<Result> results) {
        this.Results = results;
    }
}
