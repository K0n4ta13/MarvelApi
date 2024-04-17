package com.api.marvel.persistence.integration.marvel.repository;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.MarvelAPIConfig;
import com.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.api.marvel.persistence.integration.marvel.mapper.ComicMapper;
import com.api.marvel.service.HttpClientService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ComicRepository {

    private final MarvelAPIConfig marvelAPIConfig;
    private final HttpClientService httpClientService;
    @Value("${integration.marvel.base-path}")
    private String basePath;
    private String comicPath;

    public ComicRepository(MarvelAPIConfig marvelAPIConfig, HttpClientService httpClientService) {
        this.marvelAPIConfig = marvelAPIConfig;
        this.httpClientService = httpClientService;
    }

    @PostConstruct
    private void setPath() {
        this.comicPath = basePath.concat("/").concat("comics");
    }

    public List<ComicDto> findAll(PageableDto pageable, Long characterId) {
        Map<String, String> marvelQueryParams = getQueryParamsForFindAll(pageable, characterId);

        JsonNode response = this.httpClientService.doGet(comicPath, marvelQueryParams, JsonNode.class);

        return ComicMapper.toDtoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(PageableDto pageable, Long characterId) {
        Map<String, String> marvelQueryParams = this.marvelAPIConfig.getAuthenticationQueryParams();

        marvelQueryParams.put("offset", Long.toString(pageable.offset()));
        marvelQueryParams.put("limit", Long.toString(pageable.limit()));

        if (Objects.nonNull(characterId) && characterId > 0) {
            marvelQueryParams.put("characters", Long.toString(characterId));
        }

        return marvelQueryParams;
    }

    public ComicDto findById(Long comicId) {
        Map<String, String> marvelQueryParams = this.marvelAPIConfig.getAuthenticationQueryParams();

        String finalUrl = this.comicPath.concat("/").concat(Long.toString(comicId));

        JsonNode response = this.httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);

        return ComicMapper.toDtoList(response).get(0);
    }
}
