package com.api.marvel.persistence.integration.marvel.repository;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.mapper.CharacterMapper;
import com.api.marvel.persistence.integration.marvel.MarvelAPIConfig;
import com.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.api.marvel.service.HttpClientService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

@Repository
public class CharacterRepository {

    private final MarvelAPIConfig marvelAPIConfig;
    private final HttpClientService httpClientService;
    @Value("${integration.marvel.base-path}")
    private String basePath;
    private String characterPath;

    public CharacterRepository(MarvelAPIConfig marvelAPIConfig, HttpClientService httpClientService) {
        this.marvelAPIConfig = marvelAPIConfig;
        this.httpClientService = httpClientService;
    }

    @PostConstruct
    private void setPath() {
        this.characterPath = basePath.concat("/").concat("characters");
    }

    public List<CharacterDto> findAll(PageableDto pageable, String name, int[] comics, int[] series) {
        Map<String, String> marvelQueryParams = getQueryParamsForFindAll(pageable, name, comics, series);

        JsonNode response = this.httpClientService.doGet(characterPath, marvelQueryParams, JsonNode.class);

        return CharacterMapper.toDtoList(response);
    }

    private Map<String, String> getQueryParamsForFindAll(PageableDto pageable, String name, int[] comics, int[] series) {
        Map<String, String> marvelQueryParams = this.marvelAPIConfig.getAuthenticationQueryParams();

        marvelQueryParams.put("offset", Long.toString(pageable.offset()));
        marvelQueryParams.put("limit", Long.toString(pageable.limit()));

        if (StringUtils.hasText(name)) {
            marvelQueryParams.put("name", name);
        }

        if (Objects.nonNull(comics)) {
            String comicsAsString = this.joinIntArray(comics);
            marvelQueryParams.put("comics", comicsAsString);
        }

        if (Objects.nonNull(series)) {
            String seriesAsString = this.joinIntArray(series);
            marvelQueryParams.put("series", seriesAsString);
        }

        return marvelQueryParams;
    }

    private String joinIntArray(int[] array) {
        List<String> stringArray = IntStream.of(array).boxed()
                .map(Object::toString)
                .toList();

        return String.join(",", stringArray);
    }

    public CharacterDto.CharacterInfoDto findById(Long characterId) {
        Map<String, String> marvelQueryParams = this.marvelAPIConfig.getAuthenticationQueryParams();

        String finalUrl = characterPath.concat("/").concat(Long.toString(characterId));

        JsonNode response = this.httpClientService.doGet(finalUrl, marvelQueryParams, JsonNode.class);

        return CharacterMapper.toInfoDtoList(response).getFirst();
    }
}
