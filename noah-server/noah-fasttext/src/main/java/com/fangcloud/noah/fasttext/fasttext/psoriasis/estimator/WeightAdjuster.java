package com.fangcloud.noah.fasttext.fasttext.psoriasis.estimator;

import java.util.Set;

import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

/**
 * 允许外部根据业务规则调整算法评估结果，例如“法-轮-功”这个词可以增加权重。 另外也可以对整个文档调整权重，例如来自某个用户的文档权重可以增加和减少。 具体规则有使用者根据业务需求自主确定。
 *
 * @author guolin.zhuanggl 2008-7-28 下午04:45:57
 */
public interface WeightAdjuster {

    /**
     * Estimator开始文档评估是调用，用于一些初始化工作。
     */
    public void beginAdjust(Set<String> wordList);

    /**
     * Estimator评估结束后调用，用于资源释放等工作。
     */
    public void endAdjust(Set<String> wordList);

    /**
     * 调整此文档中词的权重。
     *
     * @param word 分词结果
     * @param weight 原始weight值
     * @return 调整后的weight值
     */
    double adjustWord(WordTerm word, double weight);

    /**
     * 调整文档权重。
     *
     * @param wordList　分词列表
     * @param weight　原始weight值
     * @return 调整后的weight值
     */
    double adjustDocument(Set<String> wordList, double weight);
}
