package com.example.objecteventcqrs.service;

import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.store.ScheduleStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService{

    private final ScheduleStore scheduleStore;

    @Override
    public Mono<ScheduleEvent> findSchedule(Long id) {
        return scheduleStore.find(id);
    }

    @Override
    public Mono<Boolean> isSatisfied(RecurringSchedule schedule) {
        return scheduleStore
            .findBySubject(schedule.getSubject())
            .map(it->it.isSatisfied(schedule));
    }


}
