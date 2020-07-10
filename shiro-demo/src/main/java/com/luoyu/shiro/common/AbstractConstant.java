package com.luoyu.shiro.common;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 静态常量类
 */
public abstract class AbstractConstant {

    /**************************           Shiro权限常量             *******************************/
    /**
     * request请求头属性
     */
    public static final String REQUEST_AUTH_HEADER="token";
    /**
     * tokenUserId 用户名
     */
    public static final String TOKEN_USER_ID = "userId";
    /**
     * tokenPassword 密码
     */
    public static final String TOKEN_PASSWORD = "password";
    /**
     * tokenCurrentTimeMillis 时间戳
     */
    public static final String TOKEN_CURRENT_TIME_MILLIS = "currentTimeMillis";
    /**
     * refreshTokenCheckKey key 前缀
     */
    public static final String REFRESH_TOKEN_CHECK_KEY = "refreshCheckToken:";
    /**
     * shiroRolePermissionKey key 前缀
     */
    public static final String SHIRO_ROLE_PERMISSION_KEY = "shiroRolePermission:";
    /**
     * tokenExpirationTime 过期时间(单位：毫秒)
     */
    public static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000L;
    /**
     * shiroRolePermissionExpirationTime 过期时间(单位：秒)
     */
    public static final int SHIRO_ROLE_PERMISSION_EXPIRATION_TIME = 24 * 60 * 60;
    /**
     * refreshTokenCheckExpirationTime 过期时间(单位：秒)
     */
    public static final int REFRESH_TOKEN_CHECK_EXPIRATION_TIME = 24 * 60 * 60;
    /**
     * refreshTokenCheckTime 检查时间(单位：毫秒)
     */
    public static final int REFRESH_TOKEN_CHECK_TIME = 30 * 60 * 1000;
    /**
     * releaseLockTime 释放锁延迟时间(单位：秒)
     */
    public static final int RELEASE_LOCK_TIME = 20;
    /**
     * refreshToken key 前缀(Token的时间戳)
     */
    public static final String REFRESH_TOKEN = "refreshToken:";
    /**
     * userLogInCodeExpirationTime 过期时间(单位：秒)
     */
    public static final int USER_LOG_IN_CODE_EXPIRATION_TIME = 300;
    /**
     * 前缀(用户手机验证码登录)
     */
    public static final String USER_PHONE_LOG_IN_CODE = "user:phone:login:code:";
    /**
     * 前缀(用户邮箱验证码登录)
     */
    public static final String USER_EMAIL_LOG_IN_CODE = "user:email:login:code:";

    /**************************           权限常量             *******************************/

    /**
     * userRegisterTypePhone 用户注册类型（手机）
     */
    public static final String USER_REGISTER_TYPE_PHONE = "phone";
    /**
     * userRegisterTypeEmail 用户注册类型（邮箱）
     */
    public static final String USER_REGISTER_TYPE_EMAIL = "email";
    /**
     * userCreateTime 用户注册时间
     */
    public static final String USER_CREATE_TIME = "CREATE_TIME";

    /**************************           公共实体常量             *******************************/
    /**
     * createTime 创建时间
     */
    public static final String CREATE_TIME = "CREATE_TIME";
    /**
     * createID 创建人ID
     */
    public static final String CREATE_ID = "CREATE_ID";
    /**
     * updaterTime 更新时间
     */
    public static final String UPDATE_TIME = "UPDATE_TIME";
    /**
     * updaterID 更新人ID
     */
    public static final String UPDATER_ID = "UPDATER_ID";


    /**************************           article实体常量             *******************************/
    /**
     * articlePrimaryCode 文章一级标签码
     */
    public static final String ARTICLE_PRIMARY_CODE = "PRIMARY_CODE";
    /**
     * articleSecondaryCode 文章二级标签码
     */
    public static final String ARTICLE_SECONDARY_CODE = "SECONDARY_CODE";
    /**
     * articleTitle 文章标题
     */
    public static final String ARTICLE_TITLE = "TITLE";
    /**
     * articleId 文章ID
     */
    public static final String ARTICLE_ID = "ARTICLE_ID";
    /**
     * articleStatus 文章状态
     */
    public static final String ARTICLE_STATUS = "STATUS";
    /**
     * articleLabelId 文章标签ID
     */
    public static final String ARTICLE_LABEL_ID = "LABEL_ID";
    /**
     * 文字信息缓存 key 前缀
     */
    public static final String ARTICLE_INFO_CACHE_KEY = "article:info:cache";


    /************************           上传下载文件常量             *******************************/
    /**
     * downloadFileType 文件下载类型
     */
    public static final String DOWNLOAD_FILE_TYPE_IMAGE = "image/jpeg";

}











