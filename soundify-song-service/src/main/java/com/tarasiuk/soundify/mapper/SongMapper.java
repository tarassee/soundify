package com.tarasiuk.soundify.mapper;

import com.tarasiuk.soundify.data.SongData;
import com.tarasiuk.soundify.model.Song;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mapping(target = "resourceId", expression = "java(Integer.parseInt(songData.resourceId()))")
    Song toSong(SongData songData);

    @Mapping(target = "resourceId", expression = "java(String.valueOf(song.getResourceId()))")
    SongData toSongData(Song song);
}
