package com.tarasiuk.soundify;

import com.tarasiuk.soundify.controller.DefaultAudioProcessingController;
import com.tarasiuk.soundify.facade.AudioFacade;
import com.tarasiuk.soundify.service.AudioService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SoundifyResourceServiceApplication.class)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
public class BaseTestClass {

    @Autowired
    private DefaultAudioProcessingController defaultAudioProcessingController;
    @MockBean
    private AudioFacade audioFacade;
    @MockBean
    private AudioService audioService;

    @BeforeEach
    void setUp() {
        when(audioFacade.getAudioContentById(anyInt())).thenReturn(new byte[]{101, 21, 32, 65});
        RestAssuredMockMvc.standaloneSetup(defaultAudioProcessingController);
    }

}
