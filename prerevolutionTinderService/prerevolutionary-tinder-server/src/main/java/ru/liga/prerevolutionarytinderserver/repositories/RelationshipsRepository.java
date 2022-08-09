package ru.liga.prerevolutionarytinderserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.liga.prerevolutionarytinderserver.entity.Relationships;

public interface RelationshipsRepository extends JpaRepository<Relationships, Long> {

    String updateReciprocity = "update  person_intersect\n" +
            "set reciprocity='true'\n" +
            "where (person_id = ?1 and selected_person_id = ?2) or (person_id = ?2 and selected_person_id = ?1)";

    @Modifying
    @Query(value = updateReciprocity, nativeQuery = true)
    void updateReciprocity(Long id, Long currentPersonId);
}
