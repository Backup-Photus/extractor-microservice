package com.metadata.extractor.controller;

import com.metadata.extractor.dto.LoadingMetadataDTO;
import com.metadata.extractor.service.MetadataService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metadata")
@AllArgsConstructor
public class MetadataController {

    private MetadataService service;

    @PostMapping
    private ResponseEntity<HttpStatus> metadata(@Valid @RequestBody LoadingMetadataDTO data){
        service.addEntryForMetadata(data.toLoadingMetadata());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    private ResponseEntity<HttpStatus> ping(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
