package org.qa.question.parser;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prasad on 15/12/2014.
 */
public class QuestionCharacteristics extends HashMap<String, String> {

    public static final String TERM = "term";
    public static final String FOCUS = "focus";

    public String getTerm() {
        return get(TERM);
    }

    public String getFocus() {
        return get(FOCUS);
    }

}
