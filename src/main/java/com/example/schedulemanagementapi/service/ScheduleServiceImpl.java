package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.ScheduleDetailResponseDto;
import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleSummaryResponseDto;
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
    private final ScheduleValidator scheduleValidator;

    @Transactional
    @Override
    public ScheduleSummaryResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        // 필수값 체크
        if (requestDto.getTitle() == null || requestDto.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }

        if (requestDto.getContents() == null || requestDto.getContents().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is required");
        }

        if (requestDto.getName() == null || requestDto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        // 길이 제한
        if (requestDto.getTitle().length() > 30) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The length of the title is exceeded. Please write within 30 characters.");
        }

        if (requestDto.getContents().length() > 200) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The length of the content is exceeded. Please write within 200 characters.");
        }

        // 요청 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(requestDto.getTitle(), requestDto.getContents(), requestDto.getName(), requestDto.getPassword());

        // 받아온 매핑된 Schedule 객체를 DTO로 생성하여 반환
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleSummaryResponseDto(savedSchedule);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleSummaryResponseDto> findAllSchedules(
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
                .map(ScheduleSummaryResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ScheduleDetailResponseDto findScheduleById(Long id) {

        return new ScheduleDetailResponseDto(scheduleValidator.findScheduleByIdOrElseThrow(id));
    }

    @Transactional
    @Override
    public ScheduleSummaryResponseDto updateScheduleTitleAndName(
            Long id,
            ScheduleRequestDto requestDto
    ) {

        Schedule schedule = scheduleValidator.findScheduleByIdOrElseThrow(id);

        scheduleValidator.checkPasswordMatch(schedule, requestDto.getPassword());

        // Dirty Checking
        // @Transactional이 있다면 setter만 호출해도 업데이트가 반영된다.
        if (requestDto.getTitle() != null && !requestDto.getTitle().isBlank()) schedule.updateTitle(requestDto.getTitle());
        if (requestDto.getName() != null && !requestDto.getName().isBlank()) schedule.updateName(requestDto.getName());

        return new ScheduleSummaryResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {

        Schedule schedule = scheduleValidator.findScheduleByIdOrElseThrow(id);

        scheduleValidator.checkPasswordMatch(schedule, requestDto.getPassword());

        scheduleRepository.deleteById(id);
    }

}
