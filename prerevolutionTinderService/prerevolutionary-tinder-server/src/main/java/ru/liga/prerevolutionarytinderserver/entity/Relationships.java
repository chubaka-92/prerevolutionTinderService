package ru.liga.prerevolutionarytinderserver.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_intersect")
public class Relationships {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "selected_person_id")
    private Long selectedPersonId;

}
