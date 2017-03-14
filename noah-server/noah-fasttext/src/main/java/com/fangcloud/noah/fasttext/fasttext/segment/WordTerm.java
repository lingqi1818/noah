package com.fangcloud.noah.fasttext.fasttext.segment;


/**
 * 分词以后的结果， 有2个含义，如果position == -1, 意味无法分的词
 *
 * @author sdh5724 2008-2-22 下午02:20:48
 */
public class WordTerm {

    /**
     * 在字典中的位置
     */
    public int           position   = -1;
    /**
     * 在输入文本中的位置
     */
    public int           begin;
    /**
     * 该词的长度
     */
    public int           length;

    /**
     * 该词在输入文本出现的频率
     */
    public int           frequency  = 0;
    /**
     * 该词在字典中出现的频率
     */
    public TermExtraInfo termExtraInfo;

    /**
     * 特征值
     */
    public double        eigenvalue = 0;

    @Override
    public String toString() {
        return "[position=" + position + ",begin=" + begin + ",length=" + length + ",termExtraInfo=" + termExtraInfo
                + "]";
    }

    public String toString(char[] src) {
        return "[content=" + String.copyValueOf(src, begin, length) + ",position=" + position + ",begin=" + begin
                + ",length=" + length + ",termExtraInfo=" + termExtraInfo + "]";
    }

}

