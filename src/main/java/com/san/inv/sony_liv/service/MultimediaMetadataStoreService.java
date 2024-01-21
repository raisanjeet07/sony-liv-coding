package com.san.inv.sony_liv.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Component
public class MultimediaMetadataStoreService {

    @Autowired
    DataDiscoveryService dataDiscoveryService;

    private int removeLength = "https://run.mocky.io/v3/".length();
    List<JsonNode> metaData = new ArrayList<>();

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private final Lock lock = rwl.writeLock();
    public void updateData(String url){
        try{
            lock.lock();
            deleteSource(getSourceId(url));
            List<JsonNode> data = dataDiscoveryService.pullDataFrom(url);
            data.forEach(node -> ((ObjectNode)node).put("source", getSourceId(url)));
            metaData.addAll(data);
        }finally{
            lock.unlock();
        }
    }

    public void deleteSource(String source){
        try {
            lock.lock();
            metaData = metaData.stream().filter(node -> !node.get("source").asText().equals(source)).collect(Collectors.toList());
        }finally {
            lock.unlock();
        }
    }

    private String getSourceId(String url){
        return url.substring(removeLength);
    }

    public void updateData(List<String> url){
        url.forEach(this::updateData);
    }

    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> result = new ArrayList<>();
        metaData.forEach(metaData -> {
            result.add(getJsonNode(metaData));
        });
        return result;
    }

    private Map<String, Object> getJsonNode(JsonNode jNode){
        Map<String, Object> node = new HashMap<>();
        node.put("title", jNode.get("title"));
        node.put("release_date", jNode.get("release_date"));
        node.put("source", jNode.get("source"));
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList().get(0).getAuthority();
        JsonNode access_control = jNode.get("access_control");
        access_control.fieldNames().forEachRemaining(field -> {
            if(field.equalsIgnoreCase(authority)){
                access_control.get(field).forEach(entry -> {
                    node.put(entry.asText(), jNode.get(entry.asText()));
                });
            }
        });
        return node;
    }

    public void deleteSources(List<String> ingestUrls) {
        ingestUrls.forEach(url -> deleteSource(getSourceId(url)));
    }
}
