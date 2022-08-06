package ru.liga.prerevolutionarytinderclient.dto;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonsResponse {
    private String caption;
    private byte[] picture;
    private int totalPage;
    private int currentPage;
}
