package com.example.emailService.messaging;

import com.example.emailService.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailRequestConsumer {

    @Autowired private EmailService emailService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void processPurchaseConfirmation(@Payload Message<PurchaseConfirmationEvent> message){
        PurchaseConfirmationEvent event = message.getPayload();
        emailService.sendPurchaseConfirmation(event);
    }
}
