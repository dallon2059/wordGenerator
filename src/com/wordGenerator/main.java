package com.xupanP;

import com.xupanP.bean.DataBean;
import com.xupanP.func.FileUtils;
import com.xupanP.func.GenerateWord;
import java.io.IOException;


public class main {

    public static void main(String[] args) throws IOException {

//        System.out.println(args[0]);
//        System.out.println(args[1]);

        String path = "D:\\code\\XPwordgenerator\\out\\data.json";

        // 获取json内容
        String jsonStr = FileUtils.readFile(path);

        // 把json内容转为bean
        DataBean dataBean = FileUtils.readJsonToBean(jsonStr);

        // 生成word
        String outPath = "D:/code/XPwordgenerator/out";
        GenerateWord.generate(dataBean, outPath);

    }

}
