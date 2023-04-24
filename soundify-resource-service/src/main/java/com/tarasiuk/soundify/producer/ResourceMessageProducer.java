package com.tarasiuk.soundify.producer;

import com.taraiuk.soundify.data.ResourceMessageData;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ResourceMessageProducer {

    @Value("${application.rabbitmq.exchange}")
    private String exchange;
    @Value("${application.rabbitmq.routing-key}")
    private String routingKey;
    private final AmqpTemplate amqpTemplate;

    public void send(ResourceMessageData message) {
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }
}
