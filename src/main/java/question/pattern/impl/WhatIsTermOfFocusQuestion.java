package question.pattern.impl;

import parser.QuestionCharacteristics;
import question.pattern.QuestionPattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This question pattern represents a "What is <term> of <focus>" category of question.
 */
public class WhatIsTermOfFocusQuestion implements QuestionPattern {

    private static final String TERM = "term";
    private static final String FOCUS = "focus";

    private static final Pattern PATTERN
            = Pattern.compile("what is (?:the |)(?<term>[\\w ]+) of (?<focus>[\\w ]+)\\??", Pattern.CASE_INSENSITIVE);

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
