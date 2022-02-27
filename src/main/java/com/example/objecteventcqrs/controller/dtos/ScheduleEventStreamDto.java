package com.example.objecteventcqrs.controller.dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEventStreamDto {
    private ScheduleEventDto scheduleEventDto;
    private ScheduleEventType scheduleEventType;

    public static ScheduleEventStreamDto from(ScheduleEventDto scheduleEventDto, ScheduleEventType type) {
        return new ScheduleEventStreamDto(
            scheduleEventDto,
            type
        );
    }
}
