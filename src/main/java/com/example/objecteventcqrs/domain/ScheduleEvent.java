package com.example.objecteventcqrs.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ScheduleEvent {
    private final Long eventId;
    private final String subject;
    private final LocalDateTime from;
    private final Duration duration;

    public boolean isSatisfied(RecurringSchedule schedule) {
        return !(from.getDayOfWeek() != schedule.getDayOfWeek() ||
            !from.toLocalTime().equals(schedule.getFrom()) ||
            !duration.equals(schedule.getDuration()));
    }

    public ScheduleEvent reschedule(RecurringSchedule schedule) {
        return new ScheduleEvent(
            this.eventId,
            this.subject,
            LocalDateTime.of(this.from.toLocalDate().plusDays(dayDistance(schedule)), schedule.getFrom()),
            schedule.getDuration()
        );
    }

    private long dayDistance(RecurringSchedule schedule) {
        return schedule.getDayOfWeek().getValue() - this.from.getDayOfWeek().getValue();
    }
}
