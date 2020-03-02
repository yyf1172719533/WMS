package com.timain.web.sys.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.timain.web.sys.common.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author yyf
 * @version 1.0
 * @date 2020/3/1 22:14
 */
public class UploadImgUtils {

    public static String UPLOAD_IMG_PATH = "D:/IdeaProjects/WMS/src/main/resources/static/resources/";

    /**
     * 读取配置文件
     */
    static {
        InputStream stream = UADFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
            String property = properties.getProperty("uploadImgPath");
            if (null!=property) {
                UPLOAD_IMG_PATH = property;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取新的文件名
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {
        String stuff = oldName.substring(oldName.lastIndexOf("."), oldName.length());
        return IdUtil.simpleUUID().toUpperCase() + stuff;
    }



    /**
     * 图片下载工具
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //构造文件对象
        File file = new File(UPLOAD_IMG_PATH, path);
        if (file.exists()) {
            //将下载的文件封装为byte[]
            byte[] bytes = null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //创建封装响应头信息的对象
            HttpHeaders headers = new HttpHeaders();
            //封装响应内容类型
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //创建ResponseEntity对象
            ResponseEntity<Object> entity = new ResponseEntity<Object>(bytes, headers, HttpStatus.CREATED);
            return entity;
        }
        return null;
    }

    /**
     * 更换文件名
     * @param imgPath
     * @return
     */
    public static String changeFileName(String imgPath) {
        File file = new File(UPLOAD_IMG_PATH, imgPath);
        String fileName = imgPath.replace("_temp", "");
        if (file.exists()) {
            file.renameTo(new File(UPLOAD_IMG_PATH, fileName));
        }
        return fileName;
    }

    /**
     * 删除文件
     * @param oldPath
     */
    public static void removeByPath(String oldPath) {
        File file = new File(UPLOAD_IMG_PATH, oldPath);
        if (!oldPath.equals(Constants.DEFAULT_GOODS_IMG)) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
