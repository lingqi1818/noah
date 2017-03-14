package com.fangcloud.noah.redis.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fangcloud.noah.redis.RedisService;

import junit.framework.TestCase;

public class ClusterRedisTest extends TestCase {
    private RedisService redisService;

    public void setUp() {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                new String[] { "spring-datasource.xml", "spring-service.xml" });
        redisService = (RedisService) ac.getBean("redisService");
    }

    public void testMutiThreadGetAndThread() throws InterruptedException {
        for (int i = 0; i < 100; i++) {

            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        String key = "k" + System.currentTimeMillis();
                        //long start = System.currentTimeMillis();
                        redisService.set(key, "haha");
                        //                        System.out.println(
                        //                                "set time:" + Long.valueOf(System.currentTimeMillis() - start));
                        //                        start = System.currentTimeMillis();
                        assertEquals("haha", redisService.get(key));
                        //                        System.out.println(
                        //                                "get time:" + Long.valueOf(System.currentTimeMillis() - start));

                    }
                }
            }).start();
        }
        Thread.currentThread().join();
    }

    public void testClusterRedisService() throws InterruptedException {
        redisService.sadd("easy_ops_redis_hit_stat_monitor_keys_set_17",
                "testNameSpace_testAppName_test");
        System.out.println(redisService.sismember("easy_ops_redis_hit_stat_monitor_keys_set_" + 17,
                "testNameSpace_testAppName_test"));
        System.out.println(redisService.smembers("easy_ops_redis_hit_stat_monitor_keys_set_" + 17));
        redisService.set("test_123", "123");
        assertEquals("123", redisService.get("test_123"));

        //        Thread.currentThread().join();

        //        Jedis jedis1 = new Jedis("127.0.0.1", 7001);
        //        Jedis jedis2 = new Jedis("127.0.0.1", 7004);
        //
        //        for (int i = 5462; i <= 10923; i++) {
        //            jedis1.clusterSetSlotStable(i);
        //            jedis2.clusterSetSlotStable(i);
        //        }
        //        for (int i = 5462; i < 10923; i++) {
        //            jedis1.clusterSetSlotMigrating(i, "2aef0e54237d693433d38099e04f551efef06fc4");
        //            jedis2.clusterSetSlotImporting(i, "8abbb5e176f34d9b2a38666acc7f1846384c5930");
        //            int count = jedis1.clusterCountKeysInSlot(i).intValue();
        //            List<String> keys = jedis1.clusterGetKeysInSlot(i, count);
        //            if (keys != null) {
        //                for (String key : keys) {
        //                    jedis1.migrate("127.0.0.1", 7004, key, 0, 5000);
        //                }
        //            }
        //        }
        //        System.out.println(jedis1.clusterNodes());
    }

    //    //    public void testMigrateSlots() {
    //    //        List<RedisClusterNode> nodes = new ArrayList<RedisClusterNode>();
    //    //        nodes.add(new RedisClusterNode("127.0.0.1", 7000));
    //    //        redisClusterManager.createRedisCluster("test", "test", "test-cluster", nodes);
    //    //    }
    //
    //    public void testAddNodesToCluster() throws InterruptedException {
    //        List<RedisClusterNode> nodes = new ArrayList<RedisClusterNode>();
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7000));
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7001));
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7002));
    //        redisClusterManager.createRedisCluster("test", "test", "test-cluster", nodes);
    //        RedisClusterInfo info = redisClusterManager.infoCluster("test", "test", "test-cluster");
    //        assertEquals("ok", info.getClusterInfo().get("cluster_state"));
    //        assertEquals(3, info.getNodesInfo().size());
    //        RedisClusterNode[] addNodes = new RedisClusterNode[2];
    //        addNodes[0] = new RedisClusterNode("127.0.0.1", 7000);
    //        addNodes[1] = new RedisClusterNode("127.0.0.1", 7003);
    //        redisClusterManager.addNodesToCluster("test", "test", "test-cluster", addNodes);
    //        Thread.sleep(20000);
    //        RedisClusterInfo info1 = redisClusterManager.infoCluster("test", "test", "test-cluster");
    //        assertEquals("ok", info1.getClusterInfo().get("cluster_state"));
    //        assertEquals(4, info1.getNodesInfo().size());
    //        List<RedisClusterNodeInfo> info1Nodes = info1.getNodesInfo();
    //
    //        for (RedisClusterNodeInfo node : info1Nodes) {
    //            if (node.getNode().getPort() == 7003) {
    //                assertEquals(Role.MASTER.name(), node.getRole().name());
    //                break;
    //            }
    //        }
    //
    //        redisClusterManager.dismissRedisCluster("test", "test", "test-cluster");
    //        Thread.sleep(20000);
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7003));
    //        for (RedisClusterNode node : nodes) {
    //            Jedis jedis = new Jedis(node.getHost(), node.getPort());
    //            assertEquals(1, jedis.clusterNodes().split("\n").length);
    //            jedis.close();
    //        }
    //    }
    //
    //    public void testCreateCluster() throws Exception {
    //        List<RedisClusterNode> nodes = new ArrayList<RedisClusterNode>();
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7000));
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7001));
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7002));
    //        redisClusterManager.createRedisCluster("test", "test", "test-cluster", nodes);
    //        RedisClusterInfo info = redisClusterManager.infoCluster("test", "test", "test-cluster");
    //        assertEquals("ok", info.getClusterInfo().get("cluster_state"));
    //        assertEquals(3, info.getNodesInfo().size());
    //        redisClusterManager.dismissRedisCluster("test", "test", "test-cluster");
    //        Thread.sleep(20000);
    //        for (RedisClusterNode node : nodes) {
    //            Jedis jedis = new Jedis(node.getHost(), node.getPort());
    //            assertEquals(1, jedis.clusterNodes().split("\n").length);
    //            jedis.close();
    //        }
    //    }
    //
    //    public void testAddSlaveCluster() throws InterruptedException {
    //        redisClusterManager.dismissRedisCluster("test", "test", "test-cluster");
    //        List<RedisClusterNode> nodes = new ArrayList<RedisClusterNode>();
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7000));
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7001));
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7002));
    //        redisClusterManager.createRedisCluster("test", "test", "test-cluster", nodes);
    //        RedisClusterInfo info = redisClusterManager.infoCluster("test", "test", "test-cluster");
    //        assertEquals("ok", info.getClusterInfo().get("cluster_state"));
    //        assertEquals(3, info.getNodesInfo().size());
    //
    //        redisClusterManager.addSlaveToMaster("test", "test", "test-cluster",
    //                new RedisClusterNode("127.0.0.1", 7000), new RedisClusterNode("127.0.0.1", 7003));
    //
    //        Thread.sleep(20000);
    //        RedisClusterInfo info1 = redisClusterManager.infoCluster("test", "test", "test-cluster");
    //        assertEquals("ok", info1.getClusterInfo().get("cluster_state"));
    //        assertEquals(4, info1.getNodesInfo().size());
    //        List<RedisClusterNodeInfo> info1Nodes = info1.getNodesInfo();
    //
    //        for (RedisClusterNodeInfo node : info1Nodes) {
    //            if (node.getNode().getPort() == 7003) {
    //                assertEquals(Role.SLAVE.name(), node.getRole().name());
    //                break;
    //            }
    //        }
    //
    //        redisClusterManager.dismissRedisCluster("test", "test", "test-cluster");
    //        Thread.sleep(20000);
    //        nodes.add(new RedisClusterNode("127.0.0.1", 7003));
    //        for (RedisClusterNode node : nodes) {
    //            Jedis jedis = new Jedis(node.getHost(), node.getPort());
    //            assertEquals(1, jedis.clusterNodes().split("\n").length);
    //            jedis.close();
    //        }
    //    }
}
