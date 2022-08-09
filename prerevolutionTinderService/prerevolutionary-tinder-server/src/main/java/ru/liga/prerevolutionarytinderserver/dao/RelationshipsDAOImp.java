package ru.liga.prerevolutionarytinderserver.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.prerevolutionarytinderserver.api.RelationshipsDAO;
import ru.liga.prerevolutionarytinderserver.entity.Relationships;
import ru.liga.prerevolutionarytinderserver.repositories.RelationshipsRepository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RelationshipsDAOImp implements RelationshipsDAO {

    private final RelationshipsRepository relationshipsRepository;

    @Transactional
    public Relationships addRelationships(Relationships relationships) {
        log.info("Was calling addRelationships. Input relationships: {}", relationships);
        return relationshipsRepository.save(relationships);
    }

    @Transactional
    public void updateReciprocity(Long id, Long selectedId) {
        log.info("Was calling updateReciprocity. Input id: {} selectedId: {}", id, selectedId);
        relationshipsRepository.updateReciprocity(id, selectedId);
    }
}
