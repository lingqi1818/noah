package com.fangcloud.noah.service.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fangcloud.noah.api.api.enums.NameListGrade;
import com.fangcloud.noah.api.api.model.NameList;
import com.fangcloud.noah.dao.mapper.NameListMapper;
import com.fangcloud.noah.service.common.PageObject;

/**
 * Created by chenke on 16-8-20.
 */
@Service
public class NameListService {

    @Autowired
    private NameListMapper nameListMapper;


    public void saveNameList(NameList nameList) {
        nameListMapper.insert(nameList);
    }


    public PageObject<NameList> queryNameList(Integer pageNum, Integer nameType, Integer nameGrade, String tel) {
        int totalItem = nameListMapper.selectNameListCount(nameType, nameGrade, tel);
        if (pageNum == null) {
            pageNum = 1;
        }
        PageObject<NameList> datas = new PageObject<NameList>(pageNum, totalItem);
        List<NameList> rst = nameListMapper.selectNameList(datas.getOffSet(), datas.getPageSize(), nameType, nameGrade, tel);
        datas.initViewData(rst);
        return datas;
    }

    public NameList queryNameObj(Integer nameId) {
        return nameListMapper.selectNameListById(nameId);
    }

    public void updateNameList(NameList nameList) {
        nameListMapper.update(nameList);
    }


    public boolean isBlackNameList(Integer type, String content) {

        NameList nameList = nameListMapper.selectNameListByContent(type, content);

        if (nameList != null && nameList.getGrade().equals(NameListGrade.BLACK.getCode())) {
            return true;
        }
        return false;
    }

    public List<NameList> getSyncBlackList() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        String startTime = sdf.format(calendar.getTime());

        List<NameList> nameLists = nameListMapper.selectNameListForSync(startTime);

        return nameLists;
    }

    public void updateNameListStatus(NameList record) {

        nameListMapper.updateNameListStatus(record);
    }
}

