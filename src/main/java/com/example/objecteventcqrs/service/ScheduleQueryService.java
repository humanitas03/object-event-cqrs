package com.example.objecteventcqrs.service;

import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import reactor.core.publisher.Mono;

public interface ScheduleQueryService {
    Mono<ScheduleEvent> findSchedule(Long id);
    Mono<Boolean> isSatisfied(RecurringSchedule schedule);
}
