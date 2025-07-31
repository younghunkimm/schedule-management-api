package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleResponseDto> findAllSchedules(String name);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateScheduleTitleAndName(Long id, ScheduleRequestDto requestDto);

    void deleteSchedule(Long id, ScheduleRequestDto requestDto);
}
