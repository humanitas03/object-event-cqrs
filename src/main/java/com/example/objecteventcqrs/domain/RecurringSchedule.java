package com.example.objecteventcqrs.domain;

import com.example.objecteventcqrs.controller.dtos.ReCurringScheduleStreamDto;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecurringSchedule {
    private String subject;
    private DayOfWeek dayOfWeek;
    private LocalTime from;
    private Duration duration;

    public static RecurringSchedule fromStreamDto(ReCurringScheduleStreamDto dto) {
        return new RecurringSchedule(
            dto.getSubject(),
            dto.getDayOfWeek(),
            dto.getFrom(),
            dto.getDuration()
        );
    }
}
