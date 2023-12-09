package io.liliac.shortener.dto;

import java.io.Serializable;

public record ShortenUrlRequest(
        String url
) implements Serializable {
}
