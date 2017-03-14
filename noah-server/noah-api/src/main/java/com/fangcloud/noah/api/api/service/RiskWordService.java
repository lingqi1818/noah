package com.fangcloud.noah.api.api.service;

import java.util.List;

/**
 * 高危词汇判断服务
 * Created by chenke on 16-9-23.
 */
public interface RiskWordService {

    /**
     * 高危词判断
     * @param content  输入内容
     * @param wordType  词库类型
     * @return 命中的高危词list
     */
    List<String> containTerm(String content,String wordType);


    /**
     *
     * @param contentList  多个输入内容
     * @param wordType 词库类型
     * @return 每个内容命中的高危词 按list的索引顺序和输入内容对应
     */
    List<List<String>> containTerm(List<String> contentList,String wordType);
}

