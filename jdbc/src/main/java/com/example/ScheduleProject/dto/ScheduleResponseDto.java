package com.example.ScheduleProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String content;
    private String name;
    private LocalDateTime writeTime;
    private LocalDateTime updateTime;
}