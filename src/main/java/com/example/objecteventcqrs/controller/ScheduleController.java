package com.example.objecteventcqrs.controller;

import com.example.objecteventcqrs.controller.dtos.ScheduleEventDto;
import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.service.ScheduleCommandService;
import com.example.objecteventcqrs.service.ScheduleQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleQueryService scheduleQueryService;
    private final ScheduleCommandService scheduleCommandService;

    @PostMapping("/schedule")
    public Mono<ScheduleEvent> createEvent(@RequestBody ScheduleEventDto eventDto){
        return this.scheduleCommandService.createEvent(new ScheduleEvent(
            null,
            eventDto.getSubject(),
            eventDto.getFrom(),
            eventDto.getDuration()
        ));
    }

    @GetMapping("/schedule/{eventId}")
    public Mono<ScheduleEvent> findSchedule(@PathVariable("eventId") Long eventId) {
        return this.scheduleQueryService.findSchedule(eventId);
    }

    @PutMapping("/schedule")
    public Mono<ScheduleEvent> reschedule(@RequestBody RecurringSchedule schedule) {
        return this.scheduleCommandService.reschedule(schedule);
    }

    @PostMapping("/schedule/satisfaction")
    public Mono<Boolean> isSatisfied(@RequestBody RecurringSchedule schedule) {
        System.out.println("dayofweek => " + schedule.getDayOfWeek());
        return this.scheduleQueryService.isSatisfied(schedule);
    }
}
