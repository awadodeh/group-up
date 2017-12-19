package app.group.up.service.impl;

import app.group.up.service.EnrollmentsService;
import app.group.up.domain.Enrollments;
import app.group.up.repository.EnrollmentsRepository;
import app.group.up.repository.search.EnrollmentsSearchRepository;
import app.group.up.service.dto.EnrollmentsDTO;
import app.group.up.service.mapper.EnrollmentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Enrollments.
 */
@Service
@Transactional
public class EnrollmentsServiceImpl implements EnrollmentsService{

    private final Logger log = LoggerFactory.getLogger(EnrollmentsServiceImpl.class);

    private final EnrollmentsRepository enrollmentsRepository;

    private final EnrollmentsMapper enrollmentsMapper;

    private final EnrollmentsSearchRepository enrollmentsSearchRepository;

    public EnrollmentsServiceImpl(EnrollmentsRepository enrollmentsRepository, EnrollmentsMapper enrollmentsMapper, EnrollmentsSearchRepository enrollmentsSearchRepository) {
        this.enrollmentsRepository = enrollmentsRepository;
        this.enrollmentsMapper = enrollmentsMapper;
        this.enrollmentsSearchRepository = enrollmentsSearchRepository;
    }

    /**
     * Save a enrollments.
     *
     * @param enrollmentsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnrollmentsDTO save(EnrollmentsDTO enrollmentsDTO) {
        log.debug("Request to save Enrollments : {}", enrollmentsDTO);
        Enrollments enrollments = enrollmentsMapper.toEntity(enrollmentsDTO);
        enrollments = enrollmentsRepository.save(enrollments);
        EnrollmentsDTO result = enrollmentsMapper.toDto(enrollments);
        enrollmentsSearchRepository.save(enrollments);
        return result;
    }

    /**
     *  Get all the enrollments.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentsDTO> findAll() {
        log.debug("Request to get all Enrollments");
        return enrollmentsRepository.findAll().stream()
            .map(enrollmentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one enrollments by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EnrollmentsDTO findOne(Long id) {
        log.debug("Request to get Enrollments : {}", id);
        Enrollments enrollments = enrollmentsRepository.findOne(id);
        return enrollmentsMapper.toDto(enrollments);
    }

    /**
     *  Delete the  enrollments by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enrollments : {}", id);
        enrollmentsRepository.delete(id);
        enrollmentsSearchRepository.delete(id);
    }

    /**
     * Search for the enrollments corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentsDTO> search(String query) {
        log.debug("Request to search Enrollments for query {}", query);
        return StreamSupport
            .stream(enrollmentsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(enrollmentsMapper::toDto)
            .collect(Collectors.toList());
    }
}
