package com.tarasiuk.soundify.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "resource-service", url = "${application.resource-service.url}")
public interface ResourceServiceClient {

    @GetMapping("resources/{id}")
    byte[] getAudio(@PathVariable("id") Integer id);

}
