package com.example.objecteventcqrs.service;

import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import reactor.core.publisher.Mono;

public interface ScheduleService {
    Mono<ScheduleEvent> findSchedule(Long id);
    Mono<ScheduleEvent> reschedule(RecurringSchedule schedule);
    Mono<Boolean> isSatisfied(RecurringSchedule schedule);
    Mono<ScheduleEvent> createEvent(ScheduleEvent event);
}
