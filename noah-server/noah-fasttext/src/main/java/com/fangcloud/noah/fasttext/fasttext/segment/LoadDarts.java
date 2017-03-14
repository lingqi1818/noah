package com.fangcloud.noah.fasttext.fasttext.segment;

import java.io.*;

public class LoadDarts {

    public static Darts load(String dicFile) throws IOException, ClassNotFoundException {
        InputStream fis = new FileInputStream(dicFile);
        return load(fis);

    }

    public static Darts load(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        Darts dat = (Darts) ois.readObject();
        ois.close();
        return dat;
    }

    public static Darts load(File resourceAsFile) throws IOException, ClassNotFoundException {
        InputStream fis = new FileInputStream(resourceAsFile);
        return load(fis);
    }
}
