package mapping;

import javax.xml.bind.annotation.*;

/**
 * Created by prasad on 30/11/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement (name = "Class")
public class Class {
    private String Label;
    private String URI;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        this.Label = label;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
