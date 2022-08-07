package ru.liga.prerevolutionarytinderserver.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionarytinderserver.entity.Relationships;
import ru.liga.prerevolutionarytinderserver.repositories.RelationshipsRepository;
@Repository
@RequiredArgsConstructor
public class RelationshipsDAO {
    private final RelationshipsRepository relationshipsRepository;

    @Transactional
    public Relationships addRelationships(Relationships relationships) {
        return relationshipsRepository.save(relationships);
    }
}
