package com.example.objecteventcqrs.store.entity;

import com.example.objecteventcqrs.domain.ScheduleEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("schedule")
public class ScheduleEventEntity implements Persistable<Long> {

    @Id
    private Long id;

    @Column("subject_name")
    private String subject;

    @Column("from_datetime")
    private LocalDateTime from;

    @Column("duration_minutes")
    private Long duration;

    @Transient
    public boolean newFlag;

    public ScheduleEventEntity() {
        this.newFlag = true;
    }

    public ScheduleEventEntity(Long id, String subject, LocalDateTime from, Long duration, boolean newFlag) {
        this.id = id;
        this.subject = subject;
        this.from = from;
        this.duration = duration;
        this.newFlag = newFlag;
    }

    public ScheduleEventEntity(String subject, LocalDateTime from, Long duration) {
        this.id = null;
        this.subject = subject;
        this.from = from;
        this.duration = duration;
        this.newFlag = true;
    }

    public static ScheduleEventEntity fromNewDomainEntity(ScheduleEvent event) {
        return new ScheduleEventEntity(
            event.getSubject(),
            event.getFrom(),
            event.getDuration().toMinutes()
        );
    }

    public static ScheduleEventEntity fromUpdatedDomainEntity(Long id, ScheduleEvent event) {
        return new ScheduleEventEntity(
            id,
            event.getSubject(),
            event.getFrom(),
            event.getDuration().toMinutes(),
            false
        );
    }

    public ScheduleEvent toDomainEntity() {
        return new ScheduleEvent(
            this.id,
            this.subject,
            this.from,
            Duration.ofMinutes(this.duration)
        );
    }


    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.newFlag;
    }
}
