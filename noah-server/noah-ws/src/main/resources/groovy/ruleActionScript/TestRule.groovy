import com.helijia.framework.redis.RedisService
import com.helijia.framework.risk.model.BlackListType
import com.helijia.risk.ruleengine.rule.action.Action
import com.helijia.risk.util.SpringContextUtil
import org.apache.commons.lang3.StringUtils

public class TestRule implements Action{

    @Override
    void execute(Map<String, Object> contextMap,String description) {
        RedisService redisService = SpringContextUtil.getBean(RedisService.class);

        String mobile = String.valueOf(contextMap.get("account_mobile"));
        println 'aaaa';
        if(StringUtils.isNotBlank(mobile) && !mobile.equals("null")){
            println 'bbbb';
            redisService.setex(BlackListType.RISK_BLACK_LIST_MOBILE_TOO_FREQUENTLY.getValue()+"_"+mobile,60,mobile);
            println BlackListType.RISK_BLACK_LIST_MOBILE_TOO_FREQUENTLY.getValue()+"_"+mobile
            println 'cccc';
        }
    }

}