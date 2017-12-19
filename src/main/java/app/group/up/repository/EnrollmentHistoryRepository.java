package app.group.up.repository;

import app.group.up.domain.EnrollmentHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EnrollmentHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentHistoryRepository extends JpaRepository<EnrollmentHistory, Long> {

}
