package org.qa.utils.mapping;

import javax.xml.bind.annotation.*;

/**
 * Created by prasad on 15/12/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement (name = "Class", namespace="http://lookup.dbpedia.org/")
public class Class {
    @XmlElement(name = "Label", type = String.class, namespace="http://lookup.dbpedia.org/")
    private String Label;

    @XmlElement(name = "URI", type = String.class, namespace="http://lookup.dbpedia.org/")
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
