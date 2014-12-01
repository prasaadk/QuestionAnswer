package mapping;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by prasad on 30/11/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement( name = "Result" , namespace="http://lookup.dbpedia.org/")
public class Result {
    private String Label;
    private String URI;
    private String Description;

    @XmlElement(name = "Class", type = Class.class)
    private List<Class> Classes;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        label = label;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public List<Class> getClasses() {
        return Classes;
    }

    public void setClasses(List<Class> classes) {
        this.Classes = classes;
    }
}
