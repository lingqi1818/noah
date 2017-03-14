package com.fangcloud.noah.fasttext.fasttext.psoriasis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangcloud.noah.fasttext.fasttext.extract.CharNormalization;
import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * 在SkipDarts基础上增加部首匹配处理，如”氵去“对应”法“，”氵去轮功“对应”法轮功等，同样支持编辑距离概念，<br>
 * 如“氵去aa轮功”也可以匹配"法轮功"，但是“氵aa去”不能匹配“法”因为字符拆分后在插入其它字符没有意义。
 *
 * @author guolin.zhuanggl 2008-8-11 上午11:48:03
 */
public class RadicalSkipDarts extends SkipDarts {

    private final static Logger logger         = LoggerFactory
            .getLogger(RadicalSkipDarts.class);

    public static final String      DEFAULT_RADICA_DIC_NAME = "DefaultRadicalDic_u8.txt";

    private List<SkipTermExtraInfo> radicalDic;

    /**
     * 使用关键词词典和缺省部首词典构建字典
     *
     * @param dic 关键词词典列表，包含相关消息，字典不可为空，空则抛出IllegalArgumentException
     * @param skip 编辑距离，不可小于0，建议小于3，太大影响性能而且没有必要
     */
    public RadicalSkipDarts(List<SkipTermExtraInfo> dic, int skip){
        this(dic, null, skip);
    }

    /**
     * 使用关键词词典和部首词典构建字典
     *
     * @param dic 关键词词典列表，包含相关消息，字典不可为空，空则抛出IllegalArgumentException
     * @param radicalDic 部首词词典，如“法”可以分为“氵去”，在部首字典中作为一字典项
     * @param skip 编辑距离，不可小于0，建议小于3，太大影响性能而且没有必要
     */
    public RadicalSkipDarts(List<SkipTermExtraInfo> dic, List<SkipTermExtraInfo> radicalDic, int skip){
        if (dic == null && skip < 0) {
            logger.error("Dictinary can not be null or skip must be greater than 0.");
            throw new IllegalArgumentException("Dictinary can not be null or skip must be greater than 0.");
        }
        if (radicalDic == null) {
            List<String> radicalList = null;
            try {
                radicalList = PsoriasisUtil.readList(DEFAULT_RADICA_DIC_NAME, "UTF8", getClass());
            } catch (Exception e) {
                logger.error("Radical dictionary initialization failed." + e.getMessage());
                throw new RuntimeException("Radical dictionary initialization failed." + e.getMessage());
            }
            radicalDic = PsoriasisUtil.loadRadicalDic(radicalList);
        }
        this.radicalDic = radicalDic;
        dic.addAll(radicalDic);
        init(dic, skip);
        if (logger.isDebugEnabled()) {
            logger.debug("Initialize Darts from dic: " + dic + "Radicals Dic: " + radicalDic + " skip: " + skip);
        }
    }

    /**
     * 重载方法增加对词表中包含部首的情况进行处理，例如词典中如果包含”氵去车仑工力“词条，部首词典存在”氵去“”车仑“和”工力“词条，<br>
     * 词典中的词条需要处理成”法轮功“，否则会导致状态机状态错误,这里进行词条normalization处理。如果部首词典不存在或者为空，不进行任何处理，直接返回。
     */
    @Override
    protected void normalizeWord(SkipTermExtraInfo skip) {
        super.normalizeWord(skip);
        // 如果部首字典不存在或者本词条是部首词典中的词条，直接返回
        if (radicalDic == null || radicalDic.isEmpty()
                || skip.getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
            return;
        }
        String word = skip.word;
        for (SkipTermExtraInfo info : radicalDic) {
            if (info == null) {
                continue;
            }
            //如果普通词词典中存在“氵去”类似的词条，这里不做处理，后续在去重时直接去掉
            if(info.getWord().equals(skip.getWord())){
                return;
            }
            int index = word.indexOf(info.getWord());
            if (index != -1) {
                word = word.replaceAll(info.getWord(), String.valueOf(info.getCompositeChar()));
                skip.setWord(word);
            }
        }
    }

