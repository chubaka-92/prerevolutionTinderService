package ru.liga.prerevolutionarytinderserver.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "person_intersect")
public class Relationships {

    @Id
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "selected_person_id")
    private Long selectedPersonId;

}
