package org.qa.utils;

import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.util.Literals;

import java.util.Date;

/**
 * Created by prasad on 15/12/2014.
 */
public class RDFUtils {

    public static Literal literal(Date date) {
        return Literals.createLiteral(ValueFactoryImpl.getInstance(), date);
    }

    public static URI uri(String uri) {
        return ValueFactoryImpl.getInstance().createURI(uri);
    }
}
