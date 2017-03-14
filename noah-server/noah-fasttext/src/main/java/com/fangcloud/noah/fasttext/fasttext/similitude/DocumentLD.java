package com.fangcloud.noah.fasttext.fasttext.similitude;


import java.util.ArrayList;
import java.util.List;

import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

public class DocumentLD implements IDocument {

    int hashword[];

    public DocumentLD(List<WordTerm> wordTerms){
        // 删除非词汇信息
        List<WordTerm> frequnce = new ArrayList<WordTerm>();
        for (WordTerm wordTerm : wordTerms) {
            if (wordTerm.position != -1) { // skip not word
                frequnce.add(wordTerm);
            }
        }// end for
        hashword = new int[frequnce.size()];
        int i = 0;
        for (WordTerm word2 : frequnce) {
            hashword[i] = word2.position;
            i++;
        }
    }//

    public Object getWordTerms() {
        return hashword;
    }

    public SimilitudeText getSimilitudeText() {

        return new SimilitudeUsingLD();
    }

    public Object getAttach() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAttach(Object attach) {
        // TODO Auto-generated method stub

    }

    public int getDocumentLength() {
        // TODO Auto-generated method stub
        return 0;
    }

}