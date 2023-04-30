package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.client.ResourceServiceClient;
import com.tarasiuk.soundify.exception.ClientCallException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultResourceServiceServiceTest {

    private static final byte[] AUDIO_BYTES = new byte[]{1, 2, 3};
    @Mock
    private ResourceServiceClient resourceServiceClient;
    @InjectMocks
    private DefaultResourceServiceService testInstance;

    @Test
    void shouldReturnByteArrayAfterGetAudio() {
        when(resourceServiceClient.getAudio(any())).thenReturn(AUDIO_BYTES);

        byte[] actualAudio =
                testInstance.getAudio(1);

        assertArrayEquals(AUDIO_BYTES, actualAudio);
    }

    @Test
    void shouldThrowClientCallExceptionInCaseOfClientIssues() {
        when(resourceServiceClient.getAudio(any())).thenThrow(FeignException.class);

        assertThrows(ClientCallException.class, () ->
                testInstance.getAudio(1));
    }

}
