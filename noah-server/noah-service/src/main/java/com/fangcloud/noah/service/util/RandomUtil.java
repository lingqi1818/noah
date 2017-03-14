package com.fangcloud.noah.service.util;

import java.util.Random;

/**
 * Created by chenke on 16-10-9.
 */
public class RandomUtil {


    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static void main(String[] args) {
        for(int i=0;i<1000;i++)
        System.out.println(random(0,1000));
    }
}
