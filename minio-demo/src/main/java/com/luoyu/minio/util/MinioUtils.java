package com.luoyu.minio.util;

import com.luoyu.minio.enitiy.MinioItem;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.luoyu.minio.config.MinioProperties;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author luoyu
 */
@Slf4j
@Component
public class MinioUtils {

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private MinioClient minioClient;

    /**
     * 检查存储桶是否存在
     * @param bucketName 存储桶名称
     * @return boolean
     */
    public boolean bucketExists(String bucketName){
        try {
            return minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e) {
            log.error("检查存储桶是否存在失败：" + e);
            return false;
        }
    }

    /**
     * 创建存储桶
     * @param bucketName 存储桶名称
     * @return boolean
     */
    public boolean createBucket(String bucketName) {
        try {
            if (!this.bucketExists(bucketName)) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
            }
            return true;
        } catch (Exception e) {
            log.error("创建存储桶失败：" + e);
            return false;
        }
    }

    /**
     * 根据存储桶名称获取信息
     * @param bucketName 存储桶名称
     * @return
     */
    public Optional<Bucket> getBucket(String bucketName) {
        try {
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (Exception e) {
            log.error("根据存储桶名称获取信息失败：" + e);
            return null;
        }
    }

    /**
     * 根据存储桶删除信息
     * @param bucketName 存储桶名称
     */
    public void removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (Exception e) {
            log.error("根据存储桶删除信息失败：" + e);
        }
    }

    /**
     * 根据文件前缀查询文件
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    public List<MinioItem> getMinioItemsByPrefix(String bucketName, String prefix, Boolean recursive) {
        try {
            List<MinioItem> objectList = new ArrayList<>();
            Iterable<Result<Item>> objectsIterator = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(recursive)
                            .build())
                    ;
            for (Result<Item> result : objectsIterator) {
                objectList.add(new MinioItem(result.get()));
            }
            return objectList;
        } catch (Exception e) {
            log.error("根据文件前缀查询文件失败：" + e);
            return null;
        }
    }

    /**
     * 获取文件外链地址
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @param expiry 过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return String
     */
    public String getPresignedObjectUrl(String bucketName, String objectName, Integer expiry) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expiry)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取文件外链地址失败：" + e);
            return null;
        }
    }

    /**
     * 获取文件
     * @param bucketName 存储桶名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取文件失败：" + e);
            return null;
        }
    }

    /**
     * 获取全部存储桶
     * @return List<Bucket>
     */
    public List<Bucket> getBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("获取全部存储桶失败：" + e);
            return null;
        }
    }

    /**
     * 上传文件
     * @param inputStream inputStream
     * @param objectName objectName
     * @param bucketName bucketName
     * @param contentType contentType
     */
    public void upload(InputStream inputStream, String objectName, String bucketName, String contentType) {
        try {
            // 检查存储桶是否已经存在，不存在则创建
            this.createBucket(bucketName);
            // 使用putObject上传一个文件到存储桶中。
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build()
            );
            //关闭
            inputStream.close();
        } catch (Exception e) {
            log.error("上传文件失败：" + e);
        }
    }

    /**
     * 下载文件
     *
     * @param response response
     * @param objectName objectName
     */
    public void download(HttpServletResponse response, String bucketName, String objectName) {
        InputStream inputStream = null;
        try {
            ObjectStat stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            inputStream = this.getObject(bucketName, objectName);
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(objectName, String.valueOf(StandardCharsets.UTF_8)));
            IOUtils.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败：" + e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取文件url
     * @param objectName objectName
     * @return url
     */
    public String getObjectUrl(String bucketName, String objectName) {
        try {
            return minioClient.getObjectUrl(bucketName, objectName);
        } catch (Exception e) {
            log.error("获取文件url失败：" + e);
            return null;
        }
    }

    /**
     * 获取所有文件
     * @param bucketName bucketName
     */
    public List<MinioItem> list(String bucketName) {
        try {
            List<MinioItem> list = new ArrayList<MinioItem>();
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            for (Result<Item> result : results) {
                Item item = result.get();
                MinioItem minioItem = new MinioItem(item);
                minioItem.setUrl(this.getObjectUrl(bucketName, item.objectName()));
                list.add(minioItem);
            }
            return list;
        } catch (Exception e) {
            log.error("获取所有文件失败：" + e);
            return null;
        }
    }

    /**
     * 删除文件
     * @param bucketName 存储桶名称
     * @param objectName 文件名
     */
    public void deleteObjectName(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            log.error("删除文件失败：" + e);
        }
    }

    /**
     * 批量删除文件
     * @param bucketName bucketName
     * @param objectNames 文件名列表
     */
    public void deleteObjectNames(String bucketName, List<String> objectNames) {
        objectNames.forEach(objectNamesItem -> {
            this.deleteObjectName(bucketName, objectNamesItem);
        });
    }

    /**
     * 创建上传文件对象的外链
     * @param bucketName 存储桶名称
     * @param objectName 欲上传文件对象的名称
     * @param expiry 过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return uploadUrl
     */
    public String createUploadUrl(String bucketName, String objectName, Integer expiry){
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(expiry)
                            .build()
            );
        } catch (Exception e) {
            log.error("创建上传文件对象的外链失败：" + e);
            return null;
        }
    }

    /**
     * 批量创建分片上传外链
     * @param bucketName 存储桶名称
     * @param objectMD5 欲上传分片文件主文件的MD5
     * @param chunkCount 分片数量
     * @param expiry 过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return uploadChunkUrls
     */
    public List<String> createUploadChunkUrlList(String bucketName, String objectMD5, Integer chunkCount, Integer expiry){
        objectMD5 += "/";
        if(null == chunkCount || 0 == chunkCount){
            return null;
        }
        List<String> urlList = new ArrayList<>(chunkCount);
        for (int i = 1; i <= chunkCount; i++){
            String objectName = objectMD5 + i + ".chunk";
            urlList.add(this.createUploadUrl(bucketName, objectName, expiry));
        }
        return urlList;
    }

    /**
     * 创建指定序号的分片文件上传外链
     * @param bucketName 存储桶名称
     * @param objectMD5 欲上传分片文件主文件的MD5
     * @param partNumber 分片序号
     * @param expiry 过期时间(秒) 最大为7天 超过7天则默认最大值
     * @return uploadChunkUrl
     */
    public String createUploadChunkUrl(String bucketName, String objectMD5, Integer partNumber, Integer expiry){
        objectMD5 += "/" + partNumber + ".chunk";
        return this.createUploadUrl(bucketName, objectMD5, expiry);
    }

    /**
     * 获取分片文件名称列表
     * @param bucketName 存储桶名称
     * @param prefix 对象名称前缀（ObjectMd5）
     * @param sort 是否排序(升序)
     * @return objectNames
     */
    public List<String> listObjectNames(String bucketName, String prefix, Boolean sort){
        try {
            ListObjectsArgs listObjectsArgs;
            if (null == prefix) {
                listObjectsArgs = ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .recursive(true)
                        .build();
            } else {
                listObjectsArgs = ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .prefix(prefix)
                        .recursive(true)
                        .build();
            }
            Iterable<Result<Item>> chunks = minioClient.listObjects(listObjectsArgs);
            List<String> chunkPaths = new ArrayList<>();
            for (Result<Item> item : chunks) {
                chunkPaths.add(item.get().objectName());
            }
            if (sort) {
                return chunkPaths.stream().distinct().collect(Collectors.toList());
            }
            return chunkPaths;
        } catch (Exception e) {
            log.error("获取分片文件名称列表失败：" + e);
            return null;
        }
    }

    /**
     * 获取分片名称地址，HashMap：key=分片序号，value=分片文件地址
     * @param bucketName 存储桶名称
     * @param ObjectMd5 对象Md5
     * @return objectChunkNameMap
     */
    public Map<Integer, String> mapChunkObjectNames(String bucketName, String ObjectMd5, Boolean sort){
        List<String> chunkPaths = this.listObjectNames(bucketName,ObjectMd5, sort);
        if (CollectionUtils.isEmpty(chunkPaths)){
            return null;
        }
        Map<Integer, String> chunkMap = new HashMap<>(chunkPaths.size());
        for (String chunkName : chunkPaths) {
            Integer partNumber = Integer.parseInt(chunkName.substring(chunkName.indexOf("/") + 1, chunkName.lastIndexOf(".")));
            chunkMap.put(partNumber,chunkName);
        }
        return chunkMap;
    }

    /**
     * 合并分片文件成对象文件
     * @param chunkBucKetName 分片文件所在存储桶名称
     * @param composeBucketName 合并后的对象文件存储的存储桶名称
     * @param chunkNames 分片文件名称集合
     * @param objectName 合并后的对象文件名称
     * @return true/false
     */
    public boolean composeObject(String chunkBucKetName, String composeBucketName, List<String> chunkNames, String objectName){
        try {
            List<ComposeSource> sourceObjectList = new ArrayList<>(chunkNames.size());
            for (String chunk : chunkNames) {
                sourceObjectList.add(
                        ComposeSource.builder()
                                .bucket(chunkBucKetName)
                                .object(chunk)
                                .build()
                );
            }
            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(composeBucketName)
                            .object(objectName)
                            .sources(sourceObjectList)
                            .build()
            );
            return true;
        } catch (Exception e) {
            log.error("合并分片文件成对象文件失败：" + e);
            return false;
        }
    }

}
