package parser.impl;

import com.google.common.collect.Lists;
import parser.ParseException;
import parser.QuestionCharacteristics;
import parser.QuestionParser;
import question.pattern.QuestionPattern;
import question.pattern.impl.HowTermIsFocusQuestion;
import question.pattern.impl.WhatIsTermOfFocusQuestion;

import java.util.List;

/**
 * Created by prasad on 15/12/2014.
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
