package com.wordGenerator;

import com.wordGenerator.bean.DataBean;
import com.wordGenerator.func.FileUtils;
import com.wordGenerator.func.GenerateWord;

import java.io.IOException;


public class main {

    public static void main(String[] args) throws IOException {

//        System.out.println(args[0]);
//        System.out.println(args[1]);

        // 读入路径
        String path = args[0];
        // 图片路径
        MyWordUtils.IMG_PATH = args[1];

        // 获取json内容
        String jsonStr = FileUtils.readFile(path);

        // 把json内容转为bean
        DataBean dataBean = FileUtils.readJsonToBean(jsonStr);

        // 输出路径
        String outPath = args[2];
        // 生成word
        GenerateWord.generate(dataBean, outPath);

    }

}
