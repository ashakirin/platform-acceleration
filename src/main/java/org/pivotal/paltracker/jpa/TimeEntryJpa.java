package org.pivotal.paltracker.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "time_entries")
public class TimeEntryJpa {
    @Id
    private long id;
    @Column(name = "project_id")
    private long projectId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "hours")
    private int hours;

    public TimeEntryJpa() {
    }

    public TimeEntryJpa(long projectId, long userId, LocalDate date, int hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public TimeEntryJpa(long id, long projectId, long userId, LocalDate date, int hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntryJpa timeEntry = (TimeEntryJpa) o;
        return id == timeEntry.id && projectId == timeEntry.projectId && userId == timeEntry.userId && hours == timeEntry.hours && Objects.equals(date, timeEntry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectId, userId, date, hours);
    }
}
