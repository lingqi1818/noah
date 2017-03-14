package com.fangcloud.noah.fasttext.fasttext.extract;


import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

import com.fangcloud.noah.fasttext.fasttext.psoriasis.MappedCharArray;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

@Deprecated
public class SwingHyperExtractText implements ExtractText {

    /**
     * 利用Swing html parser过滤html tag
     */
    public String getText(String html) {
        if (html == null || html.length() == 0) {
            return html;
        }
        Reader rd = new StringReader(html);
        ParserDelegator delegator = new ParserDelegator();
        // the third parameter is TRUE to ignore charset directive
        try {
            HTMLCallback cb = new HTMLCallback();
            delegator.parse(rd, cb, true);
            return cb.toString();
        } catch (IOException e) {
        }
        return null;
    }

    private class HTMLCallback extends HTMLEditorKit.ParserCallback {

        private StringBuilder   buffer  = new StringBuilder();
        private MappedCharArray mca;
        private int             current = 0;

        HTMLCallback(){
            super();
        }

        HTMLCallback(MappedCharArray mca){
            super();
            this.mca = mca;
        }

        @Override
        public void handleText(char[] text, int pos) {
            if (mca != null) {
                int[] map = mca.getMap();
                char[] target = mca.getTarget();
                for (char ch : text) {
                    target[current] = ch;
                    if (pos < map.length) {// html不规则时可能会出错。
                        map[current++] = map[pos++];
                    }
                }
            } else {
                buffer.append(text);
            }
        }

        public int getCharCount() {
            return current;
        }

        @Override
        public String toString() {
            return this.buffer.toString();
        }

        public MappedCharArray getResult() {
            return mca;
        }
    }
}