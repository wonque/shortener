package io.liliac.shortener.dao;

import io.liliac.shortener.entity.UrlMappingEntity;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@Transactional
class UrlMappingDaoTest {

    @Inject
    private UrlMappingDao dao;

    @Test
    public void getByLongUrlHash_shouldReturnMappingEntityIfSuchHashExists() {
        var sourceUrl = "SomeLongUrl";
        var hash = DigestUtils.md5Hex(sourceUrl);
        var entity = UrlMappingEntity.builder()
                .sourceUrl(sourceUrl)
                .alias("Some short url")
                .generatedId(100500L)
                .createdNow()
                .sourceUrlHash(DigestUtils.md5Hex(sourceUrl)).build();
        dao.persist(entity);

        var result = dao.getByLongUrlHash(hash);
        assertEquals(entity, result);
    }
}