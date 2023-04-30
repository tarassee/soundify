package com.tarasiuk.soundify.service.impl;

import com.tarasiuk.soundify.client.ResourceServiceClient;
import com.tarasiuk.soundify.exception.ClientCallException;
import com.tarasiuk.soundify.service.ResourceServiceService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultResourceServiceService implements ResourceServiceService {

    private final ResourceServiceClient resourceServiceClient;

    @Override
    public byte[] getAudio(Integer id) {
        try {
            return resourceServiceClient.getAudio(id);
        } catch (FeignException e) {
            log.error("Failed to get audio with id {} from resource-service. Reason: {}", id, e.getCause());
            throw new ClientCallException(e.getMessage());
        }
    }

}
