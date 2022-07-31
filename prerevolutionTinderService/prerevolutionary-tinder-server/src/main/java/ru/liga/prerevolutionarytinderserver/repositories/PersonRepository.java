package ru.liga.prerevolutionarytinderserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
