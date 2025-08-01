package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.ScheduleDetailResponseDto;
import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleSummaryResponseDto;
import com.example.schedulemanagementapi.entity.Schedule;
import com.example.schedulemanagementapi.global.util.EntityFinder;
import com.example.schedulemanagementapi.global.util.StringHelper;
import com.example.schedulemanagementapi.global.validation.PasswordValidator;
import com.example.schedulemanagementapi.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EntityFinder entityFinder;
    private final PasswordValidator passwordValidator;

    @Transactional
    @Override
    public ScheduleSummaryResponseDto saveSchedule(ScheduleRequestDto requestDto) {

        // 필수값 체크
        if (!StringUtils.hasText(requestDto.getTitle())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title is required");
        }

        if (!StringUtils.hasText(requestDto.getContents())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content is required");
        }

        if (!StringUtils.hasText(requestDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
        }

        if (!StringUtils.hasText(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        // 길이 제한
        if (!StringHelper.isLengthBetween(requestDto.getTitle(), 1, 30)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The length of the title is exceeded. Please write within 30 characters.");
        }

        if (!StringHelper.isLengthBetween(requestDto.getContents(), 1, 200)) {
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
        if (StringUtils.hasText(name)) {
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

        return new ScheduleDetailResponseDto(entityFinder.findByIdOrElseThrow(scheduleRepository, id));
    }

    @Transactional
    @Override
    public ScheduleSummaryResponseDto updateScheduleTitleAndName(
            Long id,
            ScheduleRequestDto requestDto
    ) {

        Schedule schedule = entityFinder.findByIdOrElseThrow(scheduleRepository, id);

        passwordValidator.validatePassword(schedule, requestDto.getPassword());

        // Dirty Checking
        // @Transactional이 있다면 setter만 호출해도 업데이트가 반영된다.
        if (StringUtils.hasText(requestDto.getTitle())) {
            if (!StringHelper.isLengthBetween(requestDto.getTitle(), 1, 30)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The length of the title is exceeded. Please write within 30 characters.");
            }

            schedule.updateTitle(requestDto.getTitle());
        }
        if (StringUtils.hasText(requestDto.getName())) schedule.updateName(requestDto.getName());

        return new ScheduleSummaryResponseDto(schedule);
    }

    @Transactional
    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto) {

        Schedule schedule = entityFinder.findByIdOrElseThrow(scheduleRepository, id);

        passwordValidator.validatePassword(schedule, requestDto.getPassword());

        scheduleRepository.deleteById(id);
    }

}
