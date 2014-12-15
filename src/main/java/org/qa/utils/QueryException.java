package org.qa.utils;

/**
 * Created by prasad on 15/12/2014.
 */
public class QueryException extends Exception {

    public QueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryException(String message) {
        super(message);
    }
}
