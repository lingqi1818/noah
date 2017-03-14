package com.fangcloud.noah.fasttext;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import com.fangcloud.noah.fasttext.fasttext.decorator.DecoratorText;
import com.fangcloud.noah.fasttext.fasttext.decorator.TextScanDecorator;
import com.fangcloud.noah.fasttext.fasttext.extract.CJKExtractText;
import com.fangcloud.noah.fasttext.fasttext.extract.ExtractText;
import com.fangcloud.noah.fasttext.fasttext.extract.LowerCaseExtractText;
import com.fangcloud.noah.fasttext.fasttext.psoriasis.CompositeHTMLExtractText;
import com.fangcloud.noah.fasttext.fasttext.psoriasis.MappedDecoratorText;
import com.fangcloud.noah.fasttext.fasttext.psoriasis.SkipTermExtraInfo;
import com.fangcloud.noah.fasttext.fasttext.segment.WordTerm;

/**
 * Created by chenke on 16-9-23.
 */
public class FastTextTest extends TestCase {

    public void testA(){

        String a1 = "天安门";
        String a2 = "习近平";
        String a3 = "法轮功";
        String a4 = "国家";


        List<String> sourceText = new ArrayList<String>();
        sourceText.add(a1);
        sourceText.add(a2);
        sourceText.add(a3);
        sourceText.add(a4);
        DecoratorText decoratorText = new TextScanDecorator(sourceText,true);

        boolean flag = decoratorText.containTerm("天安门事件发生",true);

        System.out.println(flag);

        String content = "法轮功来国家自天安门台湾有40年习近平历史的知名品牌艾玛压力衣，有雄厚的医学背景是达到医疗等天安门级的专业产品，拥有多习近平款专利设计。采用国际顶级进口医疗国家等级面料，所有面料都是经过OEKO-TEX国际环保纺法轮功织认证，不含甲醛、不过敏、安全。根据每个人体型特点测量身体68项数据，由人体工程学、健康医学、压力医学三大领域专家联袂打造，个人专属，全球独一无二。无任何硬性支持物设计，根据人体经络运行安放磁石，改善身体亚健康情况。运用医学压力原理设计，贴身不紧身，长期穿着真正达到收紧、消耗脂肪，改善体型的效果。一般三到六个月会有明显效果，艾玛贴心提供一年内免费减寸四次，真正的省钱、高性价比！由于本产品是为个人量身定制，定制产品不退不换，售后保障在一年内免费4次减寸。立即购买得总价值20000套餐，包含体雕一件➕胸部疏通塑型五次➕腰腹减脂塑型按摩五次！仅限五个名额";

        List<String> resultList = decoratorText.parseTerms(content,true,true);

        System.out.println(resultList);

    }

    public static void testB(){

        List<SkipTermExtraInfo> skipList = new ArrayList<SkipTermExtraInfo>();

        SkipTermExtraInfo s1 = new SkipTermExtraInfo("法轮功", true, true, 0.5f);
        SkipTermExtraInfo s2 = new SkipTermExtraInfo("习近平", true, true, 0.5f);
        SkipTermExtraInfo s3 = new SkipTermExtraInfo("胡锦涛", true, false, 0.5f);
        SkipTermExtraInfo s4 = new SkipTermExtraInfo("ab天安门", true, true, 0.5f);
        SkipTermExtraInfo s5 = new SkipTermExtraInfo("gcd", true, false, 0.5f);
        SkipTermExtraInfo s6 = new SkipTermExtraInfo("那你", true, false, 0.5f);
        SkipTermExtraInfo s7 = new SkipTermExtraInfo("福气", true, true, 0.5f);

//        skipList.add(s1);
//        skipList.add(s2);
//        skipList.add(s3);
//        skipList.add(s4);
//        skipList.add(s5);
//        skipList.add(s6);
        skipList.add(s7);

        DecoratorText decoratorText = new MappedDecoratorText(skipList,1);



        String content = "黑社会好福气纠结 hh老司机哈哈哈那你就还想继续军训基地那些年的你打那那那cm可以d很好很好ud你想你想你想你没hjhcmd哈哈哈d那些那你想cm可以d，jhhh你想你想你jjj，cm可以d，hu谢娜到哈哈哈jjcmdhhh小宝 cjjjmhhd dge那些年想你想你想你才CM可以D能从哪从哪从哪从黑社会好纠结 hh老司机哈哈哈那你就还想继续军训基地那些年的你打那那那nbja很好很好ud你想你想你想你没hjhnba哈哈哈d那些那你想nb呵a，jhhh你想你想你jjj，nba，hu谢娜到哈哈哈jjnbahhh小宝      nba dge那些年想你想你想你才NB家A能从哪从哪从哪从            福气hhh，nba/hjj 哈哈哈a危险呵呵呵呵a危险b红红火火恍恍a 危险比你a 危险 b红红火火恍a 危险b不不不a危险 b好吧宝贝表福气";

//        String content = "ab";

        ExtractText extractText = new CJKExtractText();

        String newContent = extractText.getText(content);
        System.out.println(newContent);

        List<String> resultList = decoratorText.parseTerms(content,true,false);
        System.out.println(resultList);
    }



}

