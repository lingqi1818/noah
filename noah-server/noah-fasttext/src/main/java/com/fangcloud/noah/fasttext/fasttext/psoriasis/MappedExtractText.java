package com.fangcloud.noah.fasttext.fasttext.psoriasis;


import com.fangcloud.noah.fasttext.fasttext.extract.ExtractText;

public interface MappedExtractText extends ExtractText {

    /**
     * 对给定的字符串按照某种算法进行转换和过滤工作， 并且保留目标字符串和源字符串之间的映射关系。
     *
     * @param src 源字符串
     * @return 目标字符串
     */
    public MappedCharArray getText(MappedCharArray src);

    /**
     * 对给定的字符串按照某种算法进行转换和过滤工作， 并且保留目标字符串和源字符串之间的映射关系。
     *
     * @param src 源字符串
     * @param ignoreCase 全部转换为小写
     * @return 目标字符串
     */
    public MappedCharArray getText(MappedCharArray src, boolean ignoreCase);

    /**
     * 对给定的字符串按照某种算法进行转换和过滤工作。
     *
     * @param src 源字符串
     * @param ignoreCase 全部转换为小写
     * @return 转换后的字符串
     */
    public String getText(String src, boolean ignoreCase);

}