package com.main.koko_main_api.deassemblers;

import com.main.koko_main_api.domains.Album;

import com.main.koko_main_api.domains.Composer;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.RequestDeassembler;
import com.main.koko_main_api.dtos.music.MusicRequestDto;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicDeassembler
        implements RequestDeassembler<MusicRequestDto, Music> {

    @Autowired
    private AlbumRepository albumRepository;


    @Autowired
    private ComposerRepository composerRepository;

    // composer add 추가할것
    @Override
    public Music toEntity(MusicRequestDto dto) {
        Album album = albumRepository.getById(convertURItoID(dto.getAlbum()));
        List<Composer> composers = new ArrayList<>();
        String title = dto.getTitle();
        Integer min_bpm = dto.getMin_bpm(), max_bpm = dto.getMax_bpm();

        for(URI composer_uri : dto.getComposers())
            composers.add(composerRepository.getById(convertURItoID(composer_uri)));

        return Music.builder().title(title).album(album).composers(composers)
                .min_bpm(min_bpm).max_bpm(max_bpm).build();
    }

    private Long convertURItoID(URI uri) {
        String[] data = uri.toString().split("/");
        return Long.parseLong(data[data.length - 1]);
    }
}
