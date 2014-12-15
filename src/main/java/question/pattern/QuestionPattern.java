package question.pattern;

import parser.QuestionCharacteristics;

import java.util.Map;
import java.util.Set;

/**
 * Created by prasad on 15/12/2014.
 */
public interface QuestionPattern {

    /**
     * Maps
     * @param utterance
     * @return
     */
    QuestionCharacteristics match(String utterance);

}
