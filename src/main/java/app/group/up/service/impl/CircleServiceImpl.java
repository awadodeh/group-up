package app.group.up.service.impl;

import app.group.up.service.CircleService;
import app.group.up.domain.Circle;
import app.group.up.repository.CircleRepository;
import app.group.up.repository.search.CircleSearchRepository;
import app.group.up.service.dto.CircleDTO;
import app.group.up.service.mapper.CircleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Circle.
 */
@Service
@Transactional
public class CircleServiceImpl implements CircleService{

    private final Logger log = LoggerFactory.getLogger(CircleServiceImpl.class);

    private final CircleRepository circleRepository;

    private final CircleMapper circleMapper;

    private final CircleSearchRepository circleSearchRepository;

    public CircleServiceImpl(CircleRepository circleRepository, CircleMapper circleMapper, CircleSearchRepository circleSearchRepository) {
        this.circleRepository = circleRepository;
        this.circleMapper = circleMapper;
        this.circleSearchRepository = circleSearchRepository;
    }

    /**
     * Save a circle.
     *
     * @param circleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CircleDTO save(CircleDTO circleDTO) {
        log.debug("Request to save Circle : {}", circleDTO);
        Circle circle = circleMapper.toEntity(circleDTO);
        circle = circleRepository.save(circle);
        CircleDTO result = circleMapper.toDto(circle);
        circleSearchRepository.save(circle);
        return result;
    }

    /**
     *  Get all the circles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CircleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Circles");
        return circleRepository.findAll(pageable)
            .map(circleMapper::toDto);
    }

    /**
     *  Get one circle by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CircleDTO findOne(Long id) {
        log.debug("Request to get Circle : {}", id);
        Circle circle = circleRepository.findOne(id);
        return circleMapper.toDto(circle);
    }

    /**
     *  Delete the  circle by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Circle : {}", id);
        circleRepository.delete(id);
        circleSearchRepository.delete(id);
    }

    /**
     * Search for the circle corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CircleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Circles for query {}", query);
        Page<Circle> result = circleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(circleMapper::toDto);
    }
}
