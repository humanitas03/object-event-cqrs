package com.example.objecteventcqrs.service;

import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import reactor.core.publisher.Mono;

public interface ScheduleCommandService {
    Mono<ScheduleEvent> reschedule(RecurringSchedule schedule);
    Mono<ScheduleEvent> createEvent(ScheduleEvent event);
}
