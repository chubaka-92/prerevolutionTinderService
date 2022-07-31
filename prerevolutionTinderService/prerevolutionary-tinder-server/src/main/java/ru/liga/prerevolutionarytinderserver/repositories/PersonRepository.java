package ru.liga.prerevolutionarytinderserver.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "select ps.* from person p join person_intersect pi on p.id = pi.person_id join person ps on pi.selected_person_id = ps.id  where p.ID = ?1", nativeQuery = true)
    Page<Person> findLikedPersonsByMe(Long id, PageRequest pageRequest);
}
