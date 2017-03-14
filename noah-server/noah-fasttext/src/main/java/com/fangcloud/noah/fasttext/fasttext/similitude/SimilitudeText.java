package com.fangcloud.noah.fasttext.fasttext.similitude;

/**
 * 相似度比较
 *
 * @author sdh5724 2008-2-25 上午10:57:39
 */
public interface SimilitudeText {

    public double similitudeValue(IDocument src, IDocument dest);
}