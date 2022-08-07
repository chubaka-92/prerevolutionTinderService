package ru.liga.prerevolutionarytinderserver.entity;

import lombok.*;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String gender;
    private String description;
    private String preference;

}
