package com.tarasiuk.soundify;

import com.tarasiuk.soundify.controller.DefaultSongController;
import com.tarasiuk.soundify.model.Song;
import com.tarasiuk.soundify.service.SongService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SoundifySongServiceApplication.class)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
class BaseTestClass {

    @Autowired
    private DefaultSongController defaultSongController;
    @MockBean
    private SongService songService;

    @BeforeEach
    void setUp() {
        when(songService.uploadSong(any(Song.class))).thenReturn(1);
        RestAssuredMockMvc.standaloneSetup(defaultSongController);
    }
}
