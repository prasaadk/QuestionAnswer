package nlp;

import com.google.common.collect.Lists;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by prasad on 30/11/14.
 */
public class UtteranceHandler {

    private static final Pattern WHAT_IS_PREDICATE_OF_SUBJECT_PATTERN = Pattern.compile("what is the ([\\w ]+) of ([\\w ]+)");
    private static final Pattern HOW_PREDICATE_IS_SUBJECT_PATTERN = Pattern.compile("(how [\\w]+) is ([\\w ]+)?");

    private static final List<Pattern> GRAMMAR = Lists.newArrayList(WHAT_IS_PREDICATE_OF_SUBJECT_PATTERN, HOW_PREDICATE_IS_SUBJECT_PATTERN);


    public static void main(String[] args) throws IOException, URISyntaxException {
        chunker("How old is Tony Blair?");
        chunker("What is the birth place of David Cameron?");
    }

    public static Map<String, String> regexGrammar(String question) {
        return null;
    }

    private static void chunker(String utterance) throws IOException, URISyntaxException {
        POSModel model = new POSModelLoader().load(new File(UtteranceHandler.class.getResource("/models/en-pos-maxent.bin").getFile()));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        String input = utterance;
        ObjectStream<String> lineStream =
                new PlainTextByLineStream(new StringReader(input));

        perfMon.start();
        String[] whitespaceTokenizerLine = null;
        String[] tags = null;
        String line;
        while ((line = lineStream.read()) != null) {

            whitespaceTokenizerLine = WhitespaceTokenizer.INSTANCE.tokenize(line);
            tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            System.out.println(sample.toString());

            perfMon.incrementCounter();
        }
        perfMon.stopAndPrintFinalResult();

        // chunker

        ChunkerModel cModel = new ChunkerModel(UtteranceHandler.class.getResourceAsStream("/models/en-chunker.bin"));

        ChunkerME chunkerME = new ChunkerME(cModel);
        String result[] = chunkerME.chunk(whitespaceTokenizerLine, tags);

        for (String s : result) {
            System.out.println(s);
        }

        Span[] span = chunkerME.chunkAsSpans(whitespaceTokenizerLine, tags);
        for (Span s : span) {
            System.out.println(s.toString());
        }

    }
}
