package app.group.up.service;

import app.group.up.service.dto.EnrollmentsDTO;
import java.util.List;

/**
 * Service Interface for managing Enrollments.
 */
public interface EnrollmentsService {

    /**
     * Save a enrollments.
     *
     * @param enrollmentsDTO the entity to save
     * @return the persisted entity
     */
    EnrollmentsDTO save(EnrollmentsDTO enrollmentsDTO);

    /**
     *  Get all the enrollments.
     *
     *  @return the list of entities
     */
    List<EnrollmentsDTO> findAll();

    /**
     *  Get the "id" enrollments.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    EnrollmentsDTO findOne(Long id);

    /**
     *  Delete the "id" enrollments.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the enrollments corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<EnrollmentsDTO> search(String query);
}
