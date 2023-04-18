package com.tarasiuk.soundify.consumer;

import com.tarasiuk.soundify.processor.SongProcessor;
import data.ResourceMessageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceMessageConsumer {

    private final SongProcessor songProcessor;

    @RabbitListener(queues = "${application.rabbitmq.queue}")
    public void listener(ResourceMessageData message) {
        log.info("Listening message: " + message.id());

        songProcessor.processSong(message.id());
    }

}
