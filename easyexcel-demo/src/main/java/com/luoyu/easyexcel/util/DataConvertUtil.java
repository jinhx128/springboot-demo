package com.luoyu.easyexcel.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/4/14 下午5:26
 * @Version: 1.0.0
 */
public class DataConvertUtil {

    /**
     * @Author: jinhaoxun
     * @Description: 将inputStream转byte[]
     * @param inputStream 输入流
     * @Date: 2020/1/16 21:43
     * @Return: byte[]
     * @Throws: Exception
     */
    public static byte[] inputStreamTobyte2(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inputStream.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * @Author: jinhaoxun
     * @Description: 将byte[]转inputStream
     * @param bytes byte数组
     * @Date: 2020/1/16 21:43
     * @Return: InputStream
     * @Throws: Exception
     */
    public static InputStream byte2ToInputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

}
