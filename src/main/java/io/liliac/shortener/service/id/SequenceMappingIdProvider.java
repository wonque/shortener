package io.liliac.shortener.service.id;

import io.liliac.shortener.dao.IdentifierDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SequenceMappingIdProvider implements MappingIdProvider {

    private IdentifierDao identifierDao;

    @Inject
    public SequenceMappingIdProvider(IdentifierDao identifierDao) {
        this.identifierDao = identifierDao;
    }

    @Override
    public Long provide() {
        return identifierDao.nextSequenceValue();
    }
}
