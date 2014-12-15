package org.qa.parser.impl;

import com.google.common.collect.Lists;
import org.qa.parser.ParseException;
import org.qa.parser.QuestionCharacteristics;
import org.qa.parser.QuestionParser;
import org.qa.question.pattern.QuestionPattern;
import org.qa.question.pattern.impl.HowTermIsFocusQuestion;
import org.qa.question.pattern.impl.WhatIsTermOfFocusQuestion;

import java.util.List;

/**
 * Created by prasad on 15/12/2014.
 */

/**
 * This is a very simple syntactic question parser, using custom QuestionPattern as rules for parsing questions.
 */
public class SyntacticQuestionParser implements QuestionParser {

    List<QuestionPattern> questionPatterns;

    public SyntacticQuestionParser() {
        questionPatterns = Lists.newArrayList(
                new HowTermIsFocusQuestion(),
                new WhatIsTermOfFocusQuestion()
        );
    }


    @Override
    public QuestionCharacteristics parse(String question) throws ParseException {
        for(QuestionPattern questionPattern : questionPatterns) {
            QuestionCharacteristics match = questionPattern.match(question);
            if(!match.isEmpty()) {
                return match;
            }
        }

        throw new ParseException("Unable to parse question '"+question+"'");
    }

}
