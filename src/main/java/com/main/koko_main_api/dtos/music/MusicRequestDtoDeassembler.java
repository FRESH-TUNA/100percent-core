package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Composer;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.RequestDeassembler;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicRequestDtoDeassembler
        implements RequestDeassembler<MusicRequestDto, Music> {

    @Autowired
    private AlbumRepository albumRepository;


    @Autowired
    private ComposerRepository composerRepository;

    @Override
    public Music toEntity(MusicRequestDto musicRequestDto) {
        Album album = albumRepository.getById(convertURItoID(musicRequestDto.getAlbum()));
        List<Composer> composers = new ArrayList<>();
        String title = musicRequestDto.getTitle();

        for(URI composer_uri : musicRequestDto.getComposers())
            composers.add(composerRepository.getById(convertURItoID(composer_uri)));

        return Music.builder().title(title).album(album).composers(composers).build();
    }

    private Long convertURItoID(URI uri) {
        String[] datas = uri.toString().split("/");
        return Long.parseLong(datas[datas.length - 1]);
    }
}
