package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zhangjiazhao on 2017/4/27.
 */
public class FileUtil {

    public static String readFileByBytes(String filePath){
        File file = new File(filePath);
        if(!file.exists() || !file.isFile()){
            return null;
        }

        StringBuffer content = new StringBuffer();

        try {
            byte[] temp = new byte[1024];
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                while(fileInputStream.read(temp) != -1){
                    content.append(new String(temp));
                    temp = new byte[1024];
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }



}
