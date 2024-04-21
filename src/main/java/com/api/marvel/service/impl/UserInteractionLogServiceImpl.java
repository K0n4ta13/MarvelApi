package com.api.marvel.service.impl;

import com.api.marvel.dto.UserInteractionLogDto;
import com.api.marvel.mapper.UserInteractionLogMapper;
import com.api.marvel.persistence.repository.UserInteractionLogRepository;
import com.api.marvel.service.UserInteractionLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserInteractionLogServiceImpl implements UserInteractionLogService {

    private final UserInteractionLogRepository userInteractionLogRepository;

    public UserInteractionLogServiceImpl(UserInteractionLogRepository userInteractionLogRepository) {
        this.userInteractionLogRepository = userInteractionLogRepository;
    }

    @Override
    public Page<UserInteractionLogDto> findAll(Pageable pageable) {
        return this.userInteractionLogRepository.findAll(pageable)
                .map(UserInteractionLogMapper::toDto);
    }

    @Override
    public Page<UserInteractionLogDto> findByUsername(Pageable pageable, String username) {
        return this.userInteractionLogRepository.findByUsername(pageable, username)
                .map(UserInteractionLogMapper::toDto);
    }
}
