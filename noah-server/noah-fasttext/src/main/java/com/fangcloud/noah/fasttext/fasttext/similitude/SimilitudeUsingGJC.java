package com.fangcloud.noah.fasttext.fasttext.similitude;

import java.util.Map;
import java.util.Set;

import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

public class SimilitudeUsingGJC implements SimilitudeText {

    public double similitudeValue(IDocument srcDoc, IDocument destDoc) {
        int srcLen = srcDoc.getDocumentLength();
        int destLen = destDoc.getDocumentLength();
        if (srcLen == 0 || destLen == 0) {
            return 0;
        }
        Map<Integer, WordTerm> src = (Map<Integer, WordTerm>) srcDoc.getWordTerms();
        Map<Integer, WordTerm> dest = (Map<Integer, WordTerm>) destDoc.getWordTerms();
        WordTerm w1, w2;
        double num = 0;
        double d = 0;
        double num3 = 0;
        double count = 0;
        Set<Integer> set = src.keySet().size() > dest.keySet().size() ? dest.keySet() : src.keySet();
        for (Integer i : set) {
            w1 = src.get(i);
            if (w1 != null) {
                w2 = dest.get(i);
                if (w2 != null) {
                    num += w1.eigenvalue * w2.eigenvalue; // d1*c1
                    d += w1.eigenvalue * w1.eigenvalue; // |d1|
                    num3 += w2.eigenvalue * w2.eigenvalue;// |c1|
                    count++;
                }
            }
        }
        if (((count / ((double) src.size())) <= 0.1) || ((count / ((double) dest.size())) <= 0.1)) {
            return 0;
        }
        // 考虑文档长度的对比
        double delta = (srcLen > destLen ? (double) destLen / srcLen : (double) srcLen / destLen);
        return (num / (d + num3 - num)) * delta;
    }

}
