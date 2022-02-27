package com.example.objecteventcqrs.store;

import com.example.objecteventcqrs.store.entity.ScheduleEventEntity;
import java.util.Optional;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ScheduleR2dbcRepository extends R2dbcRepository<ScheduleEventEntity, Long> {
    Mono<ScheduleEventEntity> findScheduleEventEntityBySubject(String subject);
    void deleteScheduleEventEntityBySubject(String subject);
}
