package com.example.schedulemanagementapi.global.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EntityFinder {

    public <T, ID> T findByIdOrElseThrow(JpaRepository<T, ID> repository, ID id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }
}
