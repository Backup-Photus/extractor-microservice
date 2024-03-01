package com.metadata.extractor.repository;

import com.metadata.extractor.entity.LoadingMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoadingMetadataRepository extends JpaRepository<LoadingMetadata, UUID> {
    Optional<LoadingMetadata> findByIdGreaterThan(UUID id);
}
