package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleResponseDto;
import com.example.schedulemanagementapi.entity.Schedule;
import com.example.schedulemanagementapi.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

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
            foundSchedules = scheduleRepository.findByNameContainingOrderByUpdatedAtDesc(name);
        } else {
            foundSchedules = scheduleRepository.findAllByOrderByUpdatedAtDesc();
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

}
