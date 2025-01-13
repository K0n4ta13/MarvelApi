package com.api.marvel.web.controller;

import com.api.marvel.dto.UserInteractionLogDto;
import com.api.marvel.service.UserInteractionLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users-interactions")
public class UserInteractionLogController {

    private final UserInteractionLogService userInteractionLogService;

    public UserInteractionLogController(UserInteractionLogService userInteractionLogService) {
        this.userInteractionLogService = userInteractionLogService;
    }


    @PreAuthorize("hasAuthority('user-interaction:read-all')")
    @GetMapping
    public ResponseEntity<Page<UserInteractionLogDto>> findAll(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit)
    {
        Pageable pageable = buildPageable(offset, limit);

        return ResponseEntity.ok(this.userInteractionLogService.findAll(pageable));
    }

    private static Pageable buildPageable(int offset, int limit) {
        Pageable pageable = null;

        if (offset < 0) {
            throw new IllegalArgumentException("offset must be greater than 0");
        }

        if (limit < 0) {
            throw new IllegalArgumentException("limit must be greater than 0");
        }

        if (offset == 0) {
            pageable = PageRequest.of(0, limit);
        } else {
            pageable = PageRequest.of(offset / limit, limit);
        }
        return pageable;
    }

    @PreAuthorize("hasAuthority('user-interaction:read-by-username') || " +
            "@interactionLogValidator.validate(#username) &&" +
            "hasAuthority('user-interaction:read-my-interactions')")
    @GetMapping("/{username}")
    public ResponseEntity<Page<UserInteractionLogDto>> findByUsername(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit)
    {
        Pageable pageable = buildPageable(offset, limit);

        return ResponseEntity.ok(this.userInteractionLogService.findByUsername(pageable, username));
    }
}
