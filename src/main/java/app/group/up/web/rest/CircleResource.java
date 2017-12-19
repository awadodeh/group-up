package app.group.up.web.rest;

import com.codahale.metrics.annotation.Timed;
import app.group.up.service.CircleService;
import app.group.up.web.rest.util.HeaderUtil;
import app.group.up.web.rest.util.PaginationUtil;
import app.group.up.service.dto.CircleDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Circle.
 */
@RestController
@RequestMapping("/api")
public class CircleResource {

    private final Logger log = LoggerFactory.getLogger(CircleResource.class);

    private static final String ENTITY_NAME = "circle";

    private final CircleService circleService;

    public CircleResource(CircleService circleService) {
        this.circleService = circleService;
    }

    /**
     * POST  /circles : Create a new circle.
     *
     * @param circleDTO the circleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new circleDTO, or with status 400 (Bad Request) if the circle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/circles")
    @Timed
    public ResponseEntity<CircleDTO> createCircle(@Valid @RequestBody CircleDTO circleDTO) throws URISyntaxException {
        log.debug("REST request to save Circle : {}", circleDTO);
        if (circleDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new circle cannot already have an ID")).body(null);
        }
        CircleDTO result = circleService.save(circleDTO);
        return ResponseEntity.created(new URI("/api/circles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /circles : Updates an existing circle.
     *
     * @param circleDTO the circleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated circleDTO,
     * or with status 400 (Bad Request) if the circleDTO is not valid,
     * or with status 500 (Internal Server Error) if the circleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/circles")
    @Timed
    public ResponseEntity<CircleDTO> updateCircle(@Valid @RequestBody CircleDTO circleDTO) throws URISyntaxException {
        log.debug("REST request to update Circle : {}", circleDTO);
        if (circleDTO.getId() == null) {
            return createCircle(circleDTO);
        }
        CircleDTO result = circleService.save(circleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, circleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /circles : get all the circles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of circles in body
     */
    @GetMapping("/circles")
    @Timed
    public ResponseEntity<List<CircleDTO>> getAllCircles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Circles");
        Page<CircleDTO> page = circleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/circles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /circles/:id : get the "id" circle.
     *
     * @param id the id of the circleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the circleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/circles/{id}")
    @Timed
    public ResponseEntity<CircleDTO> getCircle(@PathVariable Long id) {
        log.debug("REST request to get Circle : {}", id);
        CircleDTO circleDTO = circleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(circleDTO));
    }

    /**
     * DELETE  /circles/:id : delete the "id" circle.
     *
     * @param id the id of the circleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/circles/{id}")
    @Timed
    public ResponseEntity<Void> deleteCircle(@PathVariable Long id) {
        log.debug("REST request to delete Circle : {}", id);
        circleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/circles?query=:query : search for the circle corresponding
     * to the query.
     *
     * @param query the query of the circle search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/circles")
    @Timed
    public ResponseEntity<List<CircleDTO>> searchCircles(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Circles for query {}", query);
        Page<CircleDTO> page = circleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/circles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
