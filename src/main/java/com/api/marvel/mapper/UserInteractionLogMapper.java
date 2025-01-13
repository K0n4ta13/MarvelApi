package com.api.marvel.mapper;

import com.api.marvel.dto.UserInteractionLogDto;
import com.api.marvel.persistence.entity.UserInteractionLog;

public class UserInteractionLogMapper {

    public static UserInteractionLogDto toDto(UserInteractionLog userInteractionLog) {
        if (userInteractionLog == null) {
            return null;
        }

        return new UserInteractionLogDto(
                userInteractionLog.getId(),
                userInteractionLog.getUrl(),
                userInteractionLog.getHttpMethod(),
                userInteractionLog.getUsername(),
                userInteractionLog.getTimestamp(),
                userInteractionLog.getRemoteAddress());
    }
}
