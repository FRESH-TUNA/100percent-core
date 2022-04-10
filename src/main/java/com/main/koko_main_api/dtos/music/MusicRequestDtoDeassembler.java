package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Bpm;
import com.main.koko_main_api.domains.Composer;
import com.main.koko_main_api.domains.Music;
import com.main.koko_main_api.dtos.RequestDeassembler;
import com.main.koko_main_api.dtos.music.bpm.MusicBpmsRequestDto;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import com.main.koko_main_api.repositories.music.MusicRepository;
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
    private MusicRepository musicRepository;

    @Autowired
    private ComposerRepository composerRepository;

    // composer add 추가할것
    @Override
    public Music toEntity(MusicRequestDto musicRequestDto) {
        musicRepository.getById(convertURItoID(musicRequestDto.getMusic()));
        Album album = albumRepository.getById(convertURItoID(musicRequestDto.getAlbum()));
        List<Composer> composers = new ArrayList<>();
        String title = musicRequestDto.getTitle();

        for(URI composer_uri : musicRequestDto.getComposers())
            composers.add(composerRepository.getById(convertURItoID(composer_uri)));

        if(musicRequestDto.getMusic() == null)
            return Music.builder().title(title).album(album).build();
        else
            return Music.builder().id(convertURItoID(musicRequestDto.getMusic()))
                    .title(title).album(album).build();
    }

    private Long convertURItoID(URI uri) {
        String[] datas = uri.toString().split("/");
        return Long.parseLong(datas[datas.length - 1]);
    }
}
