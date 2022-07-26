package com.example.subsecurity.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaptchaConfig {

    @Bean
    public Producer captcha() {
        //配置配置图形验证码的基本参数
        Properties properties = new Properties();
        //图片宽度
        properties.setProperty("kaptcha.image.width", "100");
        //图片高度
        properties.setProperty("kaptcha.image.height", "50");
        //字符集
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        //字符长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");

        Config config = new Config(properties);
        //使用默认的验证码实现 也可以自定义
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
