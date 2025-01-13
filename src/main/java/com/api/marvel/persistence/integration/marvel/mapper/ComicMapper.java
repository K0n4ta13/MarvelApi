package com.api.marvel.persistence.integration.marvel.mapper;

import com.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.api.marvel.persistence.integration.marvel.dto.ThumbnailDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComicMapper {

    public static List<ComicDto> toDtoList(JsonNode rootNode) {
        ArrayNode resultsNode = getResultsNode(rootNode);

        List<ComicDto> comics = new ArrayList<>();
        resultsNode.elements().forEachRemaining(each -> {
            comics.add(ComicMapper.toDto(each));
        });

        return comics;

    }

    private static ComicDto toDto(JsonNode comicNode) {
//        if (Objects.isNull(comicNode)) {
//            throw new IllegalAccessException("json node can't be null");
//        }

        ThumbnailDto thumbnailDto = ThumbnailMapper.toDto(comicNode.get("thumbnail"));

        return new ComicDto(
                comicNode.get("id").asLong(),
                comicNode.get("title").asText(),
                comicNode.get("description").asText(),
                comicNode.get("modified").asText(),
                comicNode.get("resourceURI").asText(),
                thumbnailDto);
    }

    private static ArrayNode getResultsNode(JsonNode rootNode) {
//        if (Objects.isNull(rootNode)) {
//            throw new IllegalAccessException("json node can't be null");
//        }

        JsonNode dataNode = rootNode.get("data");
        return (ArrayNode) dataNode.get("results");
    }
}
