package com.example.objecteventcqrs.configuration;

import com.example.objecteventcqrs.controller.dtos.ReCurringScheduleStreamDto;
import com.example.objecteventcqrs.controller.dtos.ScheduleEventStreamDto;
import com.example.objecteventcqrs.domain.RecurringSchedule;
import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.service.ScheduleCommandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;

@Configuration
@RequiredArgsConstructor
public class ScheduleEventStreamConfiguration {
    private final ScheduleCommandService scheduleCommandService;
    private final ObjectMapper objectMapper;

    @Bean
    public Consumer<ScheduleEventStreamDto> scheduleEventConsumer() {

        return it -> {
            try {
                System.out.println("Consume!!======" + objectMapper.writeValueAsString(it));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            this.scheduleCommandService.createEvent(new ScheduleEvent(
                null,
                it.getScheduleEventDto().getSubject(),
                it.getScheduleEventDto().getFrom(),
                it.getScheduleEventDto().getDuration()
            ))
                .block(Duration.ofSeconds(10));
        };
    }

    @Bean
    public Consumer<ReCurringScheduleStreamDto> reCurringScheduleConsumer(){
        System.out.println("Consume!!======" );
        return it -> {
            try {
                System.out.println("Consume!!======" + objectMapper.writeValueAsString(it));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            this.scheduleCommandService
                .reschedule(RecurringSchedule.fromStreamDto(it))
                .block(Duration.ofSeconds(10));
        };
    }

    @Bean
    public Sinks.Many<ScheduleEventStreamDto> scheduleEventPublisher() {
        return Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, true);
    }

    @Bean
    public Sinks.Many<ReCurringScheduleStreamDto> recurringScheduleEventPublisher() {
        return Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, true);
    }

    @Bean
    public Supplier<Flux<ScheduleEventStreamDto>> scheduleEventProducer() {
        return scheduleEventPublisher()::asFlux;
    }

    @Bean
    public Supplier<Flux<ReCurringScheduleStreamDto>> reCurringScheduleProducer() {
        return recurringScheduleEventPublisher()::asFlux;
    }
}
