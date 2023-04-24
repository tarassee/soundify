package com.tarasiuk.soundify.consumer;

import com.tarasiuk.soundify.exception.ClientCallException;
import com.tarasiuk.soundify.exception.MetadataException;
import com.tarasiuk.soundify.processor.SongProcessor;
import com.taraiuk.soundify.data.ResourceMessageData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResourceMessageConsumer {

    private final SongProcessor songProcessor;

    @Retryable(value = {ClientCallException.class, MetadataException.class},
            maxAttempts = 4, backoff = @Backoff(delay = 2000))
    @RabbitListener(queues = "${application.rabbitmq.queue}")
    public void listener(ResourceMessageData message) {
        Integer id = message.id();
        log.info("Listening message with id {}", id);

        songProcessor.processSong(id);

        log.info("The message with id {} was successfully processed", id);
    }

    @Recover
    public void recover(Exception ex, ResourceMessageData message) {
        log.error("Failed to process song with id {} after max retries. Error message: {}", message.id(), ex.getMessage());
    }

}
