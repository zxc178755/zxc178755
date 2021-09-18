import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosSimilarity {
    protected Map<String, int[]> wordMap;

    public CosSimilarity() {
        wordMap = new HashMap<>();
    }

    /**
     *
     */
    protected void textSegment(List<String> Article, int index) {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokenList;
        for (String sentence : Article) {

            segTokenList = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
            putWordtoMap(segTokenList, index);
        }
    }

    protected void putWordtoMap(List<SegToken> segTokenList, int index) {
        for (SegToken segToken : segTokenList) {
            String word = segToken.word;
            if (wordMap.containsKey(word)) {
                wordMap.get(word)[index]++;
            } else {
                int[] count = new int[2];
                count[0] = index == 0 ? 1 : 0;
                count[1] = index == 1 ? 1 : 0;
                wordMap.put(word, count);
            }
        }
    }

    protected double calculateCos() {
        double top = calculateTop();
        double bottom = calculateBottom();
        return top / bottom;
    }

    protected double calculateTop() {
        double top = 0.0;
        for (int[] count : wordMap.values())
            top += count[0] * count[1];
        return top;
    }

    protected double calculateBottom() {
        double num1 = 0.0;
        double num2 = 0.0;
        double sqrt1 = 0.0;
        double sqrt2 = 0.0;
        for (int[] count : wordMap.values()) {
            num1 += count[0] * count[0];
            num2 += count[1] * count[1];
        }
        sqrt1 = Math.sqrt(num1);
        sqrt2 = Math.sqrt(num2);
        return sqrt1 * sqrt2;
    }

    public void finish() {
        wordMap = null;
    }
}


