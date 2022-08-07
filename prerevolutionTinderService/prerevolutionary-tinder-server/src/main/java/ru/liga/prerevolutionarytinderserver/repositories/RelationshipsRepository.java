package ru.liga.prerevolutionarytinderserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.prerevolutionarytinderserver.entity.Relationships;

public interface RelationshipsRepository  extends JpaRepository<Relationships, Long> {
}
