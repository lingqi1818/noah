package com.fangcloud.noah.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.fangcloud.noah.dao.entity.RiskTermEntity;

/**
 * Created by chenke on 16-9-23.
 */
@Repository
public interface RiskTermMapper {

    int insert(RiskTermEntity record);

    RiskTermEntity selectById(@Param("id") Integer id);

    void updateById(RiskTermEntity riskTermEntity);

    void deleteById(@Param("id") Integer id);

    List<RiskTermEntity> loadAllRecords();

    int selectRiskTermListCount(@Param("word") String word);

    List<RiskTermEntity> selectRiskTermList(@Param("offSet") int offSet,
                                            @Param("pageSize") int pageSize,
                                            @Param("word") String word);

    void updateStatus(RiskTermEntity record);

}
