package com.example.controller;

import com.example.service.MailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试邮件发送
 * @author qzz
 */
@CrossOrigin
@RestController
@RequestMapping("/mail")
@Api(tags = "邮件管理")
public class SendMailController {

    @Resource
    private MailService mailService;


    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param text
     */
    @PostMapping("/sendTextMail")
    public void sendTextMail(String to,String subject,String text){
        mailService.sendTextMailMessage(to,subject,text);
    }

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     */
    @PostMapping("/sendHtmlMailMessage")
    public void sendHtmlMailMessage(String to,String subject,String content){
        mailService.sendHtmlMailMessage(to,subject,content);
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     */
    @PostMapping("/sendAttachmentMailMessage")
    public void sendAttachmentMailMessage(String to,String subject,String content,String filePath){
        filePath="D:\\1.png";
        mailService.sendAttachmentMailMessage(to,subject,content,filePath);
    }
}
