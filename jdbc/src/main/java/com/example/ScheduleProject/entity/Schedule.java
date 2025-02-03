package com.example.ScheduleProject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String name;
    private String password;
    private String content;
    private LocalDateTime writeTime;
    private LocalDateTime updateTime;

    public Schedule(String name, String password, String content) {
        this.content = content;
        this.name = name;
        this.password = password;
        this.writeTime = LocalDateTime.now();
        this.updateTime = this.writeTime;
    }
}
