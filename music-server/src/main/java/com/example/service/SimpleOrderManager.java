package com.example.service;


import org.springframework.core.annotation.Order;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SimpleOrderManager {

    @Resource
    JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bnmhy@foxmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Sent Successfully...");
    }

//    public void setMailSender(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void placeOrder(final Order order) {
//        // Do the business calculations...
//        // Call the collaborators to persist the order...
//
//        MimeMessagePreparator preparator = new MimeMessagePreparator() {
//
//
//            public void prepare(MimeMessage mimeMessage) throws Exception {
//                mimeMessage.setRecipient(Message.RecipientType.TO,
//                        new InternetAddress(order.getCustomer().getEmailAddress()));
//                mimeMessage.setFrom(new InternetAddress("mail@mycompany.example"));
//                mimeMessage.setText("Dear " + order.getCustomer().getFirstName() + " " +
//                        order.getCustomer().getLastName() + ", thanks for your order. " +
//                        "Your order number is " + order.getOrderNumber() + ".");
//            }
//        };
//
//        try {
//            this.mailSender.send(preparator);
//        } catch (MailException ex) {
//            // simply log it and go on...
//            System.err.println(ex.getMessage());
//        }
//    }

}