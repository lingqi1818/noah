package com.fangcloud.noah.fasttext.fasttext.psoriasis;

import java.util.ArrayList;
import java.util.List;

import com.fangcloud.noah.fasttext.fasttext.decorator.DecoratorText;
import com.fangcloud.noah.fasttext.fasttext.extract.CompositeExtractText;
import com.fangcloud.noah.fasttext.fasttext.extract.ExtractText;

/**
 * Created by chenke on 16-9-23.
 */
public class TestB {

    public static void main(String[] args) {
        testB();
    }

    public static void testB(){

        List<SkipTermExtraInfo> skipList = new ArrayList<SkipTermExtraInfo>();

        SkipTermExtraInfo s1 = new SkipTermExtraInfo("法轮功", true, true, 0.5f);
        SkipTermExtraInfo s2 = new SkipTermExtraInfo("习近平", true, true, 0.5f);
        SkipTermExtraInfo s3 = new SkipTermExtraInfo("胡锦涛", true, true, 0.5f);
        SkipTermExtraInfo s4 = new SkipTermExtraInfo("河李家", true, true, 0.5f);
        SkipTermExtraInfo s5 = new SkipTermExtraInfo("天安门", true, true, 0.5f);

        skipList.add(s1);
        skipList.add(s2);
        skipList.add(s3);
        skipList.add(s4);
        skipList.add(s5);

        DecoratorText decoratorText = new MappedDecoratorText(skipList,2);

        String content = "我aaa胡锦涛自天-安-门台湾氵去车仑工力有40年我习a近a平历史的知名品牌艾法-轮-功玛压力衣，有雄厚的医学背景是达到医疗等天安bb门";

        ExtractText extractText = new CompositeExtractText();

        String newContent = extractText.getText(content);
        System.out.println(newContent);

        List<String> resultList = decoratorText.parseTerms(newContent,true,false);

        System.out.println(resultList);
    }
}
