package com.jinhaoxun.filedemo.util.sftputil;


import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * @Description: sftp上传下载工具类
 * @Author: jinhaoxun
 * @Date: 2020/1/16 16:13
 * @Version: 1.0.0
 */
public class SftpUtil {

    /**
     * @Author: jinhaoxun
     * @Description: 下载文件
     * @param userName 用户名
     * @param password 密码
     * @param host ip
     * @param port 端口
     * @param basePath 根路径
     * @param filePath 文件路径（加上根路径）
     * @param filename 文件名
     * @param privateKey 秘钥
     * @param input 文件流
     * @Date: 2020/1/16 21:23
     * @Return: void
     * @Throws: Exception
     */
    public static void uploadFile(String userName, String password, String host, int port, String basePath,
                                     String filePath, String filename, String privateKey, InputStream input) throws Exception {

        Session session = null;
        ChannelSftp sftp = null;
        // 连接sftp服务器
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }

            session = jsch.getSession(userName, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        // 将输入流的数据上传到sftp作为文件
        try {
            sftp.cd(basePath);
            sftp.cd(filePath);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String [] dirs=filePath.split("/");
            String tempPath=basePath;
            for(String dir:dirs){
                if(null== dir || "".equals(dir)){
                    continue;
                }
                tempPath+="/"+dir;
                try{
                    sftp.cd(tempPath);
                }catch(SftpException ex){
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
        //上传文件
        sftp.put(input, filename);
        //关闭连接 server
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        //关闭连接 server
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * @Author: jinhaoxun
     * @Description: 下载文件
     * @param userName 用户名
     * @param password 密码
     * @param host ip
     * @param port 端口
     * @param privateKey 秘钥
     * @param directory 文件路径
     * @param downloadFile 文件名
     * @param saveFile 存在本地的路径
     * @Date: 2020/1/16 21:22
     * @Return: void
     * @Throws: Exception
     */
    public static void downloadFile(String userName, String password, String host, int port, String privateKey, String directory,
                                String downloadFile, String saveFile) throws Exception{
        Session session = null;
        ChannelSftp sftp = null;
        // 连接sftp服务器
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }

            session = jsch.getSession(userName, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        File file = new File(saveFile);
        sftp.get(downloadFile, new FileOutputStream(file));
    }

    /**
     * @Author: jinhaoxun
     * @Description: 下载文件
     * @param userName 用户名
     * @param password 密码
     * @param host ip
     * @param port 端口
     * @param privateKey 秘钥
     * @param directory 文件路径
     * @param downloadFile 文件名
     * @Date: 2020/1/16 21:21
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] downloadFile(String userName, String password, String host, int port, String privateKey,
                                  String directory, String downloadFile) throws Exception{
        Session session = null;
        ChannelSftp sftp = null;
        // 连接sftp服务器
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }

            session = jsch.getSession(userName, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        byte[] fileData = IOUtils.toByteArray(is);
        return fileData;
    }

    /**
     * @Author: jinhaoxun
     * @Description: 删除文件
     * @param userName 用户名
     * @param password 密码
     * @param host ip
     * @param port 端口
     * @param privateKey 秘钥
     * @param directory 文件路径
     * @param deleteFile 文件名
     * @Date: 2020/1/16 21:24
     * @Return: void
     * @Throws: Exception
     */
    public static void deleteFile(String userName, String password, String host, int port, String privateKey,
                              String directory, String deleteFile) throws Exception{
        Session session = null;
        ChannelSftp sftp = null;
        // 连接sftp服务器
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }

            session = jsch.getSession(userName, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * @Author: jinhaoxun
     * @Description: 列出目录下的文件
     * @param userName 用户名
     * @param password 密码
     * @param host ip
     * @param port 端口
     * @param privateKey 秘钥
     * @param directory 要列出的目录
     * @Date: 2020/1/16 21:25
     * @Return: java.util.Vector<?>
     * @Throws: Exception
     */
    public static Vector<?> getFileList(String userName, String password, String host, int port, String privateKey,
                                      String directory) throws Exception {
        Session session = null;
        ChannelSftp sftp = null;
        // 连接sftp服务器
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                // 设置私钥
                jsch.addIdentity(privateKey);
            }

            session = jsch.getSession(userName, host, port);

            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return sftp.ls(directory);
    }

}
