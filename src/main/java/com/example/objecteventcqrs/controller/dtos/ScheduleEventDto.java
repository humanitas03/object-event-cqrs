package com.example.objecteventcqrs.controller.dtos;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleEventDto {
    private Long eventId;
    private String subject;
    private LocalDateTime from;
    private Duration duration;
}
