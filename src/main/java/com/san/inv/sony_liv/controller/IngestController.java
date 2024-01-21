package com.san.inv.sony_liv.controller;

import com.san.inv.sony_liv.dto.IngestRequest;
import com.san.inv.sony_liv.service.MultimediaMetadataStoreService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ingest")
@PreAuthorize("hasAuthority('ADMIN')")
public class IngestController {

    @Autowired
    MultimediaMetadataStoreService multimediaMetadataStoreService;

    @PostConstruct
    public void postControllerConstruct(){
        System.out.println("Constructor Created");
    }

    @GetMapping
    public String healthCheck(){
        return "Healthy";
    }

    @PostMapping
    public String discoverData(@RequestBody IngestRequest request){
        multimediaMetadataStoreService.updateData(request.getIngestUrls());
        return "Pulled";
    }

    @DeleteMapping
    public String deleteData(@RequestBody IngestRequest request){
        multimediaMetadataStoreService.deleteSources(request.getIngestUrls());
        return "Pulled";
    }
}
