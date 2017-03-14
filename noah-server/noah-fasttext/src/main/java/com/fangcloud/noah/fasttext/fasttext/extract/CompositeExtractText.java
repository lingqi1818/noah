package com.fangcloud.noah.fasttext.fasttext.extract;

/**
 * <pre>
 * 1. 转换成简体
 * 2. 转换全角到半角
 * 3. 过滤acsii
 * </pre>
 *
 * @author sdh5724 2008-7-18 下午05:38:44
 */
public class CompositeExtractText implements ExtractText {

    public String getText(String src) {
        if (src == null) {
            return src;
        }
        return CharNormalization.compositeTextConvert(src, true, true, false, true, false,false,false);
    }
}