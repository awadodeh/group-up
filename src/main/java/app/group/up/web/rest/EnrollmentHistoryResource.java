package app.group.up.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.group.up.service.EnrollmentHistoryService;
import app.group.up.web.rest.util.HeaderUtil;
import app.group.up.web.rest.util.PaginationUtil;
import app.group.up.service.dto.EnrollmentHistoryDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EnrollmentHistory.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentHistoryResource.class);

    private static final String ENTITY_NAME = "enrollmentHistory";

    private final EnrollmentHistoryService enrollmentHistoryService;

    public EnrollmentHistoryResource(EnrollmentHistoryService enrollmentHistoryService) {
        this.enrollmentHistoryService = enrollmentHistoryService;
    }

    /**
     * POST  /enrollment-histories : Create a new enrollmentHistory.
     *
     * @param enrollmentHistoryDTO the enrollmentHistoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enrollmentHistoryDTO, or with status 400 (Bad Request) if the enrollmentHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enrollment-histories")
    @Timed
    public ResponseEntity<EnrollmentHistoryDTO> createEnrollmentHistory(@RequestBody EnrollmentHistoryDTO enrollmentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save EnrollmentHistory : {}", enrollmentHistoryDTO);
        if (enrollmentHistoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new enrollmentHistory cannot already have an ID")).body(null);
        }
        EnrollmentHistoryDTO result = enrollmentHistoryService.save(enrollmentHistoryDTO);
        return ResponseEntity.created(new URI("/api/enrollment-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enrollment-histories : Updates an existing enrollmentHistory.
     *
     * @param enrollmentHistoryDTO the enrollmentHistoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enrollmentHistoryDTO,
     * or with status 400 (Bad Request) if the enrollmentHistoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the enrollmentHistoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enrollment-histories")
    @Timed
    public ResponseEntity<EnrollmentHistoryDTO> updateEnrollmentHistory(@RequestBody EnrollmentHistoryDTO enrollmentHistoryDTO) throws URISyntaxException {
        log.debug("REST request to update EnrollmentHistory : {}", enrollmentHistoryDTO);
        if (enrollmentHistoryDTO.getId() == null) {
            return createEnrollmentHistory(enrollmentHistoryDTO);
        }
        EnrollmentHistoryDTO result = enrollmentHistoryService.save(enrollmentHistoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enrollmentHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enrollment-histories : get all the enrollmentHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enrollmentHistories in body
     */
    @GetMapping("/enrollment-histories")
    @Timed
    public ResponseEntity<List<EnrollmentHistoryDTO>> getAllEnrollmentHistories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of EnrollmentHistories");
        Page<EnrollmentHistoryDTO> page = enrollmentHistoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enrollment-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /enrollment-histories/:id : get the "id" enrollmentHistory.
     *
     * @param id the id of the enrollmentHistoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enrollmentHistoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enrollment-histories/{id}")
    @Timed
    public ResponseEntity<EnrollmentHistoryDTO> getEnrollmentHistory(@PathVariable Long id) {
        log.debug("REST request to get EnrollmentHistory : {}", id);
        EnrollmentHistoryDTO enrollmentHistoryDTO = enrollmentHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enrollmentHistoryDTO));
    }

    /**
     * DELETE  /enrollment-histories/:id : delete the "id" enrollmentHistory.
     *
     * @param id the id of the enrollmentHistoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enrollment-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnrollmentHistory(@PathVariable Long id) {
        log.debug("REST request to delete EnrollmentHistory : {}", id);
        enrollmentHistoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/enrollment-histories?query=:query : search for the enrollmentHistory corresponding
     * to the query.
     *
     * @param query the query of the enrollmentHistory search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/enrollment-histories")
    @Timed
    public ResponseEntity<List<EnrollmentHistoryDTO>> searchEnrollmentHistories(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of EnrollmentHistories for query {}", query);
        Page<EnrollmentHistoryDTO> page = enrollmentHistoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/enrollment-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
