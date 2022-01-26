package org.pivotal.paltracker.jpa;

import org.pivotal.paltracker.TimeEntry;
import org.pivotal.paltracker.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
public class TimeEntryControllerJpa {
    private final SpringDataTimeEntryRepository springDataTimeEntryRepository;

    @Autowired
    public TimeEntryControllerJpa(SpringDataTimeEntryRepository springDataTimeEntryRepository) {
        this.springDataTimeEntryRepository = springDataTimeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntryJpa> create(@RequestBody TimeEntryJpa timeEntry) {
        //TimeEntry resultTimeEntry = timeEntryRepository.create(timeEntry);
        TimeEntryJpa resultTimeEntry = springDataTimeEntryRepository.save(timeEntry);
        return ResponseEntity.created(URI.create(Long.toString(timeEntry.getId()))).body(resultTimeEntry);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntryJpa> read(@PathVariable("id") long id) {
        Optional<TimeEntryJpa> readTimeEntry = springDataTimeEntryRepository.findById(id);
        return (!readTimeEntry.isEmpty())? ResponseEntity.ok(readTimeEntry.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntryJpa>> list() {
        List<TimeEntryJpa> timeEntries = new ArrayList<>();
        springDataTimeEntryRepository.findAll().forEach(
                timeEntries::add
        );
        return ResponseEntity.ok(timeEntries);
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntryJpa> update(@PathVariable("id") long id, @RequestBody TimeEntryJpa timeEntry) {
        TimeEntryJpa updatedTimeEntry = springDataTimeEntryRepository.save(timeEntry);
        return (updatedTimeEntry != null)? ResponseEntity.ok(updatedTimeEntry) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        Optional<TimeEntryJpa> readTimeEntry = springDataTimeEntryRepository.findById(id);
        if (readTimeEntry.isPresent()) {
            springDataTimeEntryRepository.delete(readTimeEntry.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
