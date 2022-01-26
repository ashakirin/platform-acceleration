package org.pivotal.paltracker;

import org.pivotal.paltracker.jpa.SpringDataTimeEntryRepository;
import org.pivotal.paltracker.jpa.TimeEntryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jdbc")
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    @Autowired
    public TimeEntryController(@Qualifier("jdbcRepository") TimeEntryRepository timeEntryRepository) {
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
        int num = timeEntryRepository.delete(id);
        return (num > 0)? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
