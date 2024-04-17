package com.api.marvel.persistence.repository;

import com.api.marvel.persistence.entity.UserInteractionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInteractionLogRepository extends JpaRepository<UserInteractionLog, Long> {
}
