package com.fangcloud.noah.fasttext.fasttext.decorator;

/**
 * 如何修饰一个被查找出来的词汇
 *
 * @author sdh5724 2008-3-28 下午04:16:10
 */
public interface DecoratorCallback {

    public StringBuilder decorator(String src);
}