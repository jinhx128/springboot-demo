package com.luoyu.zxing.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.Serializable;

/**
 * QrCodeConfig
 *
 * @author luoyu
 * @date 2018/10/19 18:47
 * @description 二维码配置类
 */
@Data
@Configuration
public class QrCodeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${qr.code.charset}")
    private String configCharset;

    @Value("${qr.code.width}")
    private Integer configWidth;

    @Value("${qr.code.height}")
    private Integer configHeight;

    @Value("${qr.code.logoWidth}")
    private Integer configLogoWidth;

    @Value("${qr.code.logoHeight}")
    private Integer configLogoHeight;

    @Value("${qr.code.picType}")
    private String configPicType;

    /**
     * 编码
     */
    public static String charset;

    /**
     * 生成二维码的宽
     */
    public static Integer width;

    /**
     * 生成二维码的高
     */
    public static Integer height;

    /**
     * logo的宽
     */
    public static Integer logoWidth;

    /**
     * logo的高
     */
    public static Integer logoHeight;

    /**
     * 生成二维码图片的格式 png, jpg
     */
    public static String picType;

    @PostConstruct
    public void setCharset() {
        charset = this.configCharset;
    }

    @PostConstruct
    public void setWidth() {
        width = this.configWidth;
    }

    @PostConstruct
    public void setHeight() {
        height = this.configHeight;
    }

    @PostConstruct
    public void setLogoWidth() {
        logoWidth = this.configLogoWidth;
    }

    @PostConstruct
    public void setLogoHeight() {
        logoHeight = this.configLogoHeight;
    }

    @PostConstruct
    public void setPicType() {
        picType = this.configPicType;
    }

}
