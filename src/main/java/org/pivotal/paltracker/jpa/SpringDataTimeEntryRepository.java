package org.pivotal.paltracker.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataTimeEntryRepository extends CrudRepository<TimeEntryJpa, Long> {
}
