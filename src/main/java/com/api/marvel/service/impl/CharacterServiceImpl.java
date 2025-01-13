package com.api.marvel.service.impl;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.api.marvel.persistence.integration.marvel.repository.CharacterRepository;
import com.api.marvel.service.CharacterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public List<CharacterDto> findAll(PageableDto pageable, String name, int[] comics, int[] series) {
        return this.characterRepository.findAll(pageable, name, comics, series);
    }

    @Override
    public CharacterDto.CharacterInfoDto findById(Long characterId) {
        return this.characterRepository.findById(characterId);
    }
}
