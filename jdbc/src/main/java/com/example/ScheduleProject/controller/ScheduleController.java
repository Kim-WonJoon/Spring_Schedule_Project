package com.example.ScheduleProject.controller;

import com.example.ScheduleProject.dto.ScheduleRequestDto;
import com.example.ScheduleProject.dto.ScheduleResponseDto;
import com.example.ScheduleProject.dto.ScheduleUpdateRequestDto;
import com.example.ScheduleProject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return ResponseEntity.ok(scheduleService.createSchedule(requestDto));
    }

    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) String updateTime,
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(scheduleService.getAllSchedules(updateTime, name));
    }

    // 선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return ResponseEntity.ok(scheduleService.getScheduleById(id));
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto) {
        scheduleService.updateSchedule(id, requestDto);
        return ResponseEntity.noContent().build();
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestParam String password) {
        scheduleService.deleteSchedule(id, password);
        return ResponseEntity.noContent().build();
    }
}
