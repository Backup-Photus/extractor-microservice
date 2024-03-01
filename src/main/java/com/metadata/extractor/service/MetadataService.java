package com.metadata.extractor.service;

import com.metadata.extractor.entity.LoadingMetadata;
import com.metadata.extractor.repository.LoadingMetadataRepository;
import com.metadata.extractor.repository.MetadataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MetadataService {
    private LoadingMetadataRepository loadingMetadataRepository;
    private MetadataRepository metadataRepository;

    public void addEntryForMetadata(LoadingMetadata metadata) {
        metadata.setLoaded(LoadingMetadata.MetadataState.NOT_STARTED);
        metadata = loadingMetadataRepository.save(metadata);
        fireExtractor(metadata.getId());

    }

    private void fireExtractor(UUID uuid){
        MetadataExtractorService service = new MetadataExtractorService(loadingMetadataRepository,metadataRepository,uuid);
        service.start();
    }
    public static LoadingMetadata unwrap(Optional<LoadingMetadata> metadata) throws NoSuchObjectException {
        if(metadata.isPresent()) return metadata.get();
        throw new NoSuchObjectException("No LoadingMetadata Present");
    }
}
