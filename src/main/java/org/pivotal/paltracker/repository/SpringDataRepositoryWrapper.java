package org.pivotal.paltracker.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository("springDataRepository")
@Profile("springDataRepository")
public class SpringDataRepositoryWrapper implements TimeEntryRepository {
    private final SpringDataTimeEntryRepository repository;

    @Autowired
    public SpringDataRepositoryWrapper(SpringDataTimeEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        return repository.save(timeEntry);
    }

    @Override
    public TimeEntry find(long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        Iterable<TimeEntry> timeEntries = repository.findAll();
        return StreamSupport.stream(timeEntries.spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry updatedEntity = repository.save(timeEntry);
        return updatedEntity;
    }

    @Override
    public boolean delete(long id) {
        Optional<TimeEntry> timeEntry = repository.findById(id);
        if (timeEntry.isPresent()) {
            repository.delete(timeEntry.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
