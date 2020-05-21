package com.hzl.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return UUIDUtil.createUUID() + getSuffix(fileOriginName);
    }

    /**
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public Map upload(MultipartFile file, String path, String fileName){

        // 生成新的文件名
        String realPath = path + "/" + getFileName(fileName);

        //使用原文件名
        //String realPath = path + "/" + fileName;

        //在指定路径下创建一个文件
        File targetFile = new File(path+"/", realPath);

        try {
            //保存文件
            file.transferTo(targetFile);
            String tmp_path = "images/"+realPath;
            String url = "http://localhost:8082/images/"+realPath;
            Map map = new HashMap();
            map.put("tmp_path",tmp_path);
            map.put("url",url);
            return map;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }


}