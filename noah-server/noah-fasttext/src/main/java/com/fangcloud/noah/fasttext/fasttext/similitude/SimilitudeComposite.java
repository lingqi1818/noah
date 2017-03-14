package com.fangcloud.noah.fasttext.fasttext.similitude;


public class SimilitudeComposite implements SimilitudeText {

    private SimilitudeText cos = new SimilitudeUsingCos();
    private SimilitudeText gjc = new SimilitudeUsingGJC();
    private SimilitudeText ld  = new SimilitudeUsingLD();

    private SimilitudeType compareType;

    public SimilitudeComposite(SimilitudeType type){
        this.compareType = type;
    }

    public double similitudeValue(IDocument src, IDocument dest) {
        if (compareType == SimilitudeType.COS_TYPE) {
            return cos.similitudeValue(src, dest);
        } else if (compareType == SimilitudeType.GJC_TYPE) {
            return gjc.similitudeValue(src, dest);
        } else if (compareType == SimilitudeType.LD_TYPE) {
            return ld.similitudeValue(src, dest);
        }
        return 0;
    }

}