    /**
     * 在darts和skip darts基础上增加radicals匹配功能，例如：“法轮功”可以同时匹配“氵去轮功”“法车仑功”和“氵去车仑工力”等等组合情况。<br>
     * 算法如下：<br>
     * 1、当分隔字符在词首时，如”氵去轮功“，根据词典darts会直接匹配”氵去“，从这个词的TermExtraInfo中可以取得其组合字为”法“，<br>
     * 调整DoubleArray状态到”法“首状态，并继续匹配。<br>
     * 2、当分隔字符不在词首时，如”法车仑工力“等，此时状态机转换到中间状态并且不能继续匹配，此时需要在处理不能匹配代码处增加子匹配代码，<br>
     * 搜索出”车仑“对应的”轮“字，并切换状态到”法轮“状态，并继续匹配。<br>
     * 3、异常情况：当进行了部首匹配但是没有匹配到词时，例如仅仅匹配”氵去“没有意义，结果应丢弃。另外，如果实际使用的字典中包含”法车仑工力“类似的词语，<br>
     * 会导致状态机状态错误，所以词典需要进行normalization处理，把部首合并。<br>
     */
    @Override
    public List<WordTerm> prefixSearch(char[] key, int pos, int len, int skip) {
        List<WordTerm> result = new ArrayList<WordTerm>();
        if (skip < 0) {
            logger.error("Skip can not less than 0!");
            skip = this.skip;
        }
        boolean needSkip = (skip != 0);
        int p, n, i, b = baseArray[0];
        int remain = skip;

        char folkChar = CharNormalization.DEFAULT_BLANK_CHAR;

        for (i = pos; i < pos + len; ++i) {
            p = b; // + 0;
            n = baseArray[p];
            if (b == checkArray[p] && n < 0) {// 找到一个词
                WordTerm w = new WordTerm();
                w.position = -n - 1;
                w.begin = pos;
                w.length = i - pos;
                w.termExtraInfo = extraNodeArray[p];
                // 处理词语拆分情况，如“氵去”匹配“法”，因为是首字匹配，需要将状态机状态转移到起始状态。
                if (w.termExtraInfo instanceof SkipTermExtraInfo
                        && ((SkipTermExtraInfo) w.termExtraInfo).getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                    folkChar = ((SkipTermExtraInfo) w.termExtraInfo).getCompositeChar();
                    b = baseArray[0];
                    p = b;
                    n = baseArray[p];
                    i--;
                    if(((SkipTermExtraInfo) w.termExtraInfo).getWeight() != SkipTermExtraInfo.DEFAULT_RADICAL_WORD_WEIGHT){//用户词典中包含部首词条，匹配之
                        result.add(w);
                    }
                } else {
                    if (result.size() > 0) {// 如果已经跳字匹配但是没有成功，此时会增加一个重复错误匹配进去，这里处理
                        WordTerm w1 = result.get(result.size() - 1);// 取得上一个匹配结果
                        if (w1.position != w.position) {// 不是同一个词重复匹配
                            result.add(w);
                        }
                    } else {
                        result.add(w);
                    }
                }
            }
            folkChar = folkChar == CharNormalization.DEFAULT_BLANK_CHAR ? key[i] : folkChar;
            p = b + (folkChar) + 1;
            folkChar = CharNormalization.DEFAULT_BLANK_CHAR;
            if (b == checkArray[p]) {// 还能往下接
                b = baseArray[p];
                remain = skip;// 还原还能跳过的字符数
            } else {
                // 先处理词语拆分情况，如“氵去”匹配“法”
                if (b != baseArray[0]) {
                    List<WordTerm> sub = super.prefixSearch(key, i, len - (i - pos), 0);
                    if (sub.size() == 1) {
                        WordTerm wt = sub.get(0);
                        if (wt != null
                                && wt.termExtraInfo instanceof SkipTermExtraInfo
                                && ((SkipTermExtraInfo) wt.termExtraInfo).getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                            folkChar = ((SkipTermExtraInfo) wt.termExtraInfo).getCompositeChar();
                            i += wt.length - 2;
                            continue;
                        }
                    }
                }
                if (needSkip) {
                    if (remain == 0 || b == baseArray[0]) {// 不能再跳过字符了或者一开始就没有匹配到
                        return result;
                    } else {
                        remain--;
                    }
                } else {
                    return result;
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
            // 处理词语拆分情况，如“氵去”匹配“法”
            if (w.termExtraInfo instanceof SkipTermExtraInfo
                    && ((SkipTermExtraInfo) w.termExtraInfo).getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                if(((SkipTermExtraInfo) w.termExtraInfo).getWeight() != SkipTermExtraInfo.DEFAULT_RADICAL_WORD_WEIGHT){//用户词典中包含部首词条，匹配之
                    result.add(w);
                }
                return result;
            }
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
     * 在darts和skip darts基础上增加radicals匹配功能，例如：“法轮功”可以同时匹配“氵去轮功”“法车仑功”和“氵去车仑工力”等等组合情况。<br>
     * 算法如下：<br>
     * 1、当分隔字符在词首时，如”氵去轮功“，根据词典darts会直接匹配”氵去“，从这个词的TermExtraInfo中可以取得其组合字为”法“，<br>
     * 调整DoubleArray状态到”法“首状态，并继续匹配。<br>
     * 2、当分隔字符不在词首时，如”法车仑工力“等，此时状态机转换到中间状态并且不能继续匹配，此时需要在处理不能匹配代码处增加子匹配代码，<br>
     * 搜索出”车仑“对应的”轮“字，并切换状态到”法轮“状态，并继续匹配。<br>
     * 3、异常情况：当进行了部首匹配但是没有匹配到词时，例如仅仅匹配”氵去“没有意义，结果应丢弃。另外，如果实际使用的字典中包含”法车仑工力“类似的词语，<br>
     * 会导致状态机状态错误，所以词典需要进行normalization处理，把部首合并。<br>
     * 4、如果词表中存在“工力”类似的词条，此时会需要特殊处理，具体见代码。
     */
    @Override
    public WordTerm prefixSearchMax(char[] key, int pos, int len, int skip) {
        if (skip < 0) {
            logger.error("Skip can not less than 0!");
            skip = this.skip;
        }
        boolean needSkip = (skip != 0);
        int p, n, i, b = baseArray[0];
        WordTerm w = null;
        int remain = skip;
        boolean isSkipMatched = false;
        char folkChar = CharNormalization.DEFAULT_BLANK_CHAR;
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
                    // 处理词语拆分情况，如“氵去”匹配“法”，因为是首字匹配，需要将状态机状态转移到起始状态。
                    if (w.termExtraInfo instanceof SkipTermExtraInfo
                            && ((SkipTermExtraInfo) w.termExtraInfo).getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                        folkChar = ((SkipTermExtraInfo) w.termExtraInfo).getCompositeChar();
                        b = baseArray[0];
                        p = b;
                        n = baseArray[p];
                        i--;
                        if(((SkipTermExtraInfo) w.termExtraInfo).getWeight() == SkipTermExtraInfo.DEFAULT_RADICAL_WORD_WEIGHT){//用户词典中包含部首词条，匹配之
                            w = null;
                        }
                    }
                }
            }
            folkChar = folkChar == CharNormalization.DEFAULT_BLANK_CHAR ? key[i] : folkChar;
            p = b + (folkChar) + 1;
            folkChar = CharNormalization.DEFAULT_BLANK_CHAR;
            if (b == checkArray[p]) {// 还能往下接
                b = baseArray[p];
                remain = skip;// 还原还能跳过的字符数
            } else {
                if (b != baseArray[0]) {
                    // 先处理词语拆分情况，如“氵去”匹配“法”
                    WordTerm wt = super.prefixSearchMax(key, i, len - (i - pos), 0);
                    if (wt != null) {
                        if (wt != null
                                && wt.termExtraInfo instanceof SkipTermExtraInfo
                                && ((SkipTermExtraInfo) wt.termExtraInfo).getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                            folkChar = ((SkipTermExtraInfo) wt.termExtraInfo).getCompositeChar();
                            i += wt.length - 2;
                            continue;
                        }
                    }
                }
                if (needSkip) {
                    if (remain == 0 || b == baseArray[0]) {// 不能再跳过字符了或者一开始就没有匹配到
                        return w;
                    } else {
                        isSkipMatched = true;// 开始跳字匹配
                        remain--;
                    }
                } else {
                    return w;
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
                // 处理词语拆分情况，如“氵去”匹配“法”
                if (w.termExtraInfo instanceof SkipTermExtraInfo
                        && ((SkipTermExtraInfo) w.termExtraInfo).getCompositeChar() != CharNormalization.DEFAULT_BLANK_CHAR) {
                    if(((SkipTermExtraInfo) w.termExtraInfo).getWeight() != SkipTermExtraInfo.DEFAULT_RADICAL_WORD_WEIGHT){//用户词典中包含部首词条，匹配之
                        return w;
                    }
                    return null;
                }
            }
        }
        return w;
    }
}