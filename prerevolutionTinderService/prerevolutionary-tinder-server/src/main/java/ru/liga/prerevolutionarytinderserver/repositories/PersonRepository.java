package ru.liga.prerevolutionarytinderserver.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.liga.prerevolutionarytinderserver.entity.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
