package com.api.marvel.persistence.integration.marvel.mapper;

import com.api.marvel.persistence.integration.marvel.dto.ThumbnailDto;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Objects;

public class ThumbnailMapper {

    public static ThumbnailDto toDto(JsonNode thumbnailNode) {
//        if (Objects.isNull(thumbnailNode)) {
//            throw new IllegalAccessException("json node can't be null");
//        }

        return new ThumbnailDto(
                thumbnailNode.get("path").asText(),
                thumbnailNode.get("extension").asText());
    }
}
