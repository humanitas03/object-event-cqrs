package com.example.objecteventcqrs.service;

import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.store.ScheduleStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleQueryService, ScheduleCommandService{

    private final ScheduleStore scheduleStore;

    @Override
    @Transactional(readOnly = true)
    public Mono<ScheduleEvent> findSchedule(Long id) {
        return scheduleStore.find(id);
    }

    @Override
    public Mono<ScheduleEvent> reschedule(RecurringSchedule schedule) {
        return scheduleStore
            .findBySubject(schedule.getSubject())
            .map(it->it.reschedule(schedule))
            .flatMap(it->this.scheduleStore.update(it.getEventId(), it));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Boolean> isSatisfied(RecurringSchedule schedule) {
        return scheduleStore
            .findBySubject(schedule.getSubject())
            .map(it->it.isSatisfied(schedule));
    }

    @Override
    public Mono<ScheduleEvent> createEvent(ScheduleEvent event) {
        return scheduleStore.create(event);
    }
}
