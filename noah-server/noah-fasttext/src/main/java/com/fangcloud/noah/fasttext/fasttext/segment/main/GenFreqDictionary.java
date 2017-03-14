package com.fangcloud.noah.fasttext.fasttext.segment.main;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fangcloud.noah.fasttext.fasttext.segment.Darts;
import com.fangcloud.noah.fasttext.fasttext.segment.TermFrequencyCallback;

public class GenFreqDictionary {

    public static void main(String[] args) throws IOException {
        // "/home/leon/work/headquarters/working/commons/fasttext/data/dic/professional.txt"
        // /home/leon/work/headquarters/working/commons/fasttext/data/bin/professional.dic.bin
        String fileIn1 = "/home/leon/work/headquarters/working/commons/fasttext/src/conf.test/dic/professional.txt";
        String fileIn2 = "/home/leon/work/headquarters/working/commons/fasttext/src/conf.test/dic/sogo.txt";
        String fileOut1 = "/home/leon/work/headquarters/working/commons/fasttext/src/conf.test/bin/professional.dic.bin";
        String fileOut2 = "/home/leon/work/headquarters/working/commons/fasttext/src/conf.test/bin/sogo.dic.bin";
        genOne(fileIn1, fileOut1);
        genOne(fileIn2, fileOut2);
    }

    public static void genOne(String dic, String binDic) throws IOException {
        Darts dat = new Darts();
        InputStream worddata = new FileInputStream(dic);
        BufferedReader in = new BufferedReader(new InputStreamReader(worddata, "UTF8"));

        String newword;
        List<char[]> wordList = new ArrayList<char[]>(800000);
        while ((newword = in.readLine()) != null) {
            if (newword.length() > 1) {
                wordList.add(newword.toCharArray());
            }

        }
        in.close();
        dat.build(wordList, new TermFrequencyCallback());

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(binDic));
        oos.writeObject(dat);
        oos.close();
    }
}
