package io.quarkcloud.quarkcore.util;

import java.lang.reflect.Field;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Lister {

    // Convert generic list of Node objects to ArrayNode
    public static <T> ArrayNode listToArrayNode(List<T> list, ObjectMapper mapper) throws IllegalAccessException {
        ArrayNode arrayNode = mapper.createArrayNode();
        for (T obj : list) {
            ObjectNode jsonNode = mapper.createObjectNode();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getType() == int.class) {
                    jsonNode.put(field.getName(), (int) field.get(obj));
                } else if (field.getType() == String.class) {
                    jsonNode.put(field.getName(), (String) field.get(obj));
                }
            }
            arrayNode.add(jsonNode);
        }
        return arrayNode;
    }

    // Convert generic list of Node objects to Tree
    public static <T> ArrayNode listToTree(List<T> list, String pk, String pid, String child, int root) throws IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        // Convert list to ArrayNode
        arrayNode = listToArrayNode(list, mapper);

        // Convert ArrayNode to Tree
        ArrayNode treeList = parserToTree(arrayNode, pk, pid, child, root);

        return treeList;
    }

    // Convert ArrayNode to Tree
    public static ArrayNode parserToTree(ArrayNode list, String pk, String pid, String child, int root) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode treeList = mapper.createArrayNode();

        for (JsonNode node : list) {
            if (node.get(pid).asInt() == root) {
                ((ObjectNode) node).set(child, parserToTree(list, pk, pid, child, node.get(pk).asInt()));
                treeList.add(node);
            }
        }

        return treeList;
    }
}
