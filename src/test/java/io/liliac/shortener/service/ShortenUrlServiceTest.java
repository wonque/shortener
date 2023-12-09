package io.liliac.shortener.service;

import io.liliac.shortener.entity.UrlMappingEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShortenUrlServiceTest {

    private String defaultDomainConfig = "http://domain.com";
    private UrlMappingService mappingService = Mockito.mock(UrlMappingService.class);
    private ShortenUrlService shortenUrlService = new ShortenUrlService(defaultDomainConfig, mappingService);

    @Test
    public void process_shouldThrowExceptionIfMappingNotFoundOrNotCreated() {
        var validUrl = "http://validUrl.com";
        Mockito.when(mappingService.createNewOrGetExistingMapping(validUrl)).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> shortenUrlService.process(validUrl));
    }

    @Test
    public void process_shouldReturnResponseWithoutErrorsIfMappingExistsOrCreated() {
        var validUrl = "http://validUrl.com/";
        Mockito.when(mappingService.createNewOrGetExistingMapping(validUrl)).thenReturn(UrlMappingEntity.builder()
                .sourceUrl(validUrl)
                .shortUrlHash("hash")
                .build());

        var response = shortenUrlService.process(validUrl);

        assertNotNull(response);
        assertTrue(response.getValidationErrors().isEmpty());
        assertEquals(validUrl, response.getSource());
        assertEquals(defaultDomainConfig + "hash", response.getShortUrl());
    }
}