package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.entity.Schedule;
import com.example.schedulemanagementapi.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Component
public class ScheduleValidator {

    private final ScheduleRepository scheduleRepository;

    public Schedule findScheduleByIdOrElseThrow(Long id) {

        return scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    public void checkPasswordMatch(Schedule schedule, String requestPassword) {
        // 데이터 유효성 검사
        // 비밀번호 NPE check
        if (requestPassword == null || requestPassword.isEmpty()) {
            // 400 BAD REQUEST
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is empty");
        }

        // 비밀번호 일치 확인
        if (!schedule.getPassword().equals(requestPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
    }

}
