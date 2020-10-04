package com.wordGenerator.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBean {

    /**
     * 第一行 xxxx年xx月xx日晚间值班报告
     */
    private int year;

    private int month;

    private int day;

    /**
     * 已经解决的事情
     */
    private List<String> solvedList;

    /**
     * 未解决的事情
     */
    private List<String> unsolvedList;

    /**
     * 付款相关
     */
    private List<String> aboutPaymentList;

//    /**
//     * 付款相关的图片base64的Map
//     * 1：会员套餐课程自动付款邮件，需要设置有效期限。更新前(1)、更新操作(1)、更新后(1)
//     * 2：会员购买点数套餐，需要手动设置。更新前(2)、更新操作(1)、更新后(2)
//     * 3：会员购买80点プレゼントポイント，需要手动加点。更新前(1)、更新操作(1)、更新后(1)
//     * 4：会员套餐课程自动付款邮件，系统已经自动设置，无需手动处理。更新前(1)
//     * 5：会员取消了自动付款。
//     * // key是序号
//     * "1":"base64(1),base64(2),base64(3)"
//     * "2":"base64(1),base64(2),base64(3),base64(4),base64(5)"
//     * "3":"base64(1),base64(2),base64(3)"
//     * "4":"base64(1)"
//     * 没有的场合
//     * "1":"base64(1),nothing,base64(3)"
//     */
//    private Map<String, String> imgBase64Map;

    /**
     * 会员入会相关 会员名称
     */
    private List<String> memberNameList;

    /**
     * 会员联系邮件
     */
    private List<List<String>> aboutMemberMailList;

    /**
     * 会員お問い合わせ
     */
    private List<List<String>> aboutMemberContactList;

    /**
     * その他
     */
    private List<List<String>> aboutOthersList;

    /**
     * 需要告知下一位值班人的事情
     */
    private List<String> inheritList;

    private String title;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<String> getSolvedList() {
        return solvedList;
    }

    public void setSolvedList(List<String> solvedList) {
        this.solvedList = solvedList;
    }

    public List<String> getUnsolvedList() {
        return unsolvedList;
    }

    public void setUnsolvedList(List<String> unsolvedList) {
        this.unsolvedList = unsolvedList;
    }

    public List<String> getAboutPaymentList() {
        return aboutPaymentList;
    }

    public void setAboutPaymentList(List<String> aboutPaymentList) {
        this.aboutPaymentList = aboutPaymentList;
    }

    public List<String> getMemberNameList() {
        return memberNameList;
    }

    public void setMemberNameList(List<String> memberNameList) {
        this.memberNameList = memberNameList;
    }

    public List<List<String>> getAboutMemberMailList() {
        return aboutMemberMailList;
    }

    public void setAboutMemberMailList(List<List<String>> aboutMemberMailList) {
        this.aboutMemberMailList = aboutMemberMailList;
    }

    public List<List<String>> getAboutMemberContactList() {
        return aboutMemberContactList;
    }

    public void setAboutMemberContactList(List<List<String>> aboutMemberContactList) {
        this.aboutMemberContactList = aboutMemberContactList;
    }

    public List<List<String>> getAboutOthersList() {
        return aboutOthersList;
    }

    public void setAboutOthersList(List<List<String>> aboutOthersList) {
        this.aboutOthersList = aboutOthersList;
    }

    public List<String> getInheritList() {
        return inheritList;
    }

    public void setInheritList(List<String> inheritList) {
        this.inheritList = inheritList;
    }

//    public Map<String, String> getImgBase64Map() {
//        return imgBase64Map;
//    }

//    public void setImgBase64Map(Map<String, String> imgBase64Map) {
//        this.imgBase64Map = imgBase64Map;
//    }
    /**
     * 获取大标题
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
