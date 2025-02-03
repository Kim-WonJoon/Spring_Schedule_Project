package com.example.ScheduleProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    private String content;
    private String name;
    private String password;

}
