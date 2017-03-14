package com.fangcloud.noah.service.apiservice;

import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.api.exception.RiskRuntimeException;
import com.fangcloud.noah.dao.entity.api.CardOrder;
import com.fangcloud.noah.dao.entity.api.UsOrder;
import com.fangcloud.noah.dao.entity.api.User;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by chenke on 16-8-30.
 */
@Service
public class ApiService {


    @Resource
    private JdbcTemplate jdbcTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public UsOrder getUsOrderByOrderSeq(String orderSeq) {

        String querySql = "SELECT o.id,o.order_seq orderSeq,o.user_id userId,o.extra_fee_price extraFeePrice,o.should_pay_price shouldPayPrice,u.mobile userMobile " +
                " FROM us_order o join user u on o.user_id = u.userid where o.order_seq = '" + orderSeq+"'";

        List<UsOrder> usOrderList = jdbcTemplate.query(querySql, new RowMapper<UsOrder>() {
            @Override
            public UsOrder mapRow(ResultSet resultSet, int i) throws SQLException {
                UsOrder usOrder = new UsOrder();
                usOrder.setId(resultSet.getInt("id"));
                usOrder.setOrderSeq(resultSet.getString("orderSeq"));
                usOrder.setUserId(resultSet.getString("userId"));
                usOrder.setUserMobile(resultSet.getString("userMobile"));
                usOrder.setExtraFeePrice(resultSet.getBigDecimal("extraFeePrice"));
                usOrder.setShouldPayPrice(resultSet.getBigDecimal("shouldPayPrice"));
                return usOrder;
            }
        });

        if (CollectionUtils.isNotEmpty(usOrderList)) {
            return usOrderList.get(0);
        } else {
            return null;
        }
    }

    public CardOrder getCardOrderByOrderSeq(String orderSeq) {

        String querySql = "SELECT o.id, o.order_seq orderSeq,o.user_id userId,u.mobile userMobile FROM card_order o join user u on o.user_id = u.userid where o.order_seq = '" + orderSeq+"'";


        List<CardOrder> resultList = jdbcTemplate.query(querySql, new RowMapper<CardOrder>() {
            @Override
            public CardOrder mapRow(ResultSet resultSet, int i) throws SQLException {
                CardOrder cardOrder = new CardOrder();
                cardOrder.setId(resultSet.getInt("id"));
                cardOrder.setOrderSeq(resultSet.getString("orderSeq"));
                cardOrder.setUserId(resultSet.getString("userId"));
                cardOrder.setUserMobile(resultSet.getString("userMobile"));
                return cardOrder;
            }
        });

        if (CollectionUtils.isNotEmpty(resultList)) {
            return resultList.get(0);
        } else {
            return null;
        }
    }


    public User getUserByUserId(String userId) {

        String querySql = "select id,userid,username,mobile from user where userid='" + userId+"'";

        List<User> resultList = jdbcTemplate.query(querySql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setMobile(resultSet.getString("mobile"));
                user.setUserid(resultSet.getString("userid"));
                user.setUsername(resultSet.getString("username"));
                return user;
            }
        });

        if (CollectionUtils.isNotEmpty(resultList)) {
            return resultList.get(0);
        } else {
            return null;
        }

    }


    public boolean isSecondKillProduct(String productId) {

        String querySql = "select count(1) from artisan_product p where p.product_id = '"+productId+"' and active_tag_dict_id in (select active_tag from secondkill_active_tag) limit 1 ";

        Boolean result = jdbcTemplate.queryForObject(querySql, Boolean.class);
        return result;
    }


    public void saveBlackList(final NameList record) {

        String querySql = "select id from hlj.black_list where type='03' and mobile = '" + record.getContent()+"'";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(querySql);

        if (CollectionUtils.isNotEmpty(result)) {
            return;
        }

        String updateSql = "insert into black_list(type,create_time,mobile,remark) value(?,?,?,?)";

        jdbcTemplate.update(updateSql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, "03");
                ps.setString(2, sdf.format(System.currentTimeMillis()));
                ps.setString(3, record.getContent());
                ps.setString(4, record.getRemark());
            }
        });
    }


    /**
     * api手机黑名单解封为白名单
     *
     * @param mobile
     */
    public void updateBlackList2White(String mobile) {

        if (StringUtils.isBlank(mobile)) {
            throw new RiskRuntimeException("mobile is null !");
        }

        String querySql = "select id from hlj.black_list where type='03' and mobile = '" + mobile+"'";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(querySql);

        if (CollectionUtils.isNotEmpty(result)) {

            final Object id = result.get(0).get("id");

            String updateSql = "update black_list set type = ? , remark = ? where id = ?";

            jdbcTemplate.update(updateSql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, "00");
                    ps.setString(2, "风控解封");
                    ps.setObject(3, id);

                }
            });

        }
    }

    public List<Map<String,Object>> getDeviceCouponUseRecord(List<String> deviceIdList) {

        if(CollectionUtils.isEmpty(deviceIdList)){
            return null;
        }

        String ids = "";
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<deviceIdList.size();i++){
            if(i == (deviceIdList.size()-1)){
                sb.append("'"+deviceIdList.get(i)+"'");
            }else {
                sb.append("'"+deviceIdList.get(i)+"',");
            }
        }

        ids = sb.toString();



        String querySql = "select r.beacon_id,c.coupon_code,c.coupon_name,u.mobile,r.update_time from device_coupon_use_record r\n" +
                "join coupon_use c on r.coupon_id = c.id \n" +
                "join user u on c.user_id = u.userid\n" +
                "where r.status = 1\n" +
                "and r.beacon_id in("+ids+")\n" +
                "order by r.id desc";

        List<Map<String,Object>> result = jdbcTemplate.queryForList(querySql);


        return result;
    }
}
