package org.qa.utils.mapping;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.*;
import java.lang.*;
import java.util.List;

/**
 * Created by prasad on 30/11/14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement( name = "Result" , namespace="http://lookup.dbpedia.org/")
public class Result {

    @XmlElement(name = "Label", type = String.class, namespace="http://lookup.dbpedia.org/")
    private String Label;

    @XmlElement(name = "URI", type = String.class, namespace="http://lookup.dbpedia.org/")
    private String URI;

    @XmlElement(name = "Description", type = String.class, namespace="http://lookup.dbpedia.org/")
    private String Description;

    @XmlElementWrapper( name = "Classes", namespace="http://lookup.dbpedia.org/")
    @XmlElement(name = "Class", type = Class.class, namespace="http://lookup.dbpedia.org/")
    private List<Class> Classes = Lists.newArrayList();

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
