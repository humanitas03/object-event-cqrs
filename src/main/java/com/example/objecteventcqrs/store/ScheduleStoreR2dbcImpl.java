package com.example.objecteventcqrs.store;

import com.example.objecteventcqrs.domain.ScheduleEvent;
import com.example.objecteventcqrs.store.entity.ScheduleEventEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ScheduleStoreR2dbcImpl implements ScheduleStore {
    private final ScheduleR2dbcRepository r2dbcRepository;

    @Override
    public Mono<ScheduleEvent> create(ScheduleEvent event) {
        return this.r2dbcRepository
            .save(ScheduleEventEntity.fromNewDomainEntity(event))
            .mapNotNull(ScheduleEventEntity::toDomainEntity)
            .onErrorMap(throwable -> new RuntimeException("스케줄 저장 중 에러 발생", throwable));
    }

    @Override
    public Mono<ScheduleEvent> find(Long id) {
        return this.r2dbcRepository.findById(id)
            .mapNotNull(ScheduleEventEntity::toDomainEntity);
    }

    @Override
    public Mono<ScheduleEvent> findBySubject(String subject) {
        return this.r2dbcRepository.findScheduleEventEntityBySubject(subject)
            .mapNotNull(ScheduleEventEntity::toDomainEntity);
    }

    @Override
    public Mono<ScheduleEvent> update(Long id, ScheduleEvent updatedEvent) {
         return this.r2dbcRepository
            .findById(id)
            .mapNotNull(it ->ScheduleEventEntity.fromUpdatedDomainEntity(it.getId(), updatedEvent))
             .flatMap(this.r2dbcRepository::save)
             .map(ScheduleEventEntity::toDomainEntity)
             .onErrorMap(throwable -> new RuntimeException("스케줄 업데이트 중 에러 발생", throwable));
    }

    @Override
    public void delete(String subject) {
        // Not Implemented
    }
}
