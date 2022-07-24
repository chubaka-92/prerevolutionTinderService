package ru.liga.prerevolutionarytinderclient.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonResponse {
    private PersonRequest personRequest;
    private byte[] picture;
}
