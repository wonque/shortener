package io.liliac.shortener.service;

import io.liliac.shortener.dto.AliasDataDto;
import io.liliac.shortener.dto.GenerateAliasResponse;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.*;

@ApplicationScoped
@Transactional
public class UrlAliasService {

    private final UrlMappingService mappingService;
    private final String defaultDomain;

    @Inject
    public UrlAliasService(@ConfigProperty(name = "url.domain")
                             String defaultDomain,
                           UrlMappingService mappingService) {
        this.defaultDomain = defaultDomain;
        this.mappingService = mappingService;
    }

    @CacheResult(cacheName = "url_alias")
    public AliasDataDto createAlias(String longUrl) {
        UrlMappingEntity mapping = mappingService.createNewOrGetExistingMapping(longUrl);
        if (Objects.isNull(mapping)) {
            throw new IllegalStateException("Unable to process url " + longUrl);
        }
        return new AliasDataDto(
                mapping.getSourceUrl(),
                defaultDomain,
                mapping.getAlias(),
                defaultDomain + mapping.getAlias(),
                mapping.getCreatedAt()
        );
    }
}
