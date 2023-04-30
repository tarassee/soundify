package com.tarasiuk.soundify.producer;

import com.taraiuk.soundify.data.ResourceMessageData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ResourceMessageProducerTest {

    private static final String EXCHANGE = "exchange";
    private static final String ROUTING_KEY = "routingKey";
    @Mock
    private AmqpTemplate amqpTemplate;
    @InjectMocks
    private ResourceMessageProducer testInstance;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testInstance, "exchange", EXCHANGE);
        ReflectionTestUtils.setField(testInstance, "routingKey", ROUTING_KEY);
    }

    @Test
    void shouldSendMessage() {
        ResourceMessageData message = new ResourceMessageData(3);

        testInstance.send(message);

        verify(amqpTemplate).convertAndSend(EXCHANGE, ROUTING_KEY, message);
    }
}
