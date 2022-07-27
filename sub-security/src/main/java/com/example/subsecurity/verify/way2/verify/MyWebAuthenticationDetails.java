package com.example.subsecurity.verify.way2.verify;

import cn.hutool.core.util.StrUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/***
 * 自定义MyAuthenticationDetails封装了验证码图片是否通过的属性信息
 */
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final String CAPTCHA = "captcha";

    private boolean imageCodeIsRight;

    //补充用户提交的验证码和Session保存的验证码
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String inputImageCode = request.getParameter(CAPTCHA);
        HttpSession httpSession = request.getSession();
        final String sessionImageCode = (String) httpSession.getAttribute(CAPTCHA);
        if (StrUtil.isNotBlank(sessionImageCode)) {
            //清除验证码 不管失败 还是成功 所有的客户端在登录失败时刷新验证码
            httpSession.removeAttribute(CAPTCHA);
            //当验证码正确时设置状态
            if (StrUtil.isNotBlank(inputImageCode) && StrUtil.equals(inputImageCode, sessionImageCode)) {
                this.imageCodeIsRight = true;
            }
        }
    }

    public boolean isImageCodeIsRight() {
        return this.imageCodeIsRight;
    }
}
