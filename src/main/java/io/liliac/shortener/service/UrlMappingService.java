package io.liliac.shortener.service;

import io.liliac.shortener.dao.UrlMappingDao;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.liliac.shortener.service.hash.HashingService;
import io.liliac.shortener.service.id.MappingIdProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class UrlMappingService {

    private HashingService hashService;
    private MappingIdProvider mappingIdProvider;
    private UrlMappingDao mappingDao;

    @Inject
    UrlMappingService(HashingService hashService, MappingIdProvider mappingIdProvider, UrlMappingDao mappingDao) {
        this.hashService = hashService;
        this.mappingIdProvider = mappingIdProvider;
        this.mappingDao = mappingDao;
    }

    UrlMappingEntity createNewOrGetExistingMapping(String longUrl) {
        var hashedLongUrl = hashService.generateMD5HexString(longUrl);
        UrlMappingEntity entity = mappingDao.getByLongUrlHash(hashedLongUrl);
        if (entity != null) {
            return entity;
        }
        Long id = mappingIdProvider.provide();
        String shortUrl = hashService.generateBase62Hash(id);
        entity = UrlMappingEntity.builder()
                .sourceUrl(longUrl)
                .sourceUrlHash(hashedLongUrl)
                .shortUrlHash(shortUrl)
                .generatedId(id)
                .createdNow()
                .build();
        mappingDao.persist(entity);
        return entity;
    }
}
