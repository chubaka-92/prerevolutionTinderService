package ru.liga.prerevolutionarytinderserver.api;

import ru.liga.prerevolutionarytinderserver.entity.Relationships;

public interface RelationshipsDAO {

    Relationships addRelationships(Relationships relationships);

    void updateReciprocity(Long id, Long selectedId);
}
