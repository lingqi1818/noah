package com.fangcloud.noah.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Redis集群管理器
 * 
 * @author chenke
 * @date 2016年5月13日 下午4:03:34
 */
public interface RedisClusterManager {
    /**
     * 创建一个集群
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     * @param nodes
     */
    public void createRedisCluster(String namespace, String appName, String clusterName,
                                   List<RedisClusterNode> nodes);

    /**
     * 遣散集群
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     */
    public void dismissRedisCluster(String namespace, String appName, String clusterName);

    /**
     * 添加节点到集群
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     * @param nodes
     */
    public void addNodesToCluster(String namespace, String appName, String clusterName,
                                  RedisClusterNode... nodes);

    /**
     * 从集群删除节点
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     * @param nodes
     */
    public void delNodesFromCluster(String namespace, String appName, String clusterName,
                                    RedisClusterNode... nodes);

    /**
     * 为集群中的master添加slave
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     * @param master
     * @param slave
     */
    public void addSlaveToMaster(String namespace, String appName, String clusterName,
                                 RedisClusterNode master, RedisClusterNode slave);

    /**
     * 在线数据迁移
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     * @param slots
     * @param src
     * @param dest
     */
    public void migrateSlots(String namespace, String appName, String clusterName, int[] slots,
                             RedisClusterNode src, RedisClusterNode dest);

    /**
     * 获取集群信息
     * 
     * @param namespace
     * @param appName
     * @param clusterName
     */
    public RedisClusterInfo infoCluster(String namespace, String appName, String clusterName);

    public static class RedisClusterInfo {
        private List<RedisClusterNodeInfo> nodesInfo;
        private Map<String, String>        clusterInfo = new HashMap<String, String>();

        public List<RedisClusterNodeInfo> getNodesInfo() {
            return nodesInfo;
        }

        public void setNodesInfo(List<RedisClusterNodeInfo> nodesInfo) {
            this.nodesInfo = nodesInfo;
        }

        public Map<String, String> getClusterInfo() {
            return clusterInfo;
        }

        public void addClusterInfo(String key, String value) {
            this.clusterInfo.put(key, value);
        }

    }

    public static class RedisClusterNodeInfo {
        public static enum Role {
            MASTER,
            SLAVE,
            UNKNOW
        }

        private RedisClusterNode             node      = new RedisClusterNode();
        private Role                         role;
        private List<Pair<Integer, Integer>> slotPairs = new ArrayList<Pair<Integer, Integer>>();
        private String                       status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public RedisClusterNode getNode() {
            return node;
        }

        public void setNode(RedisClusterNode node) {
            this.node = node;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public List<Pair<Integer, Integer>> getSlotPairs() {
            return slotPairs;
        }

        public void setSlotPair(Pair<Integer, Integer> pair) {
            this.slotPairs.add(pair);
        }

    }

    public static class RedisClusterNode {
        private String host;
        private int    port;
        private String nodeId;

        public RedisClusterNode() {

        }

        public RedisClusterNode(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public RedisClusterNode(String host, int port, String nodeId) {
            this.host = host;
            this.port = port;
            this.nodeId = nodeId;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
