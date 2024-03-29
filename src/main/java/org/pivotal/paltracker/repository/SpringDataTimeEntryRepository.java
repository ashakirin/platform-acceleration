package org.pivotal.paltracker.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTimeEntryRepository extends CrudRepository<TimeEntry, Long> {
}
