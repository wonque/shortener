package io.liliac.shortener.service;

import io.liliac.shortener.dao.UrlMappingDao;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.liliac.shortener.exception.MappingNotFoundException;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@ApplicationScoped
@Transactional
public class RedirectService {

    private final Logger logger = LoggerFactory.getLogger(RedirectService.class);
    private final UrlMappingDao mappingDao;

    @Inject
    public RedirectService(UrlMappingDao mappingDao) {
        this.mappingDao = mappingDao;
    }

    @CacheResult(cacheName = "alias_redirect")
    public String getRedirectSource(String alias) {
        UrlMappingEntity existingMapping = mappingDao.getByShortUrl(alias);
        if (Objects.isNull(existingMapping)) {
            throw new MappingNotFoundException("Unable to find source url for " + alias);
        }
        logger.debug("Mapping exists for alias {}", alias);
        return existingMapping.getSourceUrl();
    }
}
