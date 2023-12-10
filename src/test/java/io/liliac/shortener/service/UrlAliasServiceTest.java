package io.liliac.shortener.service;

import io.liliac.shortener.entity.UrlMappingEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlAliasServiceTest {

    private final String defaultDomainConfig = "http://domain.com";
    private final UrlMappingService mappingService = Mockito.mock(UrlMappingService.class);
    private final UrlAliasService urlAliasService = new UrlAliasService(defaultDomainConfig, mappingService);

    @Test
    public void createAlias_shouldThrowExceptionIfMappingNotFoundOrNotCreated() {
        var validUrl = "http://validUrl.com";
        Mockito.when(mappingService.createNewOrGetExistingMapping(validUrl)).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> urlAliasService.createAlias(validUrl));
    }

    @Test
    public void createAlias_shouldReturnResponseWithoutErrorsIfMappingExistsOrCreated() {
        var validUrl = "http://validUrl.com/";
        var mapping = UrlMappingEntity.builder()
                .sourceUrl(validUrl)
                .alias("alias")
                .createdNow()
                .build();
        Mockito.when(mappingService.createNewOrGetExistingMapping(validUrl)).thenReturn(mapping);

        var response = urlAliasService.createAlias(validUrl);

        assertNotNull(response);
        assertEquals(validUrl, response.source());
        assertEquals(mapping.getCreatedAt(), response.createdAt());
        assertEquals(mapping.getAlias(), response.alias());
        assertEquals(defaultDomainConfig + mapping.getAlias(), response.aliasFullUrl());
    }
}