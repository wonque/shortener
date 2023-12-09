package io.liliac.shortener.service;

import io.liliac.shortener.dao.UrlMappingDao;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.liliac.shortener.exception.MappingNotFoundException;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Objects;

@ApplicationScoped
@Transactional
public class RedirectService {

    private UrlMappingDao mappingDao;

    @Inject
    public RedirectService(UrlMappingDao mappingDao) {
        this.mappingDao = mappingDao;
    }

    @CacheResult(cacheName = "shorten_url_redirect")
    public String getLocation(String shortUrl) {
        UrlMappingEntity existingMapping = mappingDao.getByShortUrl(shortUrl);
        if (Objects.isNull(existingMapping)) {
            throw new MappingNotFoundException("Unable to find source url for " + shortUrl);
        }
        return existingMapping.getSourceUrl();
    }
}
