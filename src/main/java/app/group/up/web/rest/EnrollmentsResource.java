package app.group.up.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.group.up.service.EnrollmentsService;
import app.group.up.web.rest.util.HeaderUtil;
import app.group.up.service.dto.EnrollmentsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Enrollments.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentsResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentsResource.class);

    private static final String ENTITY_NAME = "enrollments";

    private final EnrollmentsService enrollmentsService;

    public EnrollmentsResource(EnrollmentsService enrollmentsService) {
        this.enrollmentsService = enrollmentsService;
    }

    /**
     * POST  /enrollments : Create a new enrollments.
     *
     * @param enrollmentsDTO the enrollmentsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enrollmentsDTO, or with status 400 (Bad Request) if the enrollments has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enrollments")
    @Timed
    public ResponseEntity<EnrollmentsDTO> createEnrollments(@RequestBody EnrollmentsDTO enrollmentsDTO) throws URISyntaxException {
        log.debug("REST request to save Enrollments : {}", enrollmentsDTO);
        if (enrollmentsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new enrollments cannot already have an ID")).body(null);
        }
        EnrollmentsDTO result = enrollmentsService.save(enrollmentsDTO);
        return ResponseEntity.created(new URI("/api/enrollments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enrollments : Updates an existing enrollments.
     *
     * @param enrollmentsDTO the enrollmentsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enrollmentsDTO,
     * or with status 400 (Bad Request) if the enrollmentsDTO is not valid,
     * or with status 500 (Internal Server Error) if the enrollmentsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enrollments")
    @Timed
    public ResponseEntity<EnrollmentsDTO> updateEnrollments(@RequestBody EnrollmentsDTO enrollmentsDTO) throws URISyntaxException {
        log.debug("REST request to update Enrollments : {}", enrollmentsDTO);
        if (enrollmentsDTO.getId() == null) {
            return createEnrollments(enrollmentsDTO);
        }
        EnrollmentsDTO result = enrollmentsService.save(enrollmentsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enrollmentsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enrollments : get all the enrollments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enrollments in body
     */
    @GetMapping("/enrollments")
    @Timed
    public List<EnrollmentsDTO> getAllEnrollments() {
        log.debug("REST request to get all Enrollments");
        return enrollmentsService.findAll();
        }

    /**
     * GET  /enrollments/:id : get the "id" enrollments.
     *
     * @param id the id of the enrollmentsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enrollmentsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enrollments/{id}")
    @Timed
    public ResponseEntity<EnrollmentsDTO> getEnrollments(@PathVariable Long id) {
        log.debug("REST request to get Enrollments : {}", id);
        EnrollmentsDTO enrollmentsDTO = enrollmentsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enrollmentsDTO));
    }

    /**
     * DELETE  /enrollments/:id : delete the "id" enrollments.
     *
     * @param id the id of the enrollmentsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enrollments/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnrollments(@PathVariable Long id) {
        log.debug("REST request to delete Enrollments : {}", id);
        enrollmentsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/enrollments?query=:query : search for the enrollments corresponding
     * to the query.
     *
     * @param query the query of the enrollments search
     * @return the result of the search
     */
    @GetMapping("/_search/enrollments")
    @Timed
    public List<EnrollmentsDTO> searchEnrollments(@RequestParam String query) {
        log.debug("REST request to search Enrollments for query {}", query);
        return enrollmentsService.search(query);
    }

}
