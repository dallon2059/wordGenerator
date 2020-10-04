package com.wordGenerator.func;

import com.wordGenerator.bean.DataBean;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GenerateWord {

    public static void generate(DataBean data, String path) throws IOException {

        // A、已经解决的事情：
        List<String> solvedList = data.getSolvedList();
        // B、没有解决的事情：
        List<String> unsolvedList = data.getUnsolvedList();
        // 一）付款邮件相关：
        List<String> aboutPaymentList = data.getAboutPaymentList();
        // 二）新会员入会相关：
        List<String> memberNameList = data.getMemberNameList();
        // 三）会员联系邮件：
        List<List<String>> aboutMemberMailList = data.getAboutMemberMailList();
        // 四）会员お問い合わせ：
        List<List<String>> aboutMemberContactList = data.getAboutMemberContactList();
        // 五）其它：
        List<List<String>> aboutOthersList = data.getAboutOthersList();
        // D、需要告诉下一位值班负责人的事：
        List<String> inheritList = data.getInheritList();


        // 创建一个word对象
        XWPFDocument doc = new XWPFDocument();

        /**
         * xxxx年xx月xx日晚间值班报告
         */
        XWPFParagraph p1 = doc.createParagraph();
        // 设置段落格式
        p1.setAlignment(ParagraphAlignment.LEFT);
        p1.setVerticalAlignment(TextAlignment.CENTER);
        // 段落的一行
        XWPFRun p1r1 = p1.createRun();

        p1r1.setText(data.getTitle());
        p1r1.setFontSize(16); // 设置字体大小
        p1r1.setFontFamily("SimSun"); // 设置字体
        p1r1.setFontFamily("Times New Roman");

        /**
         * 固定文言 A、已经解决的事情：
         */
        MyWordUtils.setHeader(doc, "A、已经解决的事情：");

        /**
         * A、已经解决的事情：的具体内容
         */
        if (solvedList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            for (int i = 0; i < solvedList.size(); i++) {
                MyWordUtils.setData(doc, (i + 1) + "、" + solvedList.get(i));
            }
        }

        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 B、没有解决的事情：
         */
        MyWordUtils.setHeader(doc, "B、没有解决的事情：");

        /**
         * A、已经解决的事情：的具体内容
         */
        if (unsolvedList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            for (int i = 0; i < unsolvedList.size(); i++) {
                MyWordUtils.setData(doc, (i + 1) + "、" + unsolvedList.get(i));
            }
        }


        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 C、需要告诉事务所的事情：
         */
        MyWordUtils.setHeader(doc, "C、需要告诉事务所的事情：");

        /**
         * 固定文言 一）付款邮件相关：
         */
        MyWordUtils.setSubHeader(doc, "一）付款邮件相关：");

        if (aboutPaymentList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            MyWordUtils.setPaymentText(doc, aboutPaymentList);
        }

        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 二）新会员入会相关：
         */
        MyWordUtils.setSubHeader(doc, "二）新会员入会相关：");

        if (memberNameList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            MyWordUtils.setMemberText(doc, memberNameList);
        }

        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 三）会员联系邮件：
         */
        MyWordUtils.setSubHeader(doc, "三）会员联系邮件：");

        if (aboutMemberMailList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            MyWordUtils.setMemberMailText(doc, aboutMemberMailList);
        }

        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 四）会员お問い合わせ：
         */
        MyWordUtils.setSubHeader(doc, "四）会员お問い合わせ：");

        if (aboutMemberContactList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            MyWordUtils.setMemberContactText(doc, aboutMemberContactList);
        }

        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 五）其它：
         */
        MyWordUtils.setSubHeader(doc, "五）其它：");

        if (aboutOthersList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            MyWordUtils.setOthersText(doc, aboutOthersList);
        }

        // 空白行
        doc.createParagraph().createRun();

        /**
         * 固定文言 D、需要告诉下一位值班负责人的事：
         */
        MyWordUtils.setHeader(doc, "D、需要告诉下一位值班负责人的事：");

        if (inheritList.size() == 0) {
            MyWordUtils.setNoData(doc);
        } else {
            for (int i = 0; i < inheritList.size(); i++) {
                MyWordUtils.setData(doc, (i + 1) + "、" + inheritList.get(i));
            }
        }

        FileOutputStream out = new FileOutputStream(path + "/" + data.getTitle() + ".docx");
        doc.write(out);
        out.close();
    }
}
