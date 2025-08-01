package com.example.schedulemanagementapi.global.validation;

import com.example.schedulemanagementapi.global.security.PasswordHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class PasswordValidator {

    public <T extends PasswordHolder> void validatePassword(T entity, String requestPassword) {
        if (!StringUtils.hasText(requestPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is empty");
        }

        if (!entity.getPassword().equals(requestPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
    }
}
