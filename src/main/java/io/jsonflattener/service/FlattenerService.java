package io.jsonflattener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;

public class FlattenerService {
    private final ObjectMapper mapper;

    public FlattenerService(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    public JsonNode flattenJson(String jsonStr) throws Exception {
        JsonNode response;
        try {
            JsonNode input = mapper.readValue(jsonStr, JsonNode.class);
            response = flattenJson(input, mapper.createObjectNode(), "");
        } catch (JsonProcessingException e) {
            throw new Exception("Not a valid json");
        }
        return response;
    }

    private JsonNode flattenJson(JsonNode input, ObjectNode response, String jsonKey) {
        input.fieldNames().forEachRemaining(key -> {
            String field = "".equals(jsonKey) ? key : jsonKey + "." + key;
            if (input.get(key).isArray() && input.get(key).size() >= 1) {
                for (int iterator = 0; iterator < input.get(key).size(); iterator++) {
                    flattenJson(input.get(key).get(iterator), response, field + "[" + iterator + "]");
                }
            } else if (input.get(key).isObject()) {
                flattenJson(input.get(key), response, field);
            } else {
                response.set(field, input.get(key));
            }
        });
        return response;
    }

    public JsonNode unflattenJson(String jsonStr) throws Exception {
        JsonNode response;
        try {
            JsonNode input = mapper.readValue(jsonStr, JsonNode.class);
            response = unflattenJson(input, mapper.createObjectNode());
        } catch (JsonProcessingException e) {
            throw new Exception("Not a valid json");
        }
        return response;
    }

    private JsonNode unflattenJson(JsonNode input, ObjectNode objectNode) {
        int keyIterator = 0;
        for (Iterator<String> it = input.fieldNames(); it.hasNext(); ) {
            String key = it.next();
            String[] keyArray = key.split("\\.");
            processJsonObject(keyArray, keyIterator, objectNode, input, key);
        }
        return objectNode;
    }

    private void processJsonObject(String[] keyArray, int keyIterator, ObjectNode objectNode, JsonNode input, String key) {
        if (!keyArray[keyIterator].contains("[")) {
            if (!objectNode.has(keyArray[keyIterator])) {
                objectNode.set(keyArray[keyIterator], mapper.createObjectNode());
            }
            if (keyArray.length > keyIterator + 1)
                processJsonObject(keyArray, keyIterator + 1, (ObjectNode) objectNode.get(keyArray[keyIterator]), input, key);
            else objectNode.set(keyArray[keyIterator], input.get(key));
        } else {
            String arrayKey = keyArray[keyIterator].split("\\[")[0].trim();
            if (!objectNode.has(arrayKey)) {
                ArrayNode value = mapper.createArrayNode();
                objectNode.set(arrayKey, value);
            }
            if (keyArray.length > keyIterator + 1)
                processJsonArray(keyArray, keyIterator + 1, (ArrayNode) objectNode.get(arrayKey), input, key);
            else objectNode.set(arrayKey, input.get(key));
        }
    }

    private void processJsonArray(String[] keyArray, int keyIterator, ArrayNode arrayNode, JsonNode input, String key) {
        if (!keyArray[keyIterator].contains("[")) {
            if (keyArray.length > keyIterator + 1)
                processJsonObject(keyArray, keyIterator + 1, (ObjectNode) arrayNode.get(keyArray[keyIterator]), input, key);
            else {
                ObjectNode value = mapper.createObjectNode();
                if (arrayNode.size() > 0) {
                    value = (ObjectNode) arrayNode.get(0);
                }
                value.set(keyArray[keyIterator], input.get(key));
                if (0 < arrayNode.size())
                    arrayNode.set(0, value);
                else arrayNode.add(value);
            }
        } else {
            String arrayKey = keyArray[keyIterator].split("\\[")[0].trim();
            ObjectNode currentNode = (ObjectNode) arrayNode.get(0);
            if (currentNode == null) {
                ObjectNode value = mapper.createObjectNode();
                value.set(arrayKey, mapper.createArrayNode());
                arrayNode.add(value);
                currentNode = (ObjectNode) arrayNode.get(arrayNode.size() - 1);
            }
            if (keyArray.length > keyIterator + 1)
                processJsonArray(keyArray, keyIterator + 1, (ArrayNode) currentNode.get(arrayKey), input, key);
            else arrayNode.add(input.get(key));
        }
    }
}
