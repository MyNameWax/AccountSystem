package cn.rzpt;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.rzpt.util.EmailUtils;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

@SpringBootTest
class AccountmanagementBackendApplicationTests {
    @Resource
    private JavaMailSender javaMailSender;
    @Test
    void contextLoads() throws Exception {
        EmailUtils emailUtils = new EmailUtils();
        emailUtils.sendMail("waxjava04@163.com","Account System","Hi Wax,Delete is " + (true ? "success": "error") + "total time:" + (15 - 10) + "ms");


    }

}
