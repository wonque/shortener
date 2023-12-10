package io.liliac.shortener.service;

import io.liliac.shortener.dao.UrlMappingDao;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.liliac.shortener.service.hash.HashingService;
import io.liliac.shortener.service.id.MappingIdProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
class UrlMappingService {

    private final Logger logger = LoggerFactory.getLogger(UrlMappingService.class);

    private final HashingService hashService;
    private final MappingIdProvider mappingIdProvider;
    private final UrlMappingDao mappingDao;

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
            logger.debug("Existing record found for url {}", longUrl);
            return entity;
        }
        Long id = mappingIdProvider.provide();
        String alias = hashService.generateBase62alias(id);
        entity = UrlMappingEntity.builder()
                .sourceUrl(longUrl)
                .sourceUrlHash(hashedLongUrl)
                .alias(alias)
                .generatedId(id)
                .createdNow()
                .build();
        mappingDao.persist(entity);
        logger.debug("New url mapping record created for url {} with alias {}", longUrl, alias);
        return entity;
    }
}
