package com.luoyu.shiro.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.luoyu.shiro.common.AbstractConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description JWT具类
 */
@Slf4j
public class JwtUtil {

    /**
     * @author jinhaoxun
     * @description 创建token方法
     * @param userId 用户id
     * @param secret 用密码当密钥
     * @param currentTimeMillis 时间戳
     * @return String 加密的token
     */
    public static String createToken(String userId, String secret, String currentTimeMillis) {
        try {
            Date date = new Date(System.currentTimeMillis() + AbstractConstant.TOKEN_EXPIRATION_TIME);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    //用戶名
                    .withClaim(AbstractConstant.TOKEN_USER_ID, userId)
                    //时间戳
                    .withClaim(AbstractConstant.TOKEN_CURRENT_TIME_MILLIS, currentTimeMillis)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * @author jinhaoxun
     * @description 校验token是否正确方法
     * @param token 加密的token
     * @param userId 用户id
     * @param secret 密钥
     * @return Boolean token是否正常
     */
    public static Boolean verify(String token, String userId, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            //在 token 中附带了 userId 信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(AbstractConstant.TOKEN_USER_ID, userId)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @author jinhaoxun
     * @description 获得token中的信息，无需secret解密也能获得方法
     * @param token 加密的token
     * @param claim 要获取的信息字段
     * @return String 要获取的信息字段
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
