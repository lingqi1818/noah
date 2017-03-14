package com.fangcloud.noah.service.util;

import org.apache.commons.lang3.StringUtils;

import com.fangcloud.noah.api.exception.RiskRuntimeException;

/**
 * Created by chenke on 16-8-20.
 */
public class AppVersionUtil {

    /**
     * appVersion 格式 x.x.x 如 3.1.0  3.3.1.0  10.0.0.0
     * @param appVersion1
     * @param appVersion2
     * @return
     */
    public static int compare(String appVersion1, String appVersion2) {

        if(StringUtils.isBlank(appVersion1)|| StringUtils.isBlank(appVersion2)){
            throw new RiskRuntimeException("appVersion is null");
        }

        String[] versionArray1 = appVersion1.split("\\.");

        String[] versionArray2 = appVersion2.split("\\.");

        Integer versionLength1 = versionArray1.length;
        Integer versionLength2 = versionArray2.length;

        int tmpLength = versionLength1>versionLength2?versionLength2:versionLength1;

        for(int i=0;i<tmpLength;i++){

            int result = versionArray1[i].compareTo(versionArray2[i]);

            if( result== 0){
                continue;
            }else {
                return Integer.valueOf(versionArray1[i]).compareTo(Integer.valueOf(versionArray2[i]));
            }
        }

        return versionLength1.compareTo(versionLength2);
    }

    public static void main(String[] args) {
        String appVersion1 = "3.0.5";

        String appVersion2 = "3.0.5";
        System.out.println(compare(appVersion1, appVersion2));
    }
}
