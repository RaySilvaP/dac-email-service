package com.example.emailService.service;

import com.example.emailService.dto.PurchaseItemEmailDto;
import com.example.emailService.exceptions.EmailException;
import com.example.emailService.messaging.PurchaseConfirmationEvent;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired JavaMailSender mailSender;

    @Autowired TemplateEngine templateEngine;

    @Async
    public void sendPurchaseConfirmation(PurchaseConfirmationEvent event) {
        try {
            if (event.purchaseItems() == null || event.purchaseItems().isEmpty()) {
                throw new EmailException("A lista de itens da compra está vazia.");
            }

            PurchaseItemEmailDto firstItem = event.purchaseItems().iterator().next();
            if (firstItem.eventName() == null) {
                throw new EmailException("Dados incompletos no item da compra.");
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(event.emailAddress());
            helper.setSubject("Confirmação de Compra - " + firstItem.eventName());

            Context context = new Context();
            context.setVariable("userName", event.userName());
            context.setVariable("eventName", firstItem.eventName());
            context.setVariable("totalPrice", event.totalPrice());

            List<Map<String, Object>> ticketsList = event.purchaseItems().stream().map(purchaseItem -> {
                Map<String, Object> ticketData = new HashMap<>();
                ticketData.put("ticketType", purchaseItem.eventName());
                ticketData.put("quantity", purchaseItem.quantity());
                ticketData.put("price", purchaseItem.price());
                return ticketData;
            }).collect(Collectors.toList());

            context.setVariable("tickets", ticketsList);

            String htmlContent = templateEngine.process("email/purchase-confirmation", context);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new EmailException("Falha ao enviar e-mail: " + e.getMessage());
        }
    }
}