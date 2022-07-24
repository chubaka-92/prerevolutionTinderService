package ru.liga.prerevolutionarytinderserver.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "person")
public class Person {

    @Id
    private Long id;

    private String name;
    private String gender;
    private String description;
    private String preference;

    //думал хранить байткод картинки, и чтоб с ним бот работал. кароч при создании пишим в это поле) Пока не используется в боте
    private byte[] picture;


}
