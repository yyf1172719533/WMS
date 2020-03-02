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
 * 文件上传下载工具类
 * @author yyf
 * @version 1.0
 * @date 2020/2/29 11:23
 */
public class UADFileUtils {

    public static String UPLOAD_PATH = "E:/upload/";

    /**
     * 读取配置文件
     */
    static {
        InputStream stream = UADFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
            String property = properties.getProperty("filePath");
            if (null!=property) {
                UPLOAD_PATH = property;
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
        File file = new File(UPLOAD_PATH, path);
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
     * @param goodsImg
     * @return
     */
    public static String changeFileName(String goodsImg) {
        File file = new File(UPLOAD_PATH, goodsImg);
        String fileName = goodsImg.replace("_temp", "");
        if (file.exists()) {
            file.renameTo(new File(UPLOAD_PATH, fileName));
        }
        return fileName;
    }

    /**
     * 删除文件
     * @param oldPath
     */
    public static void removeByPath(String oldPath) {
        File file = new File(UPLOAD_PATH, oldPath);
        if (!oldPath.equals(Constants.DEFAULT_GOODS_IMG)) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
