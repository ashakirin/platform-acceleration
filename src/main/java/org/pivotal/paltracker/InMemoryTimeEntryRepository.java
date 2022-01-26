package org.pivotal.paltracker;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryTimeEntryRepository implements TimeEntryRepository {
    private Map<Long, TimeEntry> storage = new HashMap();
    private AtomicLong atomicCounter = new AtomicLong();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        if (timeEntry.getId() == 0) {
            timeEntry.setId(atomicCounter.incrementAndGet());
        }
        storage.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return storage.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return storage.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (!storage.containsKey(id)) {
            return null;
        }
        timeEntry.setId(id);
        storage.put(id, timeEntry);
        return timeEntry;
    }

    @Override
    public int delete(long id) {
        TimeEntry timeEntry = storage.remove(id);
        return (timeEntry != null)? 1 : 0;
    }
}
