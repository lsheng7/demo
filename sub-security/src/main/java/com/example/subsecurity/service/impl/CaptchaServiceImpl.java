package com.example.subsecurity.service.impl;

import com.example.subsecurity.service.CaptchaService;
import com.google.code.kaptcha.Producer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Resource
    private Producer captchaProducer;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Override
    public void captcha() {
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        request.getSession().setAttribute("captcha", capText);
        final BufferedImage image = captchaProducer.createImage(capText);
        try (final ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "jpeg", outputStream);
            outputStream.flush();
        } catch (IOException exception) {
            log.error("error:{}", exception.getMessage());
        }
    }
}
