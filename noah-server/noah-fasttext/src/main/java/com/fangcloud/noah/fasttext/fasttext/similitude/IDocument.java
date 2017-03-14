package com.fangcloud.noah.fasttext.fasttext.similitude;

public interface IDocument {

    public Object getWordTerms();

    public int getDocumentLength();

    public SimilitudeText getSimilitudeText();

}