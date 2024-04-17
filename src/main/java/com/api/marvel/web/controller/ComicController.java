package com.api.marvel.web.controller;

import com.api.marvel.dto.PageableDto;
import com.api.marvel.persistence.integration.marvel.dto.ComicDto;
import com.api.marvel.service.ComicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comics")
public class ComicController {

    private final ComicService comicService;

    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }

    @GetMapping
    public ResponseEntity<List<ComicDto>> findAll(
            @RequestParam(required = false) Long characterId,
            @RequestParam(defaultValue = "0") long offset,
            @RequestParam(defaultValue = "10") long limit)
    {
        PageableDto pageable = new PageableDto(offset, limit);
        return ResponseEntity.ok(this.comicService.findAll(pageable, characterId));
    }

    @GetMapping("/{comicId}")
    public ResponseEntity<ComicDto> findById(
            @PathVariable Long comicId)
    {
        return ResponseEntity.ok(this.comicService.findById(comicId));
    }
}
