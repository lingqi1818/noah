package com.fangcloud.noah.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;

public class MiscUtils {

    public static Set<HostAndPort> formateServerConfigToNodes(String serverConfig)
            throws Exception {
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        String[] serverStrs = serverConfig.replace(";", ",").split(",");
        for (String serverStr : serverStrs) {
            String serverKv[] = serverStr.split(":");
            if (serverKv.length != 2) {
                throw new Exception("the server format is error:" + serverKv);
            }

            nodes.add(new HostAndPort(serverKv[0], Integer.valueOf(serverKv[1])));
        }

        return nodes;
    }

}
