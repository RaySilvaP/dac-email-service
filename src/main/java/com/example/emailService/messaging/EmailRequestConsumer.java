package com.example.emailService.messaging;

import com.example.emailService.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailRequestConsumer {

    @Autowired
    EmailService emailService;

    @RabbitListener(queues = "email-notification-request-queue")
    public void processPurchaseConfirmation(PurchaseConfirmationEvent event){
        emailService.sendPurchaseConfirmation(event);
    }
}
