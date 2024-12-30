package com.university.wiki.userChanges.RMQ;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public Queue adQueue() {
        return new Queue("adQueue", true);
    }
}