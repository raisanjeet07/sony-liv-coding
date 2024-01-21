package com.san.inv.sony_liv.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface DataDiscoveryService {

    List<JsonNode> pullDataFrom(String uri);

    List<JsonNode> pullDataFrom(List<String> uri);
}
