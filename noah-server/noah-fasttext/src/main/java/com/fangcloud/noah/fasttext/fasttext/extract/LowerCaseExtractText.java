package com.fangcloud.noah.fasttext.fasttext.extract;

public class LowerCaseExtractText implements ExtractText {

    public String getText(String src) {
        if (src == null) {
            return src;
        }
        String dest = null;
        if (src != null) {
            dest = src.toLowerCase();
        }
        return dest;
    }
}