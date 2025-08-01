package com.example.schedulemanagementapi.service;

import com.example.schedulemanagementapi.dto.ScheduleDetailResponseDto;
import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleSummaryResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleSummaryResponseDto saveSchedule(ScheduleRequestDto requestDto);

    List<ScheduleSummaryResponseDto> findAllSchedules(String name);

    ScheduleDetailResponseDto findScheduleById(Long id);

    ScheduleSummaryResponseDto updateScheduleTitleAndName(Long id, ScheduleRequestDto requestDto);

    void deleteSchedule(Long id, ScheduleRequestDto requestDto);
}
