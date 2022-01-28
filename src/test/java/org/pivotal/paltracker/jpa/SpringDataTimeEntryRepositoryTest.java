package org.pivotal.paltracker.jpa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pivotal.paltracker.repository.SpringDataTimeEntryRepository;
import org.pivotal.paltracker.repository.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@TestPropertySource(properties = {
//        "spring.jpa.hibernate.ddl-auto=create-drop"
//})
//@TestPropertySource(properties = {
//        "spring.jpa.hibernate.ddl-auto=validate"
//})
@TestPropertySource("classpath:/liquibase/liquibase.properties")
class SpringDataTimeEntryRepositoryTest {
    @Autowired
    private SpringDataTimeEntryRepository repository;

    private TimeEntry timeEntry1;
    private TimeEntry timeEntry2;

    private long id1;
    private long id2;

    @BeforeEach
    public void setup() {
        timeEntry1 = new TimeEntry(1, 1, LocalDate.parse("2017-01-08"), 8);
        timeEntry2 = new TimeEntry(2, 2, LocalDate.parse("2017-01-08"), 9);

        repository.saveAll(List.of(timeEntry1, timeEntry2));

        id1 = timeEntry1.getId();
        id2 = timeEntry2.getId();

        Iterable<TimeEntry> entries = repository.findAll();
        entries.forEach(e -> System.out.println(e.getId()));
    }

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    public void shouldFindEntityById() {
        Optional<TimeEntry> timeEntry = repository.findById(id1);

        assertTrue(timeEntry.isPresent());
        assertEquals(id1, timeEntry.get().getId());
        assertEquals(8, timeEntry.get().getHours());
    }

    @Test
    public void shouldNotFoundEntityById() {
        Optional<TimeEntry> timeEntry = repository.findById(1000L);
        assertTrue(timeEntry.isEmpty());
    }

    @Test
    public void shouldUpdateEntity() {
        timeEntry1.setHours(10);

        repository.save(timeEntry1);

        Optional<TimeEntry> controlEntry = repository.findById(id1);
        assertTrue(controlEntry.isPresent());
        assertEquals(id1, controlEntry.get().getId());
        assertEquals(10, controlEntry.get().getHours());
    }

    @Test
    public void shouldDeleteEntity() {
        repository.delete(timeEntry1);

        Optional<TimeEntry> controlEntry = repository.findById(id1);
        assertTrue(controlEntry.isEmpty());
    }

    @Test
    public void shouldListEntities() {
        Iterable<TimeEntry> entities = repository.findAll();

        List<TimeEntry> listEntities =
                StreamSupport.stream(entities.spliterator(), false)
                        .collect(Collectors.toList());
        assertEquals(2, listEntities.size());
        assertTrue(listEntities.stream()
                .anyMatch(e -> e.getId() == id1));
        assertTrue(listEntities.stream()
                .anyMatch(e -> e.getId() == id2));
    }
}