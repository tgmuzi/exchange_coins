package com.zeus.googleConfig;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 谷歌 Captcha验证码配置类
 */
@Configuration
public class CaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha() {
        // 验证码生成器
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        // 配置
        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty("kaptcha.border", "no");
        // 设置边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 验证码背景颜色渐进
        properties.setProperty("kaptcha.background.clear.from", "black");// 验证码背景颜色渐进
        properties.setProperty("kaptcha.background.clear.to", "black");// 验证码背景颜色渐进
        // 验证码
        properties.setProperty("kaptcha.session.code", "code");
        // 验证码文本字符颜色，默认为黑色
        properties.setProperty("kaptcha.textproducer.font.color", "255,255,255");
        // 设置字体样式
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.noise.color", "255,69,0");
        // 字体大小，默认为40
        properties.setProperty("kaptcha.textproducer.font.size", "40");
        // 字符长度，默认为5
        properties.setProperty("kaptcha.textproducer.char.length", "5");
        // 字符间距，默认为2
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        // 验证码图片宽度，默认为200
        properties.setProperty("kaptcha.iamge.width", "100");
        // 验证码图片高度，默认为40
        properties.setProperty("kaptcha.iamge.height", "40");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
