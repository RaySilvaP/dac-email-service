package com.example.emailService.messaging;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailRequestConsumer {

    private final Logger logger = LoggerFactory.getLogger(EmailRequestConsumer.class);

    @RabbitListener(queues = "email-notification-request-queue")
    public void processPurchaseConfirmation(String message){
        logger.info(message);
    }
}
