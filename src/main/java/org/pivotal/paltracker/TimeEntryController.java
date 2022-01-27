package org.pivotal.paltracker;

import org.pivotal.paltracker.repository.TimeEntry;
import org.pivotal.paltracker.repository.TimeEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) {
        TimeEntry resultTimeEntry = timeEntryRepository.create(timeEntry);
        return ResponseEntity.created(URI.create(Long.toString(timeEntry.getId()))).body(resultTimeEntry);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long id) {
        TimeEntry readTimeEntry = timeEntryRepository.find(id);
        return (readTimeEntry != null)? ResponseEntity.ok(readTimeEntry) : ResponseEntity.notFound().build();
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        return ResponseEntity.ok(timeEntryRepository.list());
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable("id") long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, timeEntry);
        return (updatedTimeEntry != null)? ResponseEntity.ok(updatedTimeEntry) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable("id") long id) {
        boolean deleted = timeEntryRepository.delete(id);
        return (deleted)? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
