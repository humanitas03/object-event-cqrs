package com.example.objecteventcqrs.service;

import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.store.ScheduleStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleCommandServiceImpl implements ScheduleCommandService{

    private final ScheduleStore scheduleStore;

    @Override
    public Mono<ScheduleEvent> reschedule(RecurringSchedule schedule) {
        return scheduleStore
            .findBySubject(schedule.getSubject())
            .map(it->it.reschedule(schedule))
            .flatMap(it->this.scheduleStore.update(it.getEventId(), it));
    }

    @Override
    public Mono<ScheduleEvent> createEvent(ScheduleEvent event) {
        return scheduleStore.create(event);
    }
}
