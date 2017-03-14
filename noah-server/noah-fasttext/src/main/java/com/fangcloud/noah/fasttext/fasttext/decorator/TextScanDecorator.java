package com.fangcloud.noah.fasttext.fasttext.decorator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.fasttext.fasttext.segment.Darts;
import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

import java.util.*;

/**
 * 能跳过html标签高亮处理类
 *
 * @author <a href=mailto:zhimin.liuzm@alibaba-inc.com>lim</a>
 * @version $Id$
 */
public class TextScanDecorator extends DecoratorText {

    private static final long serialVersionUID  = 2598117699183406328L;

    private final static Logger logger         = LoggerFactory
            .getLogger(TextScanDecorator.class);

    String                    highlightStartTag = DecoratorConstants.DEFAULT_HL_START_TAG;
    String                    highlightEndTag   = DecoratorConstants.DEFAULT_HL_END_TAG;

    protected int             rightTagPosition  = -1;

    protected int             leftTagPosition   = -1;

    public TextScanDecorator(List<String> source, boolean ignoreCase){
        this(new Darts(), source, ignoreCase);
    }

    public TextScanDecorator(Darts darts, List<String> source, boolean ignoreCase){
        if (source == null || source.isEmpty()) {
            logger.error("Keywords shuold be filled");
            throw new IllegalArgumentException(" Keywords shuold be filled");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Darts: " + darts + " source: " + source + " ignoreCase: " + ignoreCase);
        }
        darts = this.darts;
        List<char[]> tokens = new ArrayList<char[]>(source.size());
        for (String word : source) {
            if (word != null && word.length() > 0) {
                word = word.trim();
                if (ignoreCase) {
                    tokens.add(word.toLowerCase().toCharArray());
                } else {
                    tokens.add(word.toCharArray());
                }
            }
        }
        tokens.add(("" + DecoratorConstants.RIGHT_TAG).toCharArray()); // >
        tokens.add(("" + DecoratorConstants.LEFT_TAG).toCharArray()); // <
        darts.build(tokens);
        rightTagPosition = darts.search("" + DecoratorConstants.RIGHT_TAG);
        leftTagPosition = darts.search("" + DecoratorConstants.LEFT_TAG);
    }

    public boolean containTerm(String content, boolean ignoreCase) {

        if (content == null || content.length() == 0) {
            return false;
        }
        if (ignoreCase) {
            content = content.toLowerCase();
        }
        char inputSequence[] = content.toCharArray();
        int pos = 0;
        boolean hasTerm = false;
        while (pos < inputSequence.length) {
            WordTerm w = darts.prefixSearchMax(inputSequence, pos, inputSequence.length - pos);
            if (w != null) {
                if (w.position != rightTagPosition && w.position != leftTagPosition) {
                    hasTerm = true;
                    break;
                }
                pos += w.length;
            } else {
                pos++;
            }
        }
        return hasTerm;
    }

    public List<String> parseTerms(String content, boolean ignoreCase, boolean dupRemove) {
        List<String> terms = new ArrayList<String>();
        if (content == null || content.length() == 0) {
            return terms;
        }
        char orgin[] = content.toCharArray();
        List<WordTerm> foundTerms = parseTerms(content, ignoreCase);
        if (dupRemove) {
            Set<String> set = new HashSet<String>();
            for (WordTerm w : foundTerms) {
                set.add(new String(orgin, w.begin, w.length).toUpperCase());
            }
            terms.addAll(set);
        } else {
            for (WordTerm w : foundTerms) {
                terms.add(new String(orgin, w.begin, w.length));
            }
        }
        return terms;
    }

    public List<WordTerm> parseTerms(String content, boolean ignoreCase) {
        List<WordTerm> foundTerms = new LinkedList<WordTerm>();
        if (content == null || content.length() == 0) {
            return foundTerms;
        }
        char orgin[] = content.toCharArray();
        String inputContent = ignoreCase ? content.toLowerCase() : content;
        char inputSequence[] = inputContent.toCharArray();
        int pos = 0;
        while (pos < inputSequence.length) {
            WordTerm w = darts.prefixSearchMax(inputSequence, pos, inputSequence.length - pos);
            if (w != null) {
                foundTerms.add(w);
                pos += w.length;
            } else {
                pos++;
            }
        }
        removeTagTerms(orgin, foundTerms);
        return foundTerms;
    }

