package com.main.koko_main_api.dtos;

public interface RequestDeassembler<PayloadDto, EntityDto> {
    public EntityDto toEntity(PayloadDto payloadDto);
}
