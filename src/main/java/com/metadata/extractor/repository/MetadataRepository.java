package com.metadata.extractor.repository;

import com.metadata.extractor.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, UUID> {
}
