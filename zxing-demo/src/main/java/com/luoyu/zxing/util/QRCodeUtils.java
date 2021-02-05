package com.luoyu.zxing.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.luoyu.zxing.config.QrCodeConfig;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

@Slf4j
public class QRCodeUtils {
    
    /**
     * 生成二维码图片
     *
     * @param content      二维码内容
     * @param imgPath      中间log地址
     * @param needCompress 是否压缩
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        try {
            Hashtable hints = new Hashtable();

            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, QrCodeConfig.charset);
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QrCodeConfig.width,
                    QrCodeConfig.height, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            if (imgPath == null || "".equals(imgPath)) {
                return image;
            }
            // 插入图片
            QRCodeUtils.insertImage(image, imgPath, needCompress);
            return image;
        }catch (Exception e){
            log.error("生成二维码失败：" + e);
            return null;
        }
    }

    /**
     * 插入LOGO
     *
     * @param imgPath      二维码图片
     * @param imgPath      LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            log.error(imgPath + "，该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩logo
        if (needCompress) {
            if (width > QrCodeConfig.logoWidth) {
                width = QrCodeConfig.logoWidth;
            }
            if (height > QrCodeConfig.logoHeight) {
                height = QrCodeConfig.logoHeight;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QrCodeConfig.width - width) / 2;
        int y = (QrCodeConfig.height - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 创建文件夹， mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     *
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成带logo二维码
     *
     * @param content      二维码的内容
     * @param imgPath      中间log地址
     * @param output
     * @param needCompress 是否压缩
     * @throws Exception
     */
    public static void encode(String content, String imgPath, ServletOutputStream output, boolean needCompress) {
        try {
            BufferedImage image = QRCodeUtils.createImage(content, imgPath, needCompress);
            ImageIO.write(image, QrCodeConfig.picType, output);
        } catch (Exception e) {
            log.error("生成二维码失败：" + e);
        }
    }

    /**
     * 生成不带logo二维码
     *
     * @param content 二维码的内容
     * @param output
     * @throws Exception
     */
    public static void encode(String content, ServletOutputStream output) {
        QRCodeUtils.encode(content, null, output, false);
    }

}