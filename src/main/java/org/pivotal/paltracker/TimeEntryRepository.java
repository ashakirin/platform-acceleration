package org.pivotal.paltracker;

import java.util.List;

public interface TimeEntryRepository {

    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long id);

    List<TimeEntry> list();

    TimeEntry update(long id, TimeEntry timeEntry);

    int delete(long id);
}
