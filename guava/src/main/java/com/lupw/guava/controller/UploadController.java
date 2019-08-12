package com.lupw.guava.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lupengwei 2019/8/12
 */
@Slf4j
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Map<String, Object> uploadInitBalanceData(@RequestParam("file") MultipartFile files) {
        String fileName = UUID.randomUUID().toString().replace("-", "") + ".png";
        return upload(files, "/user/lupengwei/files/upload/", fileName);
    }


    private Map<String, Object> upload(MultipartFile file, String savePath, String fileName) {
        Map<String, Object> resultMap = new HashMap<>();

        if (file == null) {
            resultMap.put("code", -1);
            resultMap.put("msg", "请上传一个文件");
            return resultMap;
        }

        // 创建保存文件的路径，不存在就创建
        log.info("文件路径 : " + savePath);
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                resultMap.put("code", -1);
                resultMap.put("msg", "创建文件路径失败");
                return resultMap;
            }
        }

        File saveFile = new File(savePath + fileName);
        if (!saveFile.exists()) {
            try {
                file.transferTo(saveFile);
                if (!saveFile.createNewFile()) {
                    resultMap.put("code", -1);
                    resultMap.put("msg", "上传图片失败");
                }
            } catch (IOException e) {
                log.error("error message", e);
                resultMap.put("code", -1);
                resultMap.put("msg", "上传图片失败");
            }
            return resultMap;
        }

        resultMap.put("code", 0);
        resultMap.put("msg", "上传图片成功");
        resultMap.put("size", file.getSize());
        return resultMap;
    }
}
