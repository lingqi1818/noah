package com.fangcloud.noah.fasttext.fasttext.extract;

public interface ExtractText {

    /**
     * 对给定的字符串按照某种算法进行转换和过滤工作。
     *
     * @param src 源字符串
     * @return 转换后的字符串
     */
    public String getText(String src);
}