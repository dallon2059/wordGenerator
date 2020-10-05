package com.wordGenerator.func;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class MyWordUtils {

    public static String IMG_PATH;


    public static void setBackgroundColor(XWPFRun r, String bgc) {

        CTRPr pr = r.getCTR().getRPr();
        if (pr == null) {
            pr = r.getCTR().addNewRPr();
        }

        CTShd shd = pr.isSetShd() ? pr.getShd() : pr.addNewShd();
        shd.setVal(STShd.Enum.forInt(38));
        shd.setColor(bgc); //背景色
    }

    /**
     * 设置段落缩进信息 1厘米≈567
     * 设置缩进
     * @param p
     * @param firstLine
     * @param firstLineChar
     * @param hanging
     * @param hangingChar
     * @param right
     * @param rigthChar
     * @param left
     * @param leftChar
     */
    public static void setParagraphIndInfo(XWPFParagraph p, String firstLine,
                                           String firstLineChar, String hanging, String hangingChar,
                                           String right, String rigthChar, String left, String leftChar) {
        CTPPr pPPr = null;
        if (p.getCTP() != null) {
            if (p.getCTP().getPPr() != null) {
                pPPr = p.getCTP().getPPr();
            } else {
                pPPr = p.getCTP().addNewPPr();
            }
        }
        CTInd pInd = pPPr.getInd() != null ? pPPr.getInd() : pPPr.addNewInd();
        if (firstLine != null) {
            pInd.setFirstLine(new BigInteger(firstLine));
        }
        if (firstLineChar != null) {
            pInd.setFirstLineChars(new BigInteger(firstLineChar));
        }
        if (hanging != null) {
            pInd.setHanging(new BigInteger(hanging));
        }
        if (hangingChar != null) {
            pInd.setHangingChars(new BigInteger(hangingChar));
        }
        if (left != null) {
            pInd.setLeft(new BigInteger(left));
        }
        if (leftChar != null) {
            pInd.setLeftChars(new BigInteger(leftChar));
        }
        if (right != null) {
            pInd.setRight(new BigInteger(right));
        }
        if (rigthChar != null) {
            pInd.setRightChars(new BigInteger(rigthChar));
        }
    }

    public static void setHeader(XWPFDocument doc, String headerText) {
        XWPFParagraph p = doc.createParagraph();
        // 设置段落格式
        p.setAlignment(ParagraphAlignment.LEFT);
        p.setVerticalAlignment(TextAlignment.CENTER);

        XWPFRun r = p.createRun();
        r.setText(headerText);
        r.setFontSize(14);
        r.setFontFamily("SimSun");
        r.setFontFamily("Times New Roman");
        r.setColor("FF0000"); // 字体颜色
        r.setBold(true); // 设置粗体
        MyWordUtils.setBackgroundColor(r, "FFFF00"); // 背景颜色
    }

    public static void setImg(XWPFDocument doc, String base64ImgStr, String imgPath, String imgType) {
        // todo
        if ("nothing".equals(base64ImgStr)) {
            doc.createParagraph().createRun();
            return;
        }
        // word内容宽度
        int wordWidth = 368;

        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        FileInputStream fis = null;
        ByteArrayInputStream bais = null;
        BufferedImage bi = null;
        try {
            // 把图片文件生成到指定的文件夹
            byte[] imgBase64Bytes = decoder.decodeBuffer(base64ImgStr);
            bais = new ByteArrayInputStream(imgBase64Bytes);
            bi = ImageIO.read(bais);
            File img = new File(imgPath);
            if (!img.exists()) {
                img.createNewFile();
            }
            ImageIO.write(bi, imgType, img);
            // 把图片插入word中
            XWPFParagraph p = doc.createParagraph();
            XWPFRun r = p.createRun();
            fis = new FileInputStream(imgPath);
            // 计算图片大小
            int width = bi.getWidth();
            int height = bi.getHeight();
            if (width > wordWidth) {
                double times = wordWidth * 1.0 / width;
                width = wordWidth;
                height = (int)(height * times / 1);
            }

            r.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG, imgPath, Units.toEMU(width), Units.toEMU(height));
            fis.close();
            // 图片删除
            img.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void setSubHeader(XWPFDocument doc, String subHeaderText) {
        XWPFParagraph p = doc.createParagraph();
        // 设置段落格式
        p.setAlignment(ParagraphAlignment.LEFT);
        p.setVerticalAlignment(TextAlignment.CENTER);

        XWPFRun r = p.createRun();
        r.setText(subHeaderText);
        r.setFontSize(12);
        r.setFontFamily("SimSun");
        r.setFontFamily("Times New Roman");
        r.setColor("FF0000"); // 字体颜色
        r.setBold(true); // 设置粗体
    }

    public static void setNoData(XWPFDocument doc) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.setText("无");
        r.setFontSize(12);
        r.setFontFamily("SimSun");
        r.setFontFamily("Times New Roman");
    }

    public static void setData(XWPFDocument doc, String content) {
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r = p.createRun();
        r.setText(content);
        r.setFontSize(12);
        r.setFontFamily("SimSun");
        r.setFontFamily("Times New Roman");
    }

    public static void setPaymentText(XWPFDocument doc, List<String> paymentList) {

        for (int i = 0; i < paymentList.size(); i++) {

            String flag = paymentList.get(i).split(",")[0];
            // 图片文件存放路径
            String imgDir = IMG_PATH;

            /**
             *         aboutPaymentList.add("1,张三,週2日25分コース");
             *         aboutPaymentList.add("1,李四,週2日25分コース");
             *         aboutPaymentList.add("2,王五,200");
             *         aboutPaymentList.add("3,赵六");
             *         aboutPaymentList.add("4,燕七,週2日25分コース");
             *         aboutPaymentList.add("5,郭八");
             */
            if ("1".equals(flag)) {

                String name = paymentList.get(i).split(",")[1];
                String course = paymentList.get(i).split(",")[2];
                // 更新前图片
                String updateBefore = paymentList.get(i).split(",")[3];
                // 更新操作
                String updateOpe = paymentList.get(i).split(",")[4];
                // 更新后图片
                String updateAfter = paymentList.get(i).split(",")[5];


                MyWordUtils.setData(doc, (i + 1) + "、收到会员" + name + "的" + course + "的付款邮件，已经设置了有效期限，具体操作如下：");
                MyWordUtils.setData(doc, "更新前：");
                // doc.createParagraph().createRun();
                setImg(doc, updateBefore, imgDir + "img.png", "png");

                MyWordUtils.setData(doc, "更新操作：");
                // doc.createParagraph().createRun();
                setImg(doc, updateOpe,  imgDir + "img.png", "png");

                MyWordUtils.setData(doc, "更新后：");
                // doc.createParagraph().createRun();
                setImg(doc, updateAfter,  imgDir + "img.png", "png");
            } else if ("2".equals(flag)) {

                /**
                 * 会员XXX（手动输入）购买了XXX（手动输入）点点数，系统没有自动添加，已经手动添加，并且设置了有效期限，具体操作如下：
                 */
                String name = paymentList.get(i).split(",")[1];
                String point = paymentList.get(i).split(",")[2];

                // 更新前图片
                String updateBefore_1 = paymentList.get(i).split(",")[3];
                // 更新前图片
                String updateBefore_2 = paymentList.get(i).split(",")[4];
                // 更新操作
                String updateOpe = paymentList.get(i).split(",")[5];
                // 更新后图片
                String updateAfter_1 = paymentList.get(i).split(",")[6];
                // 更新后图片
                String updateAfter_2 = paymentList.get(i).split(",")[7];

                MyWordUtils.setData(doc, (i + 1) + "、会员" + name + "购买了" + point + "点点数，系统没有自动添加，已经手动添加，并且设置了有效期限，具体操作如下：");
                MyWordUtils.setData(doc, "更新前：");
                // doc.createParagraph().createRun();
                setImg(doc, updateBefore_1,  imgDir + "img.png", "png");
                setImg(doc, updateBefore_2,  imgDir + "img.png", "png");

                MyWordUtils.setData(doc, "更新操作：");
                // doc.createParagraph().createRun();
                setImg(doc, updateOpe,  imgDir + "img.png", "png");

                MyWordUtils.setData(doc, "更新后：");
                // doc.createParagraph().createRun();
                setImg(doc, updateAfter_1,  imgDir + "img.png", "png");
                setImg(doc, updateAfter_2,  imgDir + "img.png", "png");
            } else if ("3".equals(flag)) {

                /**
                 * 会员XXX（手动输入）购买了80点点数，系统没有自动添加，已经手动添加，具体操作如下：
                 */
                String name = paymentList.get(i).split(",")[1];

                // 更新前图片
                String updateBefore = paymentList.get(i).split(",")[2];
                // 更新操作
                String updateOpe = paymentList.get(i).split(",")[3];
                // 更新后图片
                String updateAfter = paymentList.get(i).split(",")[4];

                MyWordUtils.setData(doc, (i + 1) + "、会员" + name + "购买了80点点数，系统没有自动添加，已经手动添加，具体操作如下：");
                MyWordUtils.setData(doc, "更新前：");
                // doc.createParagraph().createRun();
                setImg(doc, updateBefore,  imgDir + "img.png", "png");

                MyWordUtils.setData(doc, "更新操作：");
                // doc.createParagraph().createRun();
                setImg(doc, updateOpe,  imgDir + "img.png", "png");

                MyWordUtils.setData(doc, "更新后：");
                // doc.createParagraph().createRun();
                setImg(doc, updateAfter,  imgDir + "img.png", "png");
            } else if ("4".equals(flag)) {

                /**
                 * 收到会员XXX（手动输入）的XXX（手动选择）的付款邮件，系统已经自动处理，没有操作，具体情况如下：
                 */
                String name = paymentList.get(i).split(",")[1];
                String course = paymentList.get(i).split(",")[2];

                // 更新前图片
                String updateBefore = paymentList.get(i).split(",")[3];

                MyWordUtils.setData(doc, (i + 1) + "、收到会员" + name + "的" + course + "的付款邮件，系统已经自动处理，没有操作，具体情况如下：");
                MyWordUtils.setData(doc, "更新前：");
                // doc.createParagraph().createRun();
                setImg(doc, updateBefore,  imgDir + "img.png", "png");
            } else if ("5".equals(flag)) {

                /**
                 * 会员XXX（手动输入）取消了自动付款（顧客が自動支払いをキャンセルしました）。【※ 内容（情况5）的格式为：除句号外，正文部分加粗＋下划线】
                 */
                String name = paymentList.get(i).split(",")[1];

                XWPFParagraph p = doc.createParagraph();

                XWPFRun r0 = p.createRun();
                r0.setText((i + 1) + "、");
                r0.setFontSize(12);
                r0.setFontFamily("SimSun");
                r0.setFontFamily("Times New Roman");
                r0.setBold(false);

                XWPFRun r1 = p.createRun();
                r1.setText("会员" + name + "取消了自动付款（顧客が自動支払いをキャンセルしました）");
                r1.setFontSize(12);
                r1.setFontFamily("SimSun");
                r1.setFontFamily("Times New Roman");
                r1.setBold(true);
                r1.setUnderline(UnderlinePatterns.SINGLE);

                XWPFRun r2 = p.createRun();
                r2.setText("。");
                r2.setFontSize(12);
                r2.setFontFamily("SimSun");
                r2.setFontFamily("Times New Roman");
                r2.setBold(false);
            }
        }


    }

    public static void setMemberText(XWPFDocument doc, List<String> memberNameList) {

        for (int i = 0; i < memberNameList.size(); i++) {
            // 获取会员名称
            String memberName = memberNameList.get(i);

            MyWordUtils.setData(doc, (i + 1) + "、新会员" + memberName + "登录，已经发送了问候邮件，并且申请添加了Skype好友。");

        }
    }

    public static void setMemberMailText(XWPFDocument doc, List<List<String>> aboutMemberMailList) {


        for (int i = 0; i<aboutMemberMailList.size(); i++) {

            List<String> memberMailList = aboutMemberMailList.get(i);

            String flag = memberMailList.get(0);

            // 有回复的场合
            if ("1".equals(flag)) {
                String name = memberMailList.get(1);
                String mailTitle = memberMailList.get(2);
                String mailContent = memberMailList.get(3);
                String mailReply = memberMailList.get(4);

                MyWordUtils.setData(doc, (i + 1) + "、收到会员" + name + "的邮件" + mailTitle + "，内容如下：");
                MyWordUtils.setMailContent(doc, mailContent);

                MyWordUtils.setData(doc, "我的回复：");
                MyWordUtils.setMailContent(doc, mailReply);

            } else if ("2".equals(flag)){
                String name = memberMailList.get(1);
                String mailTitle = memberMailList.get(2);
                String mailContent = memberMailList.get(3);

                MyWordUtils.setData(doc, (i + 1) + "、收到会员" + name + "的邮件" + mailTitle + "，没有回复，内容如下：");
                MyWordUtils.setMailContent(doc, mailContent);
            }
        }

    }

    /**
     * 设置邮件内容
     * @param doc
     * @param mailContent
     */
    private static void setMailContent(XWPFDocument doc, String mailContent) {

        if (mailContent == null) {
            mailContent = "";
        }

        String[] lineArray = mailContent.split("\n");

        for (int i = 0; i< lineArray.length; i++) {

            XWPFParagraph p = doc.createParagraph();
            MyWordUtils.setParagraphIndInfo(p, null, null, null, null, null, null, "440", "200");
            XWPFRun r = p.createRun();
            r.setText(lineArray[i]);
            r.setFontSize(10);
            r.setFontFamily("FangSong");
            r.setFontFamily("Times New Roman");
            MyWordUtils.setBackgroundColor(r, "DDDDDD");

        }
    }

    public static void setMemberContactText(XWPFDocument doc, List<List<String>> aboutMemberContactList) {


        for (int i = 0; i<aboutMemberContactList.size(); i++) {

            List<String> memberContactList = aboutMemberContactList.get(i);

            String flag = memberContactList.get(0);

            // 有回复的场合
            if ("1".equals(flag)) {
                String name = memberContactList.get(1);
                String mailContent = memberContactList.get(2);
                String mailReply = memberContactList.get(3);

                MyWordUtils.setData(doc, (i + 1) + "、收到会员" + name + "的お問い合わせ，内容如下：");
                MyWordUtils.setMailContent(doc, mailContent);

                MyWordUtils.setData(doc, "我的回复：");
                MyWordUtils.setMailContent(doc, mailReply);

            } else if ("2".equals(flag)){
                String name = memberContactList.get(1);
                String mailContent = memberContactList.get(2);

                MyWordUtils.setData(doc, (i + 1) + "、收到会员" + name + "的お問い合わせ，没有回复，内容如下：");
                MyWordUtils.setMailContent(doc, mailContent);
            }
        }
    }

    public static void setOthersText(XWPFDocument doc, List<List<String>> aboutOthersList) {

        for (int i = 0; i<aboutOthersList.size(); i++) {

            List<String> othersList = aboutOthersList.get(i);

            String flag = othersList.get(0);

            // 有回复的场合
            if ("1".equals(flag)) {
                String content = othersList.get(1);
                String mailContent = othersList.get(2);

                MyWordUtils.setData(doc, (i + 1) + "、" + content);
                MyWordUtils.setMailContent(doc, mailContent);

            } else if ("2".equals(flag)){
                String content = othersList.get(1);
                MyWordUtils.setData(doc, (i + 1) + "、" + content);
            }
        }
    }

    public static void setInheritText(XWPFDocument doc, List<String> inheritList) {

        for (int i = 0; i< inheritList.size(); i++) {
            MyWordUtils.setData(doc, inheritList.get(i));
        }
    }
}
