package com.main.koko_main_api.dtos;

public interface PayloadDeassembler<PayloadDto, EntityDto> {
    public EntityDto toEntityDto(PayloadDto payloadDto);
}
