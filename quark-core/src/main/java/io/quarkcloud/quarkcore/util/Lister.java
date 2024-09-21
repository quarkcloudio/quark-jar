package io.quarkcloud.quarkcore.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Lister {

    // Convert generic list of Node objects to Tree
    public static <T> ArrayNode listToTree(List<T> list, String pk, String pid, String child, Long root) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode = mapper.valueToTree(list);
        ArrayNode treeList = parserToTree(arrayNode, pk, pid, child, root);

        return treeList;
    }

    // Convert ArrayNode to Tree
    public static ArrayNode parserToTree(ArrayNode list, String pk, String pid, String child, Long root) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode treeList = mapper.createArrayNode();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
        for (JsonNode node : list) {
            if (node.get(pid).asLong() == root) {
                ArrayNode childNode= parserToTree(list, pk, pid, child, node.get(pk).asLong());
                if (childNode.size() > 0) {
                    ((ObjectNode) node).set(child, childNode);
                }
                treeList.add(node);
            }
        }

        return treeList;
    }
}
