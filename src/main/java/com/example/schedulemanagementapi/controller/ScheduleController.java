package com.example.schedulemanagementapi.controller;

import com.example.schedulemanagementapi.dto.ScheduleRequestDto;
import com.example.schedulemanagementapi.dto.ScheduleResponseDto;
import com.example.schedulemanagementapi.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        // 생성에 성공한다면 반환받은 데이터를 [201 Created]와 함께 클라이언트에게 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(
            @RequestParam(required = false) String name
    ) {
        log.info("name={}", name);

        // 작성자명(name)을 기준으로 등록된 일정 목록을 전부 조회
        return new ResponseEntity<>(scheduleService.findAllSchedules(name), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * <p>제목과 이름만 변경할 수 있는 명확한 기능의 역할이기 때문에</p>
     * <p>메서드명을 직관적으로 작성하여 분리</p>
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleTitleAndName(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto
    ) {

        /*
        제목과 이름만 변경하는 기능이지만, DTO에서 데이터를 따로 꺼내어 넘기지 않고 DTO를 넘기는 이유
        - 안전하고, 유지보수에 유리하다.
        - 필드가 많아지면 인자도 그에 맞춰 늘어나야 함
          또한, Service의 비즈니스 로직까지 수정해야하기 때문
        - DTO를 넘기면, Service 로직만 수정하면 됨
         */
        return new ResponseEntity<>(scheduleService.updateScheduleTitleAndName(id, requestDto), HttpStatus.OK);
    }
}
