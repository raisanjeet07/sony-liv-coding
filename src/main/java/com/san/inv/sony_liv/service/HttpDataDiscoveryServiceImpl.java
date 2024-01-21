package com.san.inv.sony_liv.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.san.inv.sony_liv.util.MultimediaDataJsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class HttpDataDiscoveryServiceImpl implements DataDiscoveryService{

    @Override
    public List<JsonNode> pullDataFrom(String uri) {
        String data = pullData(uri);
        JsonNode json = MultimediaDataJsonParser.parseToMultimediaMetadata(data);
        List<JsonNode> nodes = new ArrayList<>();
        json.forEach(nodes::add);
        return nodes;
    }

    @Override
    public List<JsonNode> pullDataFrom(List<String> uri) {
        List<JsonNode> nodes = new ArrayList<>();
        uri.forEach(url -> nodes.addAll(pullDataFrom(url)));
        return nodes;
    }

    private String pullData(String url){
        try {
            HttpRequest request = buildHttpRequest(url);
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest buildHttpRequest(String url) throws URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .GET()
                .build();
        return request;
    }
}
