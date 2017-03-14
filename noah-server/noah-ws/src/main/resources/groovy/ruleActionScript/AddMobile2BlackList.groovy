import com.helijia.framework.risk.api.enums.NameListGrade
import com.helijia.framework.risk.api.enums.NameListType
import com.helijia.framework.risk.api.model.NameList
import com.helijia.risk.dao.enums.EventType
import com.helijia.risk.dao.enums.NameListStatusType
import com.helijia.risk.ruleengine.rule.action.Action
import com.helijia.risk.service.NameListService
import com.helijia.risk.util.SpringContextUtil
import org.apache.commons.lang3.StringUtils

import java.sql.Timestamp

public class AddMobile2BlackList implements Action{

    @Override
    void execute(Map<String, Object> contextMap,String description) {
        NameListService nameListService = SpringContextUtil.getBean(NameListService.class);

        NameList nameList = new NameList();
        String mobile;

        String eventType = String.valueOf(contextMap.get("eventType"));
        if (eventType.equals(EventType.marketing.getCode()) || eventType.equals(EventType.message.getCode())) {
            mobile = String.valueOf(contextMap.get("account_mobile"));
        } else {
            mobile = String.valueOf(contextMap.get("account_login"));
        }

        if (StringUtils.isBlank(mobile) || "null".equals(mobile)) {
            return;
        }
        nameList.setContent(mobile);
        nameList.setType(NameListType.MOBILE.getCode());
        nameList.setStatus(NameListStatusType.INIT.getCode());
        nameList.setGrade(NameListGrade.BLACK.getCode());
        nameList.setRemark(description);
        nameList.setGmtCreated(new Timestamp(new Date().getTime()));
        nameList.setGmtModified(nameList.getGmtCreated());
        nameListService.saveNameList(nameList);
    }

}