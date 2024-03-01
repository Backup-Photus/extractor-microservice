package com.metadata.extractor.entity;


import jakarta.persistence.*;
import lombok.*;


import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadingMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    UUID userId;

    @Column(nullable = false,unique = true)
    UUID photoId;

    @Column(nullable = false)
    MetadataState loaded;

    @Column(nullable = false,unique = true)
    String path;

    public  enum MetadataState{
        NOT_STARTED,
        FAILED,
        SUCCESS,
    }

}
