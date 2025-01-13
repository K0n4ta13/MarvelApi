package com.api.marvel.service;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.dto.ComicDto;

import java.util.List;

public interface ComicService {

    List<ComicDto> findAll(PageableDto pageable, Long characterId);

    ComicDto findById(Long comicId);
}
