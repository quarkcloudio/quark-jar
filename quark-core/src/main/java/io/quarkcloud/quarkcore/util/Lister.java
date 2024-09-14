package io.quarkcloud.quarkcore.util;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Lister {

    // Convert generic list of Node objects to Tree
    public static <T> ArrayNode listToTree(List<T> list, String pk, String pid, String child, Long root) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode = mapper.valueToTree(list);
        ArrayNode treeList = parserToTree(arrayNode, pk, pid, child, root);

        return treeList;
    }

    // Convert ArrayNode to Tree
    public static ArrayNode parserToTree(ArrayNode list, String pk, String pid, String child, Long root) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode treeList = mapper.createArrayNode();

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
