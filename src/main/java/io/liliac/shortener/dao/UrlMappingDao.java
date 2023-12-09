package io.liliac.shortener.dao;

import io.liliac.shortener.entity.UrlMappingEntity;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Singleton
public class UrlMappingDao {

    @Inject
    @PersistenceContext
    private EntityManager em;

    public void persist(UrlMappingEntity entity) {
        em.persist(entity);
    }

    public UrlMappingEntity getByShortUrl(String shortUrl) {
        return em.find(UrlMappingEntity.class, shortUrl);
    }

    public UrlMappingEntity getByLongUrlHash(String sourceUrlHash) {
        TypedQuery<UrlMappingEntity> query = em.createQuery(
                "SELECT u FROM UrlMappingEntity u WHERE u.sourceUrlHash = :sourceUrlHash", UrlMappingEntity.class);
        query.setParameter("sourceUrlHash", sourceUrlHash);
        return query.getResultList().stream().findFirst().orElse(null);
    }
}
