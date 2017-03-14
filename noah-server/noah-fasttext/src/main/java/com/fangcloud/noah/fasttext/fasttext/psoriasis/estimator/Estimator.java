package com.fangcloud.noah.fasttext.fasttext.psoriasis.estimator;


import java.util.List;

import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

/**
 * 类Estimator.java的实现描述：TODO 类实现描述
 *
 * @author guolin.zhuanggl 2008-7-28 下午04:48:48
 */

public interface Estimator {

    double estimateValue(List<WordTerm> wordTermList);

    /**
     * 增加一个权重调整器，用户通过调整器对算法进行干预，允许多个调整器的复合效应。
     *
     * @see WeightAdjuster
     * @param adjuster
     */
    void addWeightAdjuster(WeightAdjuster adjuster);

    /**
     * 移出一个权重调整器。
     *
     * @see WeightAdjuster
     * @param adjuster
     */
    void removeWeightAdjuster(WeightAdjuster adjuster);

    /**
     * 取得当前Adjuster的个数。
     *
     * @return 个数
     */
    public int getAdjusterCount();

    /**
     * 取指定位置的Adjuster。
     *
     * @param index 索引
     * @return 指定位置的Adjuster，非空
     */
    public WeightAdjuster getAdjusterAt(int index);

    /**
     * 根据业务给定的词语权重对文档进行评分，目前实现是Bayes算法。<br>
     *
     * @param wordTermList
     * @return
     */
}