    public String decorator(String content, boolean toLower, DecoratorCallback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Token DecoratorCallback shuold be set.");
        }
        if (content == null || content.length() == 0) {
            return content;
        }

        char orign[] = content.toCharArray();
        if (toLower) {
            content = content.toLowerCase();
        }
        char inputSequence[] = content.toCharArray();
        int pos = 0;
        boolean isHtml = false;
        // 做HTML的简单判定， 过于复杂不利于性能
        if (content.indexOf(DecoratorConstants.RIGHT_TAG) > -1 && content.indexOf(DecoratorConstants.LEFT_TAG) > -1) {
            isHtml = true;
        }

        StringBuilder buffer = new StringBuilder(content.length());
        List<WordTerm> keyList = new ArrayList<WordTerm>(32);
        while (pos < inputSequence.length) {
            WordTerm w = darts.prefixSearchMax(inputSequence, pos, inputSequence.length - pos);
            if (w != null) {
                if (isHtml) {
                    // Html类型的文本,先记录词后面再处理
                    keyList.add(w);
                } else {
                    // if plain text, skip '<', '>' char.
                    if (w.position != rightTagPosition && w.position != leftTagPosition) {
                        buffer.append(callback.decorator(new String(orign, pos, w.length)));
                    } else {
                        buffer.append(orign, pos, w.length);
                    }

                }
                pos += w.length;
            } else {
                if (!isHtml) {
                    buffer.append(orign, pos, 1);
                }
                pos++;
            }
        }
        // 处理Html类型的文本
        if (isHtml) {
            removeTagTerms(orign, keyList);
            // 剔除标签中的词后,若词列表不为空,重新拼装文本: aaXXYYbbZZccXXdd
            if (keyList.size() > 0) {
                int begin = 0;
                for (int i = 0; i < keyList.size(); i++) {
                    WordTerm w = keyList.get(i);
                    buffer.append(orign, begin, w.begin - begin);
                    begin = w.begin;
                    buffer.append(callback.decorator(new String(orign, begin, w.length)));
                    begin = w.begin + w.length;
                }
                buffer.append(orign, begin, orign.length - begin);
            } else {
                buffer.append(orign);
            }
        }
        return buffer.toString();
    }

    /**
     * <pre>
     * 从词列表中剔除在Html标签中的词,包括&quot;&lt;&quot;和&quot;&gt;&quot;
     * 标签开始符：&quot;&lt; X&quot;(X表示非空或标签符)；标签结束符：&quot;&gt;&quot; 判断标准：在&quot;&lt;X&quot;和&quot;&gt;&quot;之间的认为在Html标签内,否则认为是文本或者大小于符号
     * </pre>
     *
     * @param orign 文本的char[]对象
     * @param keyList 找到的词信息列表
     */
    protected void removeTagTerms(char[] orign, List<WordTerm> keyList) {
        int tagPos = -1; // 当前词在词典中的位置
        int offset = -1; // 当前词在文本的偏移量
        int index = -1;
        List<WordTerm> removedList = new LinkedList<WordTerm>();
        for (int i = index + 1; i < keyList.size(); i++) {
            WordTerm term = keyList.get(i);
            tagPos = term.position;
            offset = term.begin;
            if (tagPos == leftTagPosition) {
                if (offset + 1 < orign.length && !isTagOrSpace(orign[offset + 1])) {
                    // 1.1.先找到"<X",后面接着找">",若下一次先找到"<X",则忽略前一次,记录新位置
                    index = i;
                } else if (index > -1) {
                    // 1.2.如果找到过"<X"后,又发现"<",即形如:<xxx<>xx>,应识别为大小于符号,忽略此标签
                    index = -1;
                }
                removedList.add(term);
            } else if (tagPos == rightTagPosition) {
                // 2.1.index <= -1表示是大于符号">", 否则表示前面找到过开始符"<X",这次找到的是结束符">"
                if (index <= -1) {
                    removedList.add(term);
                    continue;
                }
                // 2.2.后面直到找到结束符">"为止.
                for (int j = index; j <= i; j++) {
                    // 3.删除列表中"<X"之后,">"之前(包含本身)的词信息
                    removedList.add(keyList.get(j));
                }
                index = -1;
            }
        }

        keyList.removeAll(removedList);
    }

    /** 是不是空白字符或者左右标签 */
    private boolean isTagOrSpace(char ch) {
        return ch == DecoratorConstants.LEFT_TAG || ch == DecoratorConstants.RIGHT_TAG || ch == ' ';
    }

}