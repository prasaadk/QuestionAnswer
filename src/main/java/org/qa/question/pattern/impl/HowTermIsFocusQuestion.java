package org.qa.question.pattern.impl;

import org.qa.parser.QuestionCharacteristics;
import org.qa.question.pattern.QuestionPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This pattern class represents a "How <term> is <focus>" category question
 * Created by prasad on 15/12/2014.
 */
public class HowTermIsFocusQuestion implements QuestionPattern {

    private static final String TERM = "term";
    private static final String FOCUS = "focus";

    private static final Pattern PATTERN
            = Pattern.compile("(?<term>how [\\w ]+) is (?<focus>[\\w ]+)\\??", Pattern.CASE_INSENSITIVE);

    @Override
    public QuestionCharacteristics match(String utterance) {
        QuestionCharacteristics matchedCharacteristics = new QuestionCharacteristics();
        Matcher matcher = PATTERN.matcher(utterance);
        if(!matcher.matches()) {
            return matchedCharacteristics;
        }

        matchedCharacteristics.put(QuestionCharacteristics.TERM, matcher.group(TERM).toLowerCase());
        matchedCharacteristics.put(QuestionCharacteristics.FOCUS, matcher.group(FOCUS).toLowerCase());

        return matchedCharacteristics;
    }
}
