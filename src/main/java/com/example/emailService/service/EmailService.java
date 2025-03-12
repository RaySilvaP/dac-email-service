package com.example.emailService.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.example.emailService.domain.EmailRequest;
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

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void processEmail(EmailRequest request){
        sendPurchaseConfirmation(request);
    }

    @Async
    public void sendPurchaseConfirmation(EmailRequest request) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(request.getAddress());
            helper.setSubject("Confirmação de Compra - " + request.getEventName());

            Context context = new Context();
            context.setVariable("userName", request.getUserName());
            context.setVariable("event", request.getEventName());
            context.setVariable("ticketType", request.getTicketType());
            context.setVariable("quantity", request.getQuantity());
            context.setVariable("totalPrice", request.getTotalPrice());

            String htmlContent = templateEngine.process("email/purchase-confirmation", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Falha ao enviar e-mail: " + e.getMessage());
        }
    }
}