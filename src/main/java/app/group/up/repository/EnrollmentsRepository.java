package app.group.up.repository;

import app.group.up.domain.Enrollments;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Enrollments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollments, Long> {

}
