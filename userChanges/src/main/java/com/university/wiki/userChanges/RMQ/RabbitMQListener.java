package com.university.wiki.userChanges.RMQ;

import com.university.wiki.userChanges.entity.Ad;
import com.university.wiki.userChanges.service.AdService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    @Autowired
    private AdService adService;

    @RabbitListener(queues = "adQueue")
    public void receiveMessage(@Payload String message) {
        Ad ad = new Ad();
        ad.setImageLink(message);
        System.out.println("Received message from adQueue: " + message);
        adService.insertAd(ad);
    }
}