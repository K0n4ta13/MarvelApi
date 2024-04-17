package com.api.marvel.service.impl;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.api.marvel.persistence.integration.marvel.repository.ComicRepository;
import com.api.marvel.service.ComicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComicServiceImpl implements ComicService {

    private final ComicRepository comicRepository;

    public ComicServiceImpl(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    @Override
    public List<ComicDto> findAll(PageableDto pageable, Long characterId) {
        return this.comicRepository.findAll(pageable, characterId);
    }

    @Override
    public ComicDto findById(Long comicId) {
        return this.comicRepository.findById(comicId);
    }
}
