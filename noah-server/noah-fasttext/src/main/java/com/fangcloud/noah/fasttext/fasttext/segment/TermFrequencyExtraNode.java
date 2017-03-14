package com.fangcloud.noah.fasttext.fasttext.segment;


import java.io.Serializable;

public class TermFrequencyExtraNode implements TermExtraInfo, Serializable {

    private static final long serialVersionUID = 1040123220020582878L;
    public int                termFrequency;

    public TermFrequencyExtraNode(int freq){
        this.termFrequency = freq;
    }
}
