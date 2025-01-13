package com.api.marvel.persistence.integration.marvel.mapper;

import com.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.api.marvel.persistence.integration.marvel.dto.ThumbnailDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharacterMapper {

    public static List<CharacterDto> toDtoList(JsonNode rootNode) {
        ArrayNode resultsNode = getResultsNode(rootNode);

        List<CharacterDto> characters = new ArrayList<>();

        resultsNode.elements()
                .forEachRemaining(each -> characters.add(CharacterMapper.toDto(each)));

        return characters;
    }

    private static CharacterDto toDto(JsonNode characterNode) {
//        if (Objects.isNull(characterNode)) {
//            throw new IllegalAccessException("json node can't be null");
//        }

        return new CharacterDto(
                characterNode.get("id").asLong(),
                characterNode.get("name").asText(),
                characterNode.get("description").asText(),
                characterNode.get("modified").asText(),
                characterNode.get("resourceURI").asText());
    }

    private static ArrayNode getResultsNode(JsonNode rootNode) {
//        if (Objects.isNull(rootNode)) {
//            throw new IllegalAccessException("json node can't be null");
//        }

        JsonNode dataNode = rootNode.get("data");
        return (ArrayNode) dataNode.get("results");
    }

    public static List<CharacterDto.CharacterInfoDto> toInfoDtoList(JsonNode response) {
        ArrayNode resultsNode = getResultsNode(response);

        List<CharacterDto.CharacterInfoDto> characters = new ArrayList<>();

        resultsNode.elements().forEachRemaining(each -> {
            characters.add(CharacterMapper.toInfoDto(each));
        });

        return characters;
    }

    private static CharacterDto.CharacterInfoDto toInfoDto(JsonNode characterNode) {
//        if (Objects.isNull(characterNode)) {
//            throw new IllegalAccessException("json node can't be null");
//        }

        JsonNode thumbnailNode = characterNode.get("thumbnail");
        ThumbnailDto thumbnailDto = ThumbnailMapper.toDto(thumbnailNode);

        String image = thumbnailDto.path().concat(".").concat(thumbnailDto.extension());


        return new CharacterDto.CharacterInfoDto(
                image,
                characterNode.get("description").asText());
    }
}
