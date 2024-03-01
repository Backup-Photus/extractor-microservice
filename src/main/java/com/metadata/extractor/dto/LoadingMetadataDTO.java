package com.metadata.extractor.dto;

import com.metadata.extractor.entity.LoadingMetadata;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record LoadingMetadataDTO(

        @NotBlank( message = "userId cannot be blank")
        String userId,

        @NotBlank(message = "photoId cannot be blank")
        String photoId,

        @NotBlank(message = "path to photo cannot be blank")
        String path
) {

    public LoadingMetadata toLoadingMetadata(){
        LoadingMetadata.LoadingMetadataBuilder builder = LoadingMetadata.builder();
        return builder.
                userId(UUID.fromString(userId))
                .photoId(UUID.fromString(photoId))
                .path(path)
                .build();
    }

    @Override
    public String toString() {
        return "LoadingMetadataDTO{" +
                "userId='" + userId + '\'' +
                ", photoId='" + photoId + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
