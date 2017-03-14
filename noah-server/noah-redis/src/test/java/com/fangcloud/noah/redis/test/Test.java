package com.fangcloud.noah.redis.test;

import java.util.List;

import redis.clients.jedis.Jedis;

public class Test {

    public static void main(String[] args) {
        final Jedis j1 = new Jedis("127.0.0.1", 7000);
        //System.out.println(j1.get("1"));
        Jedis j2 = new Jedis("127.0.0.1", 7001);
        //System.out.println(j2.get("1"));
        Jedis j3 = new Jedis("127.0.0.1", 7002);

        Jedis j4 = new Jedis("127.0.0.1", 7003);
        
        Jedis j5 = new Jedis("127.0.0.1", 7004);

        j5.clusterSetSlotImporting(9842, "9c09eed85f45b371be80c4eb24e184000d6eea4f");
        //        //
        j4.clusterSetSlotMigrating(9842, "2aef0e54237d693433d38099e04f551efef06fc4");

        //j4.clusterSetSlotNode(9842, "9c09eed85f45b371be80c4eb24e184000d6eea4f");

        List<String> keys = j4.clusterGetKeysInSlot(9842,
                j4.clusterCountKeysInSlot(9842).intValue());

        for (String key : keys) {
            j4.migrate("127.0.0.1", 7004, key, 0, 5000);
        }
        j4.clusterSetSlotStable(9842);
        j5.clusterSetSlotNode(9842, "2aef0e54237d693433d38099e04f551efef06fc4");
        
        System.out.println(j1.clusterInfo());
        System.out.println(j1.clusterNodes());

    }

}
