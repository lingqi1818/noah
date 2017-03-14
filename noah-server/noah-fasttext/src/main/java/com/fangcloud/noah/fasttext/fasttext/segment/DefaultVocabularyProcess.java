package com.fangcloud.noah.fasttext.fasttext.segment;


import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yihan.zhaoyh@alibaba-inc.com>Zhao Yihan</a>
 */
public class DefaultVocabularyProcess implements VocabularyProcess {

    public List<InternalElement> postProcess(List<char[]> wordList) {
        List<InternalElement> elements = new ArrayList<InternalElement>(wordList.size());
        for (char[] cs : wordList) {
            elements.add(new InternalElement(cs));
        }
        return elements;
    }

}
