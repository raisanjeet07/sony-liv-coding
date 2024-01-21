package com.san.inv.sony_liv.controller;

import com.san.inv.sony_liv.service.MultimediaMetadataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/data")
public class MultiMediaDataReadController {

    @Autowired
    MultimediaMetadataStoreService multimediaMetadataStoreService;

    @GetMapping
    public List<Map<String, Object>> getData(){
        return multimediaMetadataStoreService.getData();
    }
}
