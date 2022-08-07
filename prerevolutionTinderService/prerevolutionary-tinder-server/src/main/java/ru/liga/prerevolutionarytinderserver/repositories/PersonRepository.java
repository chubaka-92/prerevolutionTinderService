package ru.liga.prerevolutionarytinderserver.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.liga.prerevolutionarytinderserver.entity.Person;

import java.util.Collection;

public interface PersonRepository extends JpaRepository<Person, Long> {

    String selectMyLikeList = "select distinct p.*\n" +
            "from person p, person_intersect pi\n" +
            "where (p.id = pi.person_id or p.id = pi.selected_person_id)\n" +
            "  and (pi.selected_person_id = ?1 or pi.person_id = ?1)\n" +
            "  and p.id not in (?1)";

    String selectCandidateFavoritesByMe = "select p.*\n" +
            "from person p\n" +
            "where p.preference in :preferences\n" +
            "  and p.gender in :genders\n" +
            "  and p.id <> :id\n" +
            "  and p.id not in (select pi.selected_person_id\n" +
            "                    from person_intersect  pi\n" +
            "                    where (person_id = :id)\n" +
            "                       or (pi.selected_person_id =:id\n" +
            "                        and pi.reciprocity=true)\n" +
            ")";

    String updateReciprocity = "update  person_intersect\n" +
            "set reciprocity='true'\n" +
            "where (person_id = ?1 and selected_person_id = ?2) or (person_id = ?2 and selected_person_id = ?1)";

    @Query(value = selectMyLikeList, nativeQuery = true)
    Page<Person> findMyLikeList(Long id, PageRequest pageRequest);

    @Query(value = "select count(pi.*) from person_intersect pi where (pi.person_id = ?1 and pi.selected_person_id = ?2) or (pi.person_id = ?2 and pi.selected_person_id = ?1)", nativeQuery = true)
    Integer countReciprocity(Long personId, Long selectedPersonId);

    @Query(value = "select count(pi.*) from person_intersect pi where (pi.person_id = ?1 and pi.selected_person_id = ?2)", nativeQuery = true)
    Integer countLikedByMe(Long personId, Long selectedPersonId);

    @Query(value = "select count(pi.*) from person_intersect pi where (pi.person_id = ?2 and pi.selected_person_id = ?1)", nativeQuery = true)
    Integer countYouLikeMe(Long personId, Long selectedPersonId);

    @Query(value = selectCandidateFavoritesByMe, nativeQuery = true)
    Page<Person> findCandidateFavoritesByMe(
            @Param("id") Long id,
            @Param("genders") Collection genders,
            @Param("preferences") Collection preferences,
            PageRequest pageRequest);

    @Modifying
    @Query(value = updateReciprocity, nativeQuery = true)
    void updateReciprocity(Long id, Long currentPersonId);
}
