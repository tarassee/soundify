package com.tarasiuk.soundify.consumer;

import com.taraiuk.soundify.data.ResourceMessageData;
import com.tarasiuk.soundify.processor.SongProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ResourceMessageConsumerTest {

    @Mock
    private SongProcessor songProcessor;
    @InjectMocks
    private ResourceMessageConsumer testInstance;

    @Test
    void shouldProcessSongWhileListenerExecution() {
        ResourceMessageData message = new ResourceMessageData(1);

        testInstance.listener(message);

        verify(songProcessor).processSong(1);
    }

}
