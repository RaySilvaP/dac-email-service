package com.example.emailService.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EmailAmqpConfig {

    @Bean
    public Queue requestQueue(){
        return new Queue("email-request-queue");
    }

    @Bean
    public TopicExchange requestExchange(){
        return new TopicExchange("email-exchange");
    }

    @Bean
    public  Binding resquestBinding(Queue requestQueue, TopicExchange requestExchange) {
        return BindingBuilder.bind(requestQueue).to(requestExchange)
                .with("email-request");
    }
}
