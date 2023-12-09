package io.liliac.shortener.service;

import io.liliac.shortener.dao.UrlMappingDao;
import io.liliac.shortener.entity.UrlMappingEntity;
import io.liliac.shortener.exception.MappingNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RedirectServiceTest {

    private UrlMappingDao mappingDao = Mockito.mock(UrlMappingDao.class);
    private RedirectService service = new RedirectService(mappingDao);

    @Test
    public void getLocation_throwExceptionIfMappingNotFoundForGivenShortUrl() {
        var url = "test";
        Mockito.when(mappingDao.getByShortUrl(url)).thenReturn(null);
        assertThrows(MappingNotFoundException.class, () -> service.getLocation(url));
    }

    @Test
    public void getLocation_returnLocationStringWithShortUrlIfMappingExists() {
        var shortUrlHash = "test";
        var sourceUrl = "http://goto.this";
        Mockito.when(mappingDao.getByShortUrl(shortUrlHash))
                .thenReturn(UrlMappingEntity.builder().sourceUrl(sourceUrl).build());
        var result = service.getLocation(shortUrlHash);
        assertEquals(sourceUrl, result);
    }
}