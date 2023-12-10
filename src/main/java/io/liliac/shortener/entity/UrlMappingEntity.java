package io.liliac.shortener.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "urlMapping")
public class UrlMappingEntity {

    @Id
    private String alias;
    private Long generatedId;
    private String sourceUrlHash;
    private String sourceUrl;
    @CreationTimestamp
    private Instant createdAt;

    public UrlMappingEntity() {
    }

    private UrlMappingEntity(Builder builder) {
        this.alias = builder.alias;
        this.sourceUrl = builder.sourceUrl;
        this.sourceUrlHash = builder.sourceUrlHash;
        this.generatedId = builder.generatedId;
        this.createdAt = builder.createdAt;
    }

    public String getAlias() {
        return alias;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public Long getGeneratedId() {
        return generatedId;
    }

    public String getSourceUrlHash() {
        return sourceUrlHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlMappingEntity that = (UrlMappingEntity) o;
        return Objects.equals(alias, that.alias) && Objects.equals(sourceUrlHash, that.sourceUrlHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, alias);
    }

    public static class Builder {
        private String alias;
        private Long generatedId;
        private String sourceUrlHash;
        private String sourceUrl;
        private Instant createdAt;

        public Builder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Builder generatedId(Long id) {
            this.generatedId = id;
            return this;
        }

        public Builder sourceUrlHash(String hash) {
            this.sourceUrlHash = hash;
            return this;
        }

        public Builder sourceUrl(String url) {
            this.sourceUrl = url;
            return this;
        }

        public Builder createdNow() {
            this.createdAt = Instant.now();
            return this;
        }

        public UrlMappingEntity build() {
            return new UrlMappingEntity(this);
        }
    }
}
