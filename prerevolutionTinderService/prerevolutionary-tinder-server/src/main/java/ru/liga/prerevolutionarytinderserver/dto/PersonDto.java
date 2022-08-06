package ru.liga.prerevolutionarytinderserver.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class PersonDto {
    private String caption;
    private byte[] picture;
    private int totalPage;
    private int currentPage;
}
