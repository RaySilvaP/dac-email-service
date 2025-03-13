package com.example.emailService.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailRequestProducer {
    @Autowired AmqpTemplate amqpTemplate;

    public void registerErrorEmail(EmailErrorEvent event){
        amqpTemplate.convertAndSend("${rabbitmq.exchange}", "${rabbitmq.routing-key.response-error}", event);
    }

    public void registerSuccessEmail(EmailSuccessEvent event){
        amqpTemplate.convertAndSend("${rabbit.exchange}", "${rabbitmq.routing-key.response-success}", event);
    }
}
