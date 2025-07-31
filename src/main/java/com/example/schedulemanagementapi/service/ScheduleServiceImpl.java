package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleResponseDto;
import com.example.schedulemanagementapi.entity.Schedule;
import com.example.schedulemanagementapi.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        // 요청 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), requestDto.getName(), requestDto.getPassword());

        // 받아온 매핑된 Schedule 객체를 DTO로 생성하여 반환
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponseDto> findAllSchedules(
            String name
    ) {

        // NPE 체크
        /*
        isEmpty() VS isBlank()
        isEmpty(): " " <- false
        isBlank(): " " <- true
         */
        List<Schedule> foundSchedules;
        if (name != null && !name.isBlank()) {
            foundSchedules = scheduleRepository.findByNameContainingOrderByModifiedAtDesc(name);
        } else {
            foundSchedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        }

        return foundSchedules.stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id)));
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateScheduleTitleAndName(
            Long id,
            ScheduleRequestDto requestDto
    ) {

        // 데이터 유효성 검사
        // 비밀번호 NPE check
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            // 400 BAD REQUEST
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is empty");
        }

        // 해당 ID의 일정이 있는지 확인
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));

        // 비밀번호 일치 확인
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        // Dirty Checking
        // @Transactional이 있다면 setter만 호출해도 업데이트가 반영된다.
        if (requestDto.getTitle() != null && !requestDto.getTitle().isBlank()) schedule.updateTitle(requestDto.getTitle());
        if (requestDto.getName() != null && !requestDto.getName().isBlank()) schedule.updateName(requestDto.getName());

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {
        // 데이터 유효성 검사
        // 비밀번호 NPE check
        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            // 400 BAD REQUEST
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is empty");
        }

        // 해당 ID의 일정이 있는지 확인
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));

        // 비밀번호 일치 확인
        if (!schedule.getPassword().equals(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        scheduleRepository.deleteById(id);
    }

}
