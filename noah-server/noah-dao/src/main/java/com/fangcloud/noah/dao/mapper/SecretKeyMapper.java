package com.fangcloud.noah.dao.mapper;

import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.SecretKeyEntity;

import java.util.List;

/**
 * Created by chenke on 16-3-5.
 */
@Repository
public interface SecretKeyMapper {

    /**
     * 新增密钥
     *
     * @param record
     */
    void insert(SecretKeyEntity record);

    /**
     * 查询所有有效密钥
     *
     * @return
     */
    List<SecretKeyEntity> selectValidSecretKeys();

}
