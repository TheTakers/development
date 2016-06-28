package com.sophia.web.constant;

/**
 * Created by Kim on 2015/9/29.
 */
public class Constant {

    public static final String SUCCESS_MESSAGE = "处理成功";
    public static final String FAILURE_MESSAGE = "处理失败";

    public static final String LOGIN_SUCCESS = "登陆成功";
    public static final String LOGIN_FAILD = "登陆失败";

    public static final String SYSTEM_ERROR_MESSAGE = "服务器异常";
    public static final String RUNTIME_ERROR_MESSAGE = "服务器运行异常";
    
    public static final String VERIFICATION_CODE_ERROR = "验证码错误,请重新输入！";
    public static final String KEY_FAILURE_MESSAGE = "验证码错误,请重新获取验证码！";
    public static final String ACCOUNT_ERROR = "账号信息异常！";

    public static final String RESPONSE_OK = "ok";
    public static final String RESPONSE_ERROR = "error";

    public static final String KEY_OF_CODE = "code";
    public static final String KEY_OF_MESSAGE = "message";
    public static final String KEY_OF_RESULT = "result";

    public static final Integer STATUS_CODE_SUCCESS = 0;
    public static final Integer STATUS_CODE_FAILURE = 1;
    
    //生成邀请URL的签名密钥
    public static final String SECRECT_KEY = "eb3eb0c7a70158961a586927434d0213";

    //短信发送成功code值
    public static final String SMS_SEND_SUCCESS = "0";

}
