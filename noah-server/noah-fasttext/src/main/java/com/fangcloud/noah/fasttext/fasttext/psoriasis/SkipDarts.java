package com.fangcloud.noah.fasttext.fasttext.psoriasis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.fasttext.fasttext.decorator.TextScanDecorator;
import com.fangcloud.noah.fasttext.fasttext.extract.CharNormalization;
import com.fangcloud.noah.fasttext.fasttext.segment.Darts;
import com.fangcloud.noah.fasttext.fasttext.segment.InternalElement;
import com.fangcloud.noah.fasttext.fasttext.segment.VocabularyProcess;
import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类SkipDarts.java的实现描述：<br>
 * 在darts基础上增加跳字匹配功能，例如“法-轮-功”匹配“法轮功”等，跳字次数可以设定，缺省为0，表示不跳字。
 * 算法对于状态机在不匹配状态进行了后续处理，继续按照可以跳字次数匹配后续字符，直到发现一个匹配或者跳字结束或者文本扫描完毕。 跳字次数不可小于0，建议在1~3之间，大于3没有意义，而且对性能影响较大。
 * note:内部对词表词条进行了规范化和去重处理，包括：<br>
 * 1、繁体转简体<br>
 * 2、全角转半角<br>
 * 3、大写转小写<br>
 * 4、过滤空格<br>
 * 5、去重<br>
 *
 * @author guolin.zhuanggl 2008-8-18 上午11:19:59
 */
public class SkipDarts extends Darts {

    private final static Logger logger         = LoggerFactory
            .getLogger(SkipDarts.class);
    protected int            skip   = 0;

    /**
     * 缺省构建器，方便子类进行继承扩展，注意扩展类构建器必须调用init()。
     */
    protected SkipDarts(){
    }

    /**
     * 使用词语列表构建darts，内部对两个词表项进行规范化处理和去重处理。<br>
     *
     * @param dic 词语列表，包含权重，是否可以跳字匹配，是否需要全字匹配等信息
     * @param skip 跳字匹配，如果为0表示不跳字，如果小于0抛出IAE
     */

    public SkipDarts(List<SkipTermExtraInfo> dic, int skip){
        if (dic == null || skip < 0) {
            logger.error("Dic can not be null or skip must be greater than 0.");
            throw new IllegalArgumentException("Dic can not be null or skip must be greater than 0.");
        }
        init(dic, skip);
    }

    /**
     * 使用使用词语列表构建darts，内部对词表项进行规范化处理和去重处理。<br>
     *
     * @param dic 关键词词典，不可为空，为空抛出IAE异常<br>
     * @param skip 跳字匹配，如果为0表示不跳字，如果小于0抛出IAE异常<br>
     */
    protected void init(List<SkipTermExtraInfo> dic, int skip) {
        this.skip = skip;
        List<char[]> tokens = new ArrayList<char[]>(dic.size());
        Map<String, SkipTermExtraInfo> wordMap = new HashMap<String, SkipTermExtraInfo>();
        for (SkipTermExtraInfo word : dic) {
            normalizeWord(word);
            // 利用map去重,优先保留部首词典中的词
            if (word != null && word.getWord() != null && word.getWord().length() != 0) {
                if (wordMap.containsKey(word.getWord())) {
                    // 如果已经包含，替换weight，包括部首词典的weight
                    SkipTermExtraInfo tmp = wordMap.get(word.getWord());
                    if (tmp.getWeight() == SkipTermExtraInfo.DEFAULT_RADICAL_WORD_WEIGHT) {
                        tmp.setWeight(word.getWeight());
                    }
                    // 如果是部首词典中的词，需要保留
                    if (word.getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                        tmp.setCompositeChar(word.getCompositeChar());
                    }
                } else {
                    tokens.add(word.getWord().toCharArray());
                    wordMap.put(word.getWord(), word);
                }
            }
        }
        build(tokens, new SkipWordProcess(wordMap));
    }

