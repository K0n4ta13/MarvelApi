package com.api.marvel.web.controller;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.dto.CharacterDto;
import com.api.marvel.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public ResponseEntity<List<CharacterDto>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) int[] comics,
            @RequestParam(required = false) int[] series,
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "10") long limit)
    {
        PageableDto pageable = new PageableDto(offset, limit);
        return ResponseEntity.ok(this.characterService.findAll(pageable, name, comics, series));
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterDto.CharacterInfoDto> findById(@PathVariable Long characterId) {
        return ResponseEntity.ok(this.characterService.findById(characterId));
    }


}
