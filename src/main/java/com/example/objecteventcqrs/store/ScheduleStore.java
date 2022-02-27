package com.example.objecteventcqrs.store;

import com.example.objecteventcqrs.domain.ScheduleEvent;
import reactor.core.publisher.Mono;

public interface ScheduleStore {

    Mono<ScheduleEvent> create(ScheduleEvent event);
    Mono<ScheduleEvent> find(Long id);
    Mono<ScheduleEvent> findBySubject(String subject);
    Mono<ScheduleEvent> update(Long id, ScheduleEvent updatedEvent);
    void delete(String subject);
}
