package com.hzl.controller;

import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {
    /**
     *
     * @param file 要上传的文件
     * @return
     */
    @PostMapping("fileUpload")
    public Result upload(@RequestParam("file") MultipartFile file){

        // 要上传的目标文件存放路径
        String localPath = "D:/images";
        FileUtils fileUtils = new FileUtils();
        Map map = new HashMap();
        map.putAll(fileUtils.upload(file, localPath, file.getOriginalFilename()));

        if (map != null){
            return ResultFactory.buildSuccessResult(map,"上传成功");
        }else {
            return ResultFactory.buildFailResult("上传失败");
        }

    }

}
