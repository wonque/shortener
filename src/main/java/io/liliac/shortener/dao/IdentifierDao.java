package io.liliac.shortener.dao;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class IdentifierDao {

    @Inject
    @PersistenceContext
    private EntityManager em;

    public long nextSequenceValue() {
        return (long) em.createNativeQuery("SELECT nextval('urlMappingUniqueIdSequence')").getSingleResult();
    }
}
