package com.fangcloud.noah.fasttext.fasttext.segment;


import java.util.Comparator;

public class CharArrayComparator<T> implements Comparator<T> {

    public int compare(T o1, T o2) {
        char[] a = ((InternalElement) o1).sequence;
        char[] b = ((InternalElement) o2).sequence;
        int loop = a.length > b.length ? b.length : a.length;
        for (int i = 0; i < loop; i++) {
            int c = a[i] - b[i];
            if (c != 0) {
                return c;
            }
        }
        return a.length - b.length;
    }
}
