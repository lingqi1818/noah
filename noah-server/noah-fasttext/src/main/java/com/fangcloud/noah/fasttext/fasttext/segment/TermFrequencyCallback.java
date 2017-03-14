package com.fangcloud.noah.fasttext.fasttext.segment;

import java.util.ArrayList;
import java.util.List;

/**
 * 带词频信息的词汇表预处理
 *
 * @author sdh5724 2008-3-31 上午12:34:00
 */
public class TermFrequencyCallback implements VocabularyProcess {

    public List<InternalElement> postProcess(List<char[]> lines) {
        // find last space
        List<InternalElement> list = new ArrayList<InternalElement>(lines.size());
        for (char[] cs : lines) {
            String s = String.valueOf(cs);
            int idx = s.lastIndexOf(' ');
            if (idx > 0) {

                try {
                    TermExtraInfo node = new TermFrequencyExtraNode(Integer.valueOf(s.substring(idx + 1)));
                    list.add(new InternalElement(s.substring(0, idx).toCharArray(), node));
                } catch (Exception e) {
                    System.out.println(cs);
                }

            }
        }
        return list;
    }
}