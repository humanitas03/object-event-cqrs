package com.example.objecteventcqrs.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventDto {
    private Long eventId;
    private String subject;
    private LocalDateTime from;
    private Duration duration;
}
