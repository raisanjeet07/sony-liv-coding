package com.san.inv.sony_liv.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MultimediaDataJsonParser {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode parseToMultimediaMetadata(String str){
        try {
            return MAPPER.readTree(str);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
