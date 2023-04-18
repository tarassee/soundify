package com.tarasiuk.soundify.service;

import data.SongData;
import org.apache.tika.metadata.Metadata;

public interface SongDataService {

    SongData retrieveFrom(Integer id, Metadata metadata);

}
