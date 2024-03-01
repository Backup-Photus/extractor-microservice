package com.metadata.extractor.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metadata.extractor.entity.LoadingMetadata;
import com.metadata.extractor.entity.Metadata;
import com.metadata.extractor.repository.LoadingMetadataRepository;
import com.metadata.extractor.repository.MetadataRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.rmi.NoSuchObjectException;
import java.util.UUID;

@AllArgsConstructor
public class MetadataExtractorService extends Thread {
    private LoadingMetadataRepository loadingMetadataRepository;
    private MetadataRepository metadataRepository;

    public UUID uuid;

    @Override
    public void run() {
        try {
            LoadingMetadata loadingMetadata =  MetadataService.unwrap(loadingMetadataRepository.findById(uuid));
            Metadata data = extractMetadata(Path.of(loadingMetadata.getPath()));
            if(data !=null){
                 data=data.toBuilder()
                        .userId(loadingMetadata.getUserId())
                        .photoId(loadingMetadata.getPhotoId()).build();
                metadataRepository.save(data);
                loadingMetadata.setLoaded(LoadingMetadata.MetadataState.SUCCESS);
                System.out.println("Metadata saved successfully");
            }else {
                System.out.println("Metadata failed");
                loadingMetadata.setLoaded(LoadingMetadata.MetadataState.FAILED);
            }
            loadingMetadataRepository.save(loadingMetadata);
        } catch (NoSuchObjectException e) {
            throw new RuntimeException(e);
        }
    }

    private Metadata extractMetadata(Path path){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("exiftool", "-json", path.toString());
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) output.append(line);

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("ExifTool process exited with error code " + exitCode);
                return null;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            Object json = objectMapper.readValue(output.toString(), Object.class);

            String jsonString = objectMapper.writeValueAsString(json);
            Metadata[] data = objectMapper.readValue(jsonString,Metadata[].class);
            System.out.println(data.length);
            System.out.println(data[0]);
            return data[0];
        } catch (IOException | InterruptedException e) {
            return  null;
        }
    }
}
