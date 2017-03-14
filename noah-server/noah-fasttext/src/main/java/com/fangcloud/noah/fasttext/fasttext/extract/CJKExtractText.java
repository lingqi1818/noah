package com.fangcloud.noah.fasttext.fasttext.extract;


/**
 * 只获得CJK字符群
 *
 * @author sdh5724 2008-3-27 下午01:53:21
 */

public class CJKExtractText implements ExtractText {

    /**
     * 提取字符串中的所有汉字字符。
     */
    public String getText(String content) {
        if (content == null) {
            return content;
        }
        StringBuilder sb = new StringBuilder(content.length());
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= CharNormalization.CJK_UNIFIED_IDEOGRAPHS_START || c < CharNormalization.CJK_UNIFIED_IDEOGRAPHS_END) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}