package com.timain.web.sys.controller;

import cn.hutool.core.date.DateUtil;
import com.timain.web.sys.utils.UADFileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传下载
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 11:17
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /**
     * 文件上传
     * @param mf
     * @return
     */
    @RequestMapping("uploadFile")
    public Map<String, Object> uploadFile(MultipartFile mf) throws IOException {
        //获取文件名
        String oldName = mf.getOriginalFilename();
        //创建新的文件名
        String newName = UADFileUtils.createNewFileName(oldName);
        //获取当前日期的字符串
        String dirName = DateUtil.format(new Date(), "yyyy-MM-dd");
        //构造文件夹
        File dirFile = new File(UADFileUtils.UPLOAD_PATH, dirName);
        //判断当前文件夹是否存在
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //构造文件对象
        File file = new File(dirFile, newName + "_temp");
        //将mf中的图片存入file
        mf.transferTo(file);
        Map<String, Object> map = new HashMap<>();
        map.put("path", dirName + "/" + newName + "_temp");
        return map;
    }

    /**
     * 图片下载
     * @param path
     * @return
     */
    @RequestMapping("showImgByPath")
    public ResponseEntity<Object> showImgByPath(String path) {
        return UADFileUtils.createResponseEntity(path);
    }
}
