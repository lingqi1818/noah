package com.fangcloud.noah.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

/**
 * redis缓存服务
 * 
 * @author chenke
 * @date 2015年11月3日 下午6:36:04
 */
public interface RedisService {
    Long hset(String key, String field, String value);

    Long hsetnx(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String... fields);

    String hmset(String key, Map<String, String> hash);

    List<String> hmget(String key, String... fields);

    Map<String, String> hgetAll(String key);

    Set<String> hkeys(String key);

    List<String> hvals(String key);

    public void set(String key, String value);

    public void expire(String key, int seconds);

    public String get(String key);

    public void del(String key);

    public void sadd(String key, String... members);

    public Long scard(String key);

    public Set<String> smembers(String key);

    public void srem(String key, String member);

    public boolean sismember(String key, String member);

    public void setnx(String key, String value);

    public void setex(String key, int seconds, String value);

    public Long decrBy(String key, long integer);

    public Long decr(String key);

    public Long incrBy(String key, long integer);

    public Long incr(String key);

    public void append(String key, String value);

    public void subscribe(final JedisPubSub jedisPubSub, final String... channels);

    public Long publish(final String channel, final String message);

    public Long lPush(String key, String... values);

    public String rPop(String key);

    Long zcount(String key, double min, double max);

    Long zcount(String key, String min, String max);

    Long zadd(String key, double score, String member);

    Long zremrangeByScore(String key, double start, double end);

    Long zremrangeByScore(String key, String start, String end);

    public ScanResult<String> sScan(String key, String cursor, ScanParams scanParams);

    public Long expireAt(final String key, final long unixTime);
}
