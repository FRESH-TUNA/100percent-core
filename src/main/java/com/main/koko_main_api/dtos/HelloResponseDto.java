package com.main.koko_main_api.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Getter: 선언된 필드의 get메소드 생성
 * RequiredArgsConstructor: final 필드가 포함된 생성자를 생성
 */
@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