    /**
     * 对字典进行规范化处理，主要做以下处理：<br>
     * 1、繁体转简体。<br>
     * 2、全角转半角。<br>
     * 3、全部转成小写。<br>
     * 4、去除词语中空格等分隔符<br>
     * 5、利用trim功能去除首尾空格（包括全角空格）<br>
     */
    protected void normalizeWord(SkipTermExtraInfo skip) {
//        if (skip != null) {
//            String word = skip.getWord();
//            if (word != null && word.length() != 0) {
//                word = CharNormalization.compositeTextConvert(word.trim(), true, true, true, false, true, false).trim();
//                skip.setWord(word);
//            }
//        }
    }

    /**
     * 扩展Darts，添加允许指定数量跳过字符的匹配方法</br> <br>
     * 扩展的前向匹配查找， 要以在匹配时跳过指定个数的字符。</br> <br>
     * 如指定跳过一个字符，则'a b c', 'abdc', 'a我bc'都可以匹配'abc'</br> 使用缺省设置skip值。
     *
     * @param key 需要被匹配的字串
     * @param pos 从字串中哪个位置开始匹配
     * @param len 匹配多少个字符
     * @return 一次匹配的结果(可能匹配到多个相互包含的词，如'abcd'和'abcde')
     */

    public List<WordTerm> prefixSearch(char[] key, int pos, int len) {
        return prefixSearch(key, pos, len, this.skip);
    }

    /**
     * 扩展Darts，添加允许指定数量跳过字符的匹配方法</br> <br>
     * 扩展的前向匹配查找， 要以在匹配时跳过指定个数的字符。</br> <br>
     * 如指定跳过一个字符，则'a b c', 'abdc', 'a我bc'都可以匹配'abc'</br> 使用缺省设置skip值。
     *
     * @param key 需要被匹配的字串
     * @param pos 从字串中哪个位置开始匹配
     * @param len 匹配多少个字符
     * @return 一次匹配的结果(可能匹配到多个相互包含的词，如'abcd'和'abcde')
     */
    public WordTerm prefixSearchMax(char[] key, int pos, int len) {
        return prefixSearchMax(key, pos, len, this.skip);
    }

    /**
     * 扩展Darts，添加允许指定数量跳过字符的匹配方法</br> <br>
     * 扩展的前向匹配查找， 要以在匹配时跳过指定个数的字符。</br> <br>
     * 如指定跳过一个字符，则'a b c', 'abdc', 'a我bc'都可以匹配'abc'</br>
     *
     * @param key 需要被匹配的字串
     * @param pos 从字串中哪个位置开始匹配
     * @param len 匹配多少个字符
     * @param skip 允许跳过的字符数(每两个字符间)
     * @return 一次匹配的结果(可能匹配到多个相互包含的词，如'abcd'和'abcde')
     */

    public List<WordTerm> prefixSearch(char[] key, int pos, int len, int skip) {
        if (skip < 0) {
            throw new IllegalArgumentException("Skip can not less than 0!");
        }
        if (skip == 0) {
            return super.prefixSearch(key, pos, len);
        }
        int p, n, i, b = baseArray[0];
        List<WordTerm> result = new ArrayList<WordTerm>();
        int remain = skip;
        for (i = pos; i < pos + len; ++i) {
            p = b; // + 0;
            n = baseArray[p];
            if (b == checkArray[p] && n < 0) {// 找到一个词
                WordTerm w = new WordTerm();
                w.position = -n - 1;
                w.begin = pos;
                w.length = i - pos;
                w.termExtraInfo = extraNodeArray[p];
                if (result.size() > 0) {// 如果已经跳字匹配但是没有成功，此时会增加一个重复错误匹配进去，这里处理
                    WordTerm w1 = result.get(result.size() - 1);// 取得上一个匹配结果
                    if (w1.position != w.position) {// 不是同一个词重复匹配
                        result.add(w);
                    }
                } else {
                    result.add(w);
                }
            }
            p = b + (key[i]) + 1;
            if (b == checkArray[p]) {// 还能往下接
                b = baseArray[p];
                remain = skip;// 还原还能跳过的字符数
            } else {
                if (remain == 0 || b == baseArray[0]) {// 不能再跳过字符了或者一开始就没有匹配到
                    return result;
                } else {
                    remain--;
                }
            }
        }
        p = b;
        n = baseArray[p];
        if (b == checkArray[p] && n < 0) {
            WordTerm w = new WordTerm();
            w.position = -n - 1;
            w.begin = pos;
            w.length = i - pos;
            w.termExtraInfo = extraNodeArray[p];
            if (result.size() > 0) {// 如果已经跳字匹配但是没有成功，此时会增加一个重复错误匹配进去，这里处理
                WordTerm w1 = result.get(result.size() - 1);// 取得上一个匹配结果
                if (w1.position != w.position) {// 不是同一个词重复匹配
                    result.add(w);
                }
            } else {
                result.add(w);
            }
        }
        return result;
    }

