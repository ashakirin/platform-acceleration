package org.pivotal.paltracker.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.pivotal.paltracker.TimeEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SpringDataTimeEntryRepositoryTest {
    @Autowired
    private SpringDataTimeEntryRepository repository;

    @BeforeEach
    public void setup() {
        TimeEntryJpa timeEntry1 = new TimeEntryJpa(1, 1, 1, LocalDate.parse("2017-01-08"), 8);
        TimeEntryJpa timeEntry2 = new TimeEntryJpa(2, 2, 2, LocalDate.parse("2017-01-08"), 9);

        repository.saveAll(List.of(timeEntry1, timeEntry2));
    }

    @Test
    public void shouldReadEntityById() {
        Optional<TimeEntryJpa> timeEntryJpa = repository.findById(1L);

        assertTrue(timeEntryJpa.isPresent());
        assertEquals(1, timeEntryJpa.get().getId());
        assertEquals(8, timeEntryJpa.get().getHours());
    }
}