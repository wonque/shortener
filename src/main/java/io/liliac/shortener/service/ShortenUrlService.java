package io.liliac.shortener.service;

import io.liliac.shortener.dto.ShortenUrlResponse;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.*;

@ApplicationScoped
@Transactional
public class ShortenUrlService {

    private UrlMappingService mappingService;
    private String defaultDomain;

    @Inject
    public ShortenUrlService(@ConfigProperty(name = "url.domain", defaultValue = "http://shor.ty/")
                             String defaultDomain,
                             UrlMappingService mappingService) {
        this.defaultDomain = defaultDomain;
        this.mappingService = mappingService;
    }

    @CacheResult(cacheName = "shorten_url")
    public ShortenUrlResponse process(String longUrl) {
        UrlMappingEntity mapping = mappingService.createNewOrGetExistingMapping(longUrl);
        if (Objects.isNull(mapping)) {
            throw new IllegalStateException("Unable to process url " + longUrl);
        }
        return new ShortenUrlResponse(defaultDomain + mapping.getShortUrlHash(), mapping.getSourceUrl());
    }
}
