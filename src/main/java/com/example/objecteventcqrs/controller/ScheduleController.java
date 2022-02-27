package com.example.objecteventcqrs.controller;

import com.example.objecteventcqrs.configuration.ScheduleEventStreamConfiguration;
import com.example.objecteventcqrs.controller.dtos.ReCurringScheduleStreamDto;
import com.example.objecteventcqrs.controller.dtos.ScheduleEventDto;
import com.example.objecteventcqrs.controller.dtos.ScheduleEventStreamDto;
import com.example.objecteventcqrs.controller.dtos.ScheduleEventType;
import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.service.ScheduleCommandService;
import com.example.objecteventcqrs.service.ScheduleQueryService;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleQueryService scheduleQueryService;
//    private final ScheduleCommandService scheduleCommandService;
    private final Sinks.Many<ScheduleEventStreamDto> scheduleEventPublisher;
    private final Sinks.Many<ReCurringScheduleStreamDto> recurringScheduleEventPublisher;


    @PostMapping("/schedule")
    public void createEvent(@RequestBody ScheduleEventDto eventDto){
        this.scheduleEventPublisher.tryEmitNext(ScheduleEventStreamDto.from(eventDto, ScheduleEventType.NEW_SCHEDULE));
    }

    @GetMapping("/schedule/{eventId}")
    public Mono<ScheduleEvent> findSchedule(@PathVariable("eventId") Long eventId) {
        return this.scheduleQueryService.findSchedule(eventId);
    }

    @PutMapping("/schedule")
    public void reschedule(@RequestBody ReCurringScheduleStreamDto scheduleStreamDto) {
        this.recurringScheduleEventPublisher.tryEmitNext(scheduleStreamDto);
    }

    @PostMapping("/schedule/satisfaction")
    public Mono<Boolean> isSatisfied(@RequestBody RecurringSchedule schedule) {
        System.out.println("dayofweek => " + schedule.getDayOfWeek());
        return this.scheduleQueryService.isSatisfied(schedule);
    }
}
