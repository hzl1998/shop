package com.hzl.controller;

import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.utils.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
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
        // 上传成功或者失败的提示
        String msg = "";

        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
            // 上传成功，给出页面提示
            msg = "上传成功！";
            String tmp_path = "images/"+file.getOriginalFilename();
            String url = "http://localhost:8082/"+file.getOriginalFilename();
            Map map = new HashMap();
            map.put("tmp_path",tmp_path);
            map.put("url",url);
            return ResultFactory.buildSuccessResult(map,msg);
        }else {
            msg = "上传失败！";
            return ResultFactory.buildFailResult(msg);
        }

    }

}
