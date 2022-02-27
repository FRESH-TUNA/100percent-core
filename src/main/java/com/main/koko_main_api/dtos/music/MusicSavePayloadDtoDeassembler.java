package com.main.koko_main_api.dtos.music;

import com.main.koko_main_api.domains.Album;
import com.main.koko_main_api.domains.Composer;
import com.main.koko_main_api.dtos.PayloadDeassembler;
import com.main.koko_main_api.repositories.ComposerRepository;
import com.main.koko_main_api.repositories.album.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Component
public class MusicSavePayloadDtoDeassembler
        implements PayloadDeassembler<MusicSavePayloadDto, MusicSaveDto> {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ComposerRepository composerRepository;

    @Override
    public MusicSaveDto toEntityDto(MusicSavePayloadDto musicSavePayloadDto) {
        Album album = null;
        List<Composer> composers = new ArrayList<>();
        String title = musicSavePayloadDto.getTitle();
        List<Long> composer_ids = new ArrayList<>();

        URI album_uri = musicSavePayloadDto.getAlbum();
        if (album_uri != null) {
            Long album_id = convertURItoID(album_uri);
            album = albumRepository.findById(convertURItoID(album_uri))
                    .orElseThrow(() -> new IllegalArgumentException(
                            "해당 앨벙이 없습니다. id= " + album_id));
        }

        for(URI composer_uri : musicSavePayloadDto.getComposers())
            composer_ids.add(convertURItoID(composer_uri));
        if(composer_ids.size() > 0)
            composers = composerRepository.findAllById(composer_ids);

        return MusicSaveDto.builder()
                .title(title).album(album).composers(composers).build();
    }

    private Long convertURItoID(URI uri) {
        String[] datas = uri.toString().split("/");
        return Long.parseLong(datas[datas.length - 1]);
    }
}
