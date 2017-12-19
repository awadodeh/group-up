package app.group.up.service;

import app.group.up.service.dto.CircleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Circle.
 */
public interface CircleService {

    /**
     * Save a circle.
     *
     * @param circleDTO the entity to save
     * @return the persisted entity
     */
    CircleDTO save(CircleDTO circleDTO);

    /**
     *  Get all the circles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CircleDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" circle.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CircleDTO findOne(Long id);

    /**
     *  Delete the "id" circle.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the circle corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CircleDTO> search(String query, Pageable pageable);
}
