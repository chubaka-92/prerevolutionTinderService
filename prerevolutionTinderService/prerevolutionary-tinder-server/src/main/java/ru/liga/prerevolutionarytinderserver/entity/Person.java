package ru.liga.prerevolutionarytinderserver.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "person")
public class Person {
    /*
    тут наверное надо подумать над названием сущности...толи форма,толи профиль, толи юзер...или персона

    что будим хранить...
        айдишник, а какой? из телеги вроде как можно айдишкик чата получать..наверное можно его в качестве айди замутить
        Имя
        пол
        кого ищет(тут енам...)
        Описание...тут надо определиться чо сохранять будим) толи прям текст из тг, толи уже обработаную картинку закодированую.скорее всего последний вариант
        но пока просто тескт наверное)
     */

    @Id
    private Long id;
    private String name;
    private String gender;
    private String description;
    private String preference;


}
