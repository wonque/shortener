package io.liliac.shortener.service;

import io.liliac.shortener.dao.UrlMappingDao;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.liliac.shortener.service.hash.HashingService;
import io.liliac.shortener.service.id.MappingIdProvider;
import io.liliac.shortener.service.id.SequenceMappingIdProvider;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class UrlMappingServiceTest {

    private final HashingService hashingService = new HashingService();
    private final MappingIdProvider mappingIdProvider = Mockito.mock(SequenceMappingIdProvider.class);
    private final UrlMappingDao mappingDao = Mockito.mock(UrlMappingDao.class);
    private final UrlMappingService service = new UrlMappingService(hashingService, mappingIdProvider, mappingDao);

    @Test
    public void createNewOrGetExistingMapping_returnExistingMappingIfSuchMappingExists() {
        var longUrl = "https://www.google.com";
        var longUrlHash = hashingService.generateMD5HexString(longUrl);
        var expectedEntity = UrlMappingEntity.builder()
                .sourceUrl(longUrl)
                .sourceUrlHash(longUrlHash)
                .build();

        Mockito.when(mappingDao.getByLongUrlHash(longUrlHash))
                .thenReturn(expectedEntity);

        var result = service.createNewOrGetExistingMapping(longUrl);

        assertNotNull(result);
        assertEquals(expectedEntity, result);
        Mockito.verifyNoInteractions(mappingIdProvider);
        Mockito.verify(mappingDao, Mockito.never()).persist(any());
    }

    @Test
    public void createNewOrGetExistingMapping_createAndReturnNewMappingIfSuchMappingNotExists() {
        var longUrl = "https://www.google.com";
        var longUrlHash = hashingService.generateMD5HexString(longUrl);

        Mockito.when(mappingIdProvider.provide()).thenReturn(100500L);
        Mockito.when(mappingDao.getByLongUrlHash(longUrlHash))
                .thenReturn(null);

        var entityArgumentCaptor = ArgumentCaptor.forClass(UrlMappingEntity.class);

        var result = service.createNewOrGetExistingMapping(longUrl);
        Mockito.verify(mappingDao).persist(entityArgumentCaptor.capture());

        assertEquals(entityArgumentCaptor.getValue(), result);

        assertEquals(100500L, result.getGeneratedId());
        assertEquals(longUrl, result.getSourceUrl());
        assertEquals(longUrlHash, result.getSourceUrlHash());
        assertNotNull(result.getCreatedAt());
        assertFalse(result.getAlias().isBlank());
    }
}