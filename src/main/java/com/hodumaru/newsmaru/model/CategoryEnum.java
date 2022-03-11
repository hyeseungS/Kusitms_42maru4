package com.hodumaru.newsmaru.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryEnum {
    POLITICS("politics", "정치"),
    ECONOMY("economy", "경제"),
    SOCIETY("society", "사회"),
    CULTURE("culture", "생활/문화"),
    WORLD("world", "세계"),
    TECH("teach", "기술/IT"),
    ENTERTAIN("entertain", "연예"),
    SPORT("sport", "스포츠");

    private final String key;
    private final String value;
}
