package ru.liga.prerevolutionarytinderclient.dto;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PersonRequest {
    private Long id;
    private String name;
    private String gender;
    private String description;
    private String preference;

    @Override
    public String toString() {
        return "Вот что ты навводил: " +
                "\n Имя: " + name +
                "\n Пол: " + gender +
                "\n Описание: " + description +
                "\n Кто интересен: " + preference;
    }
}
