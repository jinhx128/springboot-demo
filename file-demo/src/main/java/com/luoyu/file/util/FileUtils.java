package com.luoyu.file.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 获取文件的属性信息：
 * boolean exists():判断文件或目录是否存在
 * String getName():返回文件或目录的名称
 * String getParent():返回父路径的路径名字符串
 * String getAbsolutePath():返回绝对路径的路径名字符串
 * String getPath():返回抽象路径的路径名字符串
 * boolean isAbsolute():判断当前路径是否为绝对路径
 * boolean isDirectory():判断当前文件是否为目录
 * boolean isFile():判断当前文件是否为一个标准文件
 * boolean isHidden():判断当前文件是否是一个隐藏文件
 * long lastModified():返回当前文件最后一次被修改的时间
 * long length():返回当前文件的长度
 * boolean canRead():判断是否可读
 * boolean canWrite():判断是否可写
 *
 * 实现文件和文件夹的操作：
 * createNewFile()：创建文件
 * delete()： 删除文件
 * renameTo()：移动文件
 * mkdir()和mkdirs()：创建文件夹
 * delete()： 删除文件夹
 *
 * 字节流，主要包含InputStream和OutputStream两个基础类。
 * FileInputStream/FileOutputStream(读写文件)
 * BufferedInputStream/BufferedOutputStream(读写缓冲流)
 * ByteArrayInputStream/ByteArrayOutputStream(按字节数组读写内存中临时数据)
 * DataInputStream/DataOutputStream(读写基本类型和String)
 * ObjectInputStream/ObjectOutputStream(读写对象)
 * PipedInputStream/PipedOutputStream(主要在线程中使用)
 * PrintStream(打印流，可以一次写一行)
 *
 * 字符流，主要包含Reader和Writer两个基础类。
 * FileReader/FileWriter(只能采用系统默认编码方式读写)
 * InputStreamReader/OutputStreamWriter(转换流，采用指定编码方式读写)
 * BufferedReader/BufferedWriter(缓冲流，借助readLine()和newLine()可以一次读写一行)
 * CharArrayReader/CharArrayWriter(按字符数组读写)
 * PipedReader/PipedWriter(主要在线程中使用)
 * PrintWriter(打印流，可以一次写一行)
 */
@Slf4j
public class FileUtils {

    public static void main(String[] args) {
        log.info("start");
        method2("/Users/luoyu/Desktop/test.txt", "追加到文件的末尾");
        log.info("end");
    }

    /**
     * 第一种，追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     */
    public static void method1(String file, String conent) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, false)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 第二种，追加文件：使用FileWriter
     */
    public static void method2(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加文件：使用RandomAccessFile
     */
    public static void method3(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 追加文件
     */
    public void wirte(String path, String content) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            //判断目录是否存在，不存在创建
            dirFile.mkdir();
        }

        File file = new File(path);
        if (!file.exists()) {
            //判断文件是否存在，不存在创建
            file.createNewFile();
        }

        //new FileWriter(path + "config.log", true)  设置true 在不覆盖以前文件的基础上继续写
        BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/luoyu/Desktop", true));
        try {
            //写入文件
            writer.write(content + "\r\n");
            //清空缓冲区数据
            writer.flush();
        } catch (Exception e) {
            log.error("数据写入失败，msg：" + e);
        }finally {
            //关闭读写流
            writer.close();
        }
    }

}
