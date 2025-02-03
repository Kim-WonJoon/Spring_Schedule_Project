package com.example.ScheduleProject.repository;

import com.example.ScheduleProject.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);
    Optional<Schedule> findById(Long id);
    List<Schedule> findAll(String updateTime, String name); // 전체 일정 조회 (조건부 검색 포함)
    void update(Long id, String content, String name, String password);
    void delete(Long id, String password);
}
