package com.api.marvel.service;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CharacterService {

    List<CharacterDto> findAll(PageableDto pageable, String name, int[] comics, int[] series);

    CharacterDto.CharacterInfoDto findById(Long characterId);
}