    /**
     * 扩展Darts，添加允许指定数量跳过字符的匹配方法</br> <br>
     * 扩展的前向匹配查找， 要以在匹配时跳过指定个数的字符。</br> <br>
     * 如指定跳过一个字符，则'a b c', 'abdc', 'a我bc'都可以匹配'abc'</br>
     *
     * @param key 需要被匹配的字串
     * @param pos 从字串中哪个位置开始匹配
     * @param len 匹配多少个字符
     * @param skip 允许跳过的字符数(每两个字符间)
     * @return 一次匹配的结果(可能匹配到多个相互包含的词，如'abcd'和'abcde')
     */
    public WordTerm prefixSearchMax(char[] key, int pos, int len, int skip) {
        if (skip < 0) {
            throw new IllegalArgumentException("Skip can not less than 0!");
        }
        if (skip == 0) {
            return super.prefixSearchMax(key, pos, len);
        }
        int p, n, i, b = baseArray[0];
        WordTerm w = null;
        int remain = skip;
        boolean isSkipMatched = false;
        for (i = pos; i < pos + len; ++i) {
            p = b; // + 0;
            n = baseArray[p];
            if (b == checkArray[p] && n < 0) {
                if (w == null || !isSkipMatched || w.position != -n - 1) {// 如果是跳字匹配且没有发现新词，保留原词
                    if (w == null) {
                        w = new WordTerm();
                    }
                    w.position = -n - 1;
                    w.begin = pos;
                    w.length = i - pos;
                    if (hasExtraData) {
                        w.termExtraInfo = extraNodeArray[p];
                    }
                }
            }
            p = b + (key[i]) + 1;
            if (b == checkArray[p]) {// 还能往下接
                b = baseArray[p];
                remain = skip;// 还原还能跳过的字符数
            } else {
                if (remain == 0 || b == baseArray[0]) {// 不能再跳过字符了或者一开始就没有匹配到
                    return w;
                } else {
                    isSkipMatched = true;// 开始跳字匹配
                    remain--;
                }
            }
        }
        p = b;
        n = baseArray[p];
        if (b == checkArray[p] && n < 0) {
            if (w == null || !isSkipMatched || w.position != -n - 1) {// 如果是跳字匹配且没有发现新词，保留原词
                if (w == null) {
                    w = new WordTerm();
                }
                w.position = -n - 1;
                w.begin = pos;
                w.length = i - pos;
                if (hasExtraData) {
                    w.termExtraInfo = extraNodeArray[p];
                }
            }
        }
        return w;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        if (skip < 0) {
            logger.error("skip must be greater than 0.");
            throw new IllegalArgumentException("Skip must be greater than 0.");
        }
        this.skip = skip;
    }

    static class SkipWordProcess implements VocabularyProcess {

        private Map<String, SkipTermExtraInfo> wordMap;

        public SkipWordProcess(Map<String, SkipTermExtraInfo> wordMap){
            this.wordMap = wordMap;
        }

        public List<InternalElement> postProcess(List<char[]> lines) {
            List<InternalElement> elements = new ArrayList<InternalElement>(lines.size());
            for (char[] cs : lines) {
                SkipTermExtraInfo word = wordMap.get(new String(cs));
                if (word != null) {
                    InternalElement element = new InternalElement(cs, word);
                    elements.add(element);
                }
            }
            return elements;
        }
    }
}