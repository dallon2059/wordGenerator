package com.xupanP.func;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xupanP.bean.DataBean;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static String readFile(String path) {

        String content = "";

        File file = new File(path);
        if (!file.exists()) {
            return "";
        }

        Reader reader = null;
        BufferedReader br = null;

        try {
            reader = new FileReader(file);
            br = new BufferedReader(reader);
            String line = "";
            while ((line = br.readLine()) != null) {
                content+=line;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static DataBean readJsonToBean(String json) {

        JSONObject jsonObject = JSONObject.parseObject(json);
        DataBean dataBean = new DataBean();
        // 获取title
        String title = jsonObject.getString("title");
        dataBean.setTitle(title);
        // 获取 已经解决的事情 list
        String solved = jsonObject.getString("solved");
        List<String> solvedList = new ArrayList<>();
        if (solved != null) {
            String[] solvedArray = solved.split("\n");
            for (int i = 0; i < solvedArray.length; i++) {
                solvedList.add(solvedArray[i]);
            }
        }
        dataBean.setSolvedList(solvedList);

        // 获取 没有解决的事情 list
        String unsolved = jsonObject.getString("unsolved");
        List<String> unsolvedList = new ArrayList<>();
        if (unsolved != null) {
            String[] unsolvedArray = unsolved.split("\n");
            for (int i=0; i< unsolvedArray.length; i++) {
                unsolvedList.add(unsolvedArray[i]);
            }
        }
        dataBean.setUnsolvedList(unsolvedList);
        // 获取一）付款邮件相关：
        /**
         *
         *         aboutPaymentList.add("1,李四,週2日25分コース");
         *         aboutPaymentList.add("2,王五,200");
         *         aboutPaymentList.add("3,赵六");
         *         aboutPaymentList.add("4,燕七,週2日25分コース");
         *         aboutPaymentList.add("5,郭八");
         */
        JSONObject aboutPaymentObjectC1 = jsonObject.getJSONObject("C1");
        List<String> aboutPaymentList = new ArrayList<>();
        int C1_count = 1;
        while(aboutPaymentObjectC1.get(C1_count+"") != null) {
            JSONObject tempObject = aboutPaymentObjectC1.getJSONObject(C1_count+"");
            String type = tempObject.getString("type");

            if ("1".equals(type)) {
                String name = tempObject.getString("name");
                String course = tempObject.getString("course");
                aboutPaymentList.add(type + "," + name + "," + course);
            } else if ("2".equals(type)) {
                String name = tempObject.getString("name");
                String point = tempObject.getString("point");
                aboutPaymentList.add(type + "," + name + "," + point);
            } else if ("3".equals(type)) {
                String name = tempObject.getString("name");
                aboutPaymentList.add(type + "," + name);
            } else if ("4".equals(type)) {
                String name = tempObject.getString("name");
                String course = tempObject.getString("course");
                aboutPaymentList.add(type + "," + name + "," + course);
            } else if ("5".equals(type)) {
                String name = tempObject.getString("name");
                aboutPaymentList.add(type + "," + name);
            }
            C1_count++;
        }
        dataBean.setAboutPaymentList(aboutPaymentList);

        // 获取二）新会员入会相关：
        JSONObject memberNameObjectC2 = jsonObject.getJSONObject("C2");
        JSONArray memberName = memberNameObjectC2.getJSONArray("name");
        List<String> memberNameList = new ArrayList<>();
        for(int i=0; i<memberName.size(); i++) {
            memberNameList.add((String) memberName.get(i));
        }
        dataBean.setMemberNameList(memberNameList);

        // 三）会员联系邮件：
        JSONObject aboutMemberMailObjectC3 = jsonObject.getJSONObject("C3");
        List<List<String>> aboutMemberMailList = new ArrayList<>();

        int C3_count = 1;
        while(aboutMemberMailObjectC3.get(C3_count+"") != null) {
            JSONObject tempObject = aboutMemberMailObjectC3.getJSONObject(C3_count+"");
            String type = tempObject.getString("type");

            if ("1".equals(type)) {
                List<String> tempList = new ArrayList<>();
                String name = tempObject.getString("name");
                String mail = tempObject.getString("mail");
                String mailContent = tempObject.getString("mailContent");
                String replyContent = tempObject.getString("replyContent");
                tempList.add(type);
                tempList.add(name);
                tempList.add(mail);
                tempList.add(mailContent);
                tempList.add(replyContent);
                aboutMemberMailList.add(tempList);
            } else if ("2".equals(type)) {
                List<String> tempList = new ArrayList<>();
                String name = tempObject.getString("name");
                String mail = tempObject.getString("mail");
                String mailContent = tempObject.getString("mailContent");
                tempList.add(type);
                tempList.add(name);
                tempList.add(mail);
                tempList.add(mailContent);
                aboutMemberMailList.add(tempList);
            }

            C3_count++;
        }
        dataBean.setAboutMemberMailList(aboutMemberMailList);

        // 获取 四）会员お問い合わせ：
        JSONObject aboutMemberContactObjectC4 = jsonObject.getJSONObject("C4");
        List<List<String>> aboutMemberContactList = new ArrayList<>();

        int C4_count = 1;
        while(aboutMemberContactObjectC4.get(C4_count+"") != null) {
            JSONObject tempObject = aboutMemberContactObjectC4.getJSONObject(C4_count+"");
            String type = tempObject.getString("type");

            if ("1".equals(type)) {
                List<String> tempList = new ArrayList<>();
                String name = tempObject.getString("name");
                String mailContent = tempObject.getString("mailContent");
                String replyContent = tempObject.getString("replyContent");
                tempList.add(type);
                tempList.add(name);
                tempList.add(mailContent);
                tempList.add(replyContent);
                aboutMemberContactList.add(tempList);
            } else if ("2".equals(type)) {
                List<String> tempList = new ArrayList<>();
                String name = tempObject.getString("name");
                String mailContent = tempObject.getString("mailContent");
                tempList.add(type);
                tempList.add(name);
                tempList.add(mailContent);
                aboutMemberContactList.add(tempList);
            }
            C4_count++;
        }
        dataBean.setAboutMemberContactList(aboutMemberContactList);

        // 五）其它：
        JSONObject aboutOthersObjectC5 = jsonObject.getJSONObject("C5");
        List<List<String>> aboutOthersList = new ArrayList<>();

        int C5_count = 1;
        while(aboutOthersObjectC5.get(C5_count+"") != null) {
            JSONObject tempObject = aboutOthersObjectC5.getJSONObject(C5_count+"");
            String type = tempObject.getString("type");

            if ("1".equals(type)) {
                List<String> tempList = new ArrayList<>();
                String content = tempObject.getString("content");
                String mailContent = tempObject.getString("mail");
                tempList.add(type);
                tempList.add(content);
                tempList.add(mailContent);
                aboutOthersList.add(tempList);
            } else if ("2".equals(type)) {
                List<String> tempList = new ArrayList<>();
                String content = tempObject.getString("content");
                tempList.add(type);
                tempList.add(content);
                aboutOthersList.add(tempList);
            }
            C5_count++;
        }
        dataBean.setAboutOthersList(aboutOthersList);

        // D、需要告诉下一位值班负责人的事：list
        String inherit = jsonObject.getString("inherit");
        List<String> inheritList = new ArrayList<>();
        if (inherit != null) {
            String[] inheritArray = inherit.split("\n");
            for (int i = 0; i < inheritArray.length; i++) {
                inheritList.add(inheritArray[i]);
            }
        }
        dataBean.setInheritList(inheritList);

        return dataBean;
    }
}
