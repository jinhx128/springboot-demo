package com.luoyu.minio.controller;

import com.luoyu.minio.util.MinioUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * minio 前端控制器
 * </p>
 *
 * @author luoyu
 * @since 2018-11-30
 */
@Slf4j
@RestController
public class MinioController {

    @Autowired
    private MinioUtils minioUtils;

    /**
     * 上传文件
     */
    @PostMapping("/minio/upload")
    public void uploadByMinio(MultipartFile file, String bucketName) throws Exception {
        if (file.getSize() < 1){
            log.warn("文件大小为：0");
            return;
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        String patchName = this.getPath() + suffix;
        minioUtils.upload(inputStream, patchName, bucketName, contentType);
    }

    /**
     * 下载文件
     */
    @PostMapping("/minio/download")
    public void downloadByMinio(HttpServletResponse response, String bucketName, String fileName) throws Exception {
        minioUtils.download(response, bucketName, fileName);
    }

    /**
     * 文件路径
     * @return 返回上传路径
     */
    private String getPath() {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()) + "/" + uuid;
    }

}
