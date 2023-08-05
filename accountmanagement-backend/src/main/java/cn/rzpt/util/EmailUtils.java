package cn.rzpt.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Properties;

/**
 * 邮件工具类
 * @author: itdragons
 * @date: 2020/9/25
 */
@Component
public class EmailUtils {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.qq.com");
        sender.setPort(465);
        sender.setUsername("1022662230@qq.com");
        sender.setPassword("gynjoxvavdtmbfbj");
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", "10000");
        p.setProperty("mail.smtp.auth", "false");
        p.setProperty("mail.smtp.ssl.enable", "true");
        // java mail发邮件是附件名过长默认会被截断，附件名显示【tcmime.2475.2828.3041.bin】，主动设为false可正常显示附件名
        p.setProperty("mail.mime.splitlongparameters", "false");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     * @param to        收件人
     * @param subject   主题
     * @param content   邮件正文
     * @return true：成功   false：失败
     */
    public boolean sendMail(String to, String subject, String content) throws Exception {
        JavaMailSenderImpl mailSender = createMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8编码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(mailSender.getUsername());
        // 收件人
        messageHelper.setTo(to);
        // 主题
        messageHelper.setSubject(subject);
        // 邮件正文
        messageHelper.setText(content, true);
        // 发送邮件
        try {
            mailSender.send(mimeMessage);
            logger.info("邮件发送成功");
        }catch (Exception e){
            logger.error("异常:", e);
            return false;
        }
        return true;
    }

}

