package io.liliac.shortener.dto;

import java.time.Instant;

public record AliasDataDto(
    String source,
    String domain,
    String alias,
    String aliasFullUrl,
    Instant createdAt
){}
