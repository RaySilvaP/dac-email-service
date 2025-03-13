package com.example.emailService.service;

import com.example.emailService.messaging.EmailErrorEvent;
import com.example.emailService.messaging.EmailRequestProducer;
import com.example.emailService.messaging.EmailSuccessEvent;
import com.example.emailService.messaging.PurchaseConfirmationEvent;
import com.example.emailService.exceptions.EmailException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired JavaMailSender mailSender;

    @Autowired TemplateEngine templateEngine;

    @Autowired EmailRequestProducer producer;

    @Async
    public void sendPurchaseConfirmation(PurchaseConfirmationEvent purchase) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(purchase.emailAddress());
            helper.setSubject("Confirmação de Compra - " + purchase.eventName());

            Context context = new Context();
            context.setVariable("userName", purchase.userName());
            context.setVariable("event", purchase.eventName());
            context.setVariable("ticketType", purchase.ticketType());
            context.setVariable("quantity", purchase.quantity());
            context.setVariable("totalPrice", purchase.totalPrice());

            String htmlContent = templateEngine.process("email/purchase-confirmation", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            EmailSuccessEvent event = new EmailSuccessEvent(purchase.emailAddress());
            producer.registerSuccessEmail(event);
        } catch (Exception e) {
            EmailException exception = new EmailException("Falha ao enviar e-mail: " + e.getMessage());
            EmailErrorEvent event = new EmailErrorEvent(purchase.emailAddress(), exception.getMessage());
            producer.registerErrorEmail(event);
            throw exception;
        }
    }
}