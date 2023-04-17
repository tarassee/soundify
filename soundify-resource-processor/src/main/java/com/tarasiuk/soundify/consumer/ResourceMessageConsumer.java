package com.tarasiuk.soundify.consumer;

import data.ResourceMessageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ResourceMessageConsumer {

    @RabbitListener(queues = "${application.rabbitmq.queue}")
    public void listener(ResourceMessageData message) {
        log.info("Listening message: " + message.id());
    }

}
