package com.fangcloud.noah.dao.entity;

/**
 * Created by chenke on 16-10-28.
 */
public class IpEntity {

    private Integer id;

    //ip段开始
    private String ipStart;

    //ip段结束
    private String ipEnd;

    //数字段开始
    private String ipStartNum;

    //数字段结束
    private String ipEndNum;

    //大洲
    private String continent;

    //国家
    private String country;

    //省份
    private String province;

    //城市
    private String city;

    //区县
    private String district;

    //运营商
    private String isp;

    //区划代码
    private String areaCode;

    //国家英文名称
    private String countryEnglish;

    //国家简码
    private String countryCode;

    //经度
    private String longitude;

    //纬度
    private String latitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpStart() {
        return ipStart;
    }

    public void setIpStart(String ipStart) {
        this.ipStart = ipStart;
    }

    public String getIpEnd() {
        return ipEnd;
    }

    public void setIpEnd(String ipEnd) {
        this.ipEnd = ipEnd;
    }

    public String getIpStartNum() {
        return ipStartNum;
    }

    public void setIpStartNum(String ipStartNum) {
        this.ipStartNum = ipStartNum;
    }

    public String getIpEndNum() {
        return ipEndNum;
    }

    public void setIpEndNum(String ipEndNum) {
        this.ipEndNum = ipEndNum;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getCountryEnglish() {
        return countryEnglish;
    }

    public void setCountryEnglish(String countryEnglish) {
        this.countryEnglish = countryEnglish;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
