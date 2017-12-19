package app.group.up.service;

import app.group.up.service.dto.EnrollmentHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EnrollmentHistory.
 */
public interface EnrollmentHistoryService {

    /**
     * Save a enrollmentHistory.
     *
     * @param enrollmentHistoryDTO the entity to save
     * @return the persisted entity
     */
    EnrollmentHistoryDTO save(EnrollmentHistoryDTO enrollmentHistoryDTO);

    /**
     *  Get all the enrollmentHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EnrollmentHistoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" enrollmentHistory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EnrollmentHistoryDTO findOne(Long id);

    /**
     *  Delete the "id" enrollmentHistory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the enrollmentHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<EnrollmentHistoryDTO> search(String query, Pageable pageable);
}
