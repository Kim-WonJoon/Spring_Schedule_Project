package com.example.ScheduleProject.service;

import com.example.ScheduleProject.dto.ScheduleRequestDto;
import com.example.ScheduleProject.dto.ScheduleResponseDto;
import com.example.ScheduleProject.dto.ScheduleUpdateRequestDto;
import com.example.ScheduleProject.entity.Schedule;
import com.example.ScheduleProject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 일정 저장
    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto.getContent(), requestDto.getName(), requestDto.getPassword());
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(
                savedSchedule.getId(),
                savedSchedule.getContent(),
                savedSchedule.getName(),
                savedSchedule.getWriteTime(),
                savedSchedule.getUpdateTime()
        );
    }

    // 전체 일정 조회
    public List<ScheduleResponseDto> getAllSchedules(String updateTime, String name) {
        return scheduleRepository.findAll(updateTime, name)
                .stream()
                .map(schedule -> new ScheduleResponseDto(
                        schedule.getId(),
                        schedule.getContent(),
                        schedule.getName(),
                        schedule.getWriteTime(),
                        schedule.getUpdateTime()
                ))
                .collect(Collectors.toList());
    }

    // ID로 일정 조회
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 일정이 존재하지 않습니다."));

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getContent(),
                schedule.getName(),
                schedule.getWriteTime(),
                schedule.getUpdateTime()
        );
    }

    // 일정 수정
    public void updateSchedule(Long id, ScheduleUpdateRequestDto requestDto) {
        scheduleRepository.update(id, requestDto.getContent(), requestDto.getName(), requestDto.getPassword());
    }

    // 일정 삭제
    public void deleteSchedule(Long id, String password) {
        scheduleRepository.delete(id, password);
    }
}
