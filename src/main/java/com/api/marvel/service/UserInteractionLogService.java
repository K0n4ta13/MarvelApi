package com.api.marvel.service;

import com.api.marvel.dto.UserInteractionLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserInteractionLogService {

    Page<UserInteractionLogDto> findAll(Pageable pageable);
    Page<UserInteractionLogDto> findByUsername(Pageable pageable, String username);
}
