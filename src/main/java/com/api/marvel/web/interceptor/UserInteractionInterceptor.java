package com.api.marvel.web.interceptor;

import com.api.marvel.exception.ApiErrorException;
import com.api.marvel.persistence.entity.UserInteractionLog;
import com.api.marvel.persistence.repository.UserInteractionLogRepository;
import com.api.marvel.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class UserInteractionInterceptor implements HandlerInterceptor {

    @Value("$integration.marvel.base-path")
    private String basePath;

    private final UserInteractionLogRepository userLogRepository;
    private final AuthenticationService authenticationService;

    @Lazy
    public UserInteractionInterceptor(UserInteractionLogRepository userLogRepository, AuthenticationService authenticationService) {
        this.userLogRepository = userLogRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/characters") || requestURI.startsWith("/comics")) {
            UserInteractionLog userLog = new UserInteractionLog();

            userLog.setUrl(request.getRequestURL().toString());
            userLog.setHttpMethod(request.getMethod());
            UserDetails user = this.authenticationService.getUserLoggedIn();
            userLog.setUsername(user.getUsername());
            userLog.setTimestamp(LocalDateTime.now());
            userLog.setRemoteAddress(request.getRemoteAddr());

            try {
                this.userLogRepository.save(userLog);
                return true;
            } catch (Exception ex) {
                throw new ApiErrorException("Cannot save the log correctly");
            }
        }

        return true;
    }
}
