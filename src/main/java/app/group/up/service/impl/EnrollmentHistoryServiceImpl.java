package app.group.up.service.impl;

import app.group.up.service.EnrollmentHistoryService;
import app.group.up.domain.EnrollmentHistory;
import app.group.up.repository.EnrollmentHistoryRepository;
import app.group.up.repository.search.EnrollmentHistorySearchRepository;
import app.group.up.service.dto.EnrollmentHistoryDTO;
import app.group.up.service.mapper.EnrollmentHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EnrollmentHistory.
 */
@Service
@Transactional
public class EnrollmentHistoryServiceImpl implements EnrollmentHistoryService{

    private final Logger log = LoggerFactory.getLogger(EnrollmentHistoryServiceImpl.class);

    private final EnrollmentHistoryRepository enrollmentHistoryRepository;

    private final EnrollmentHistoryMapper enrollmentHistoryMapper;

    private final EnrollmentHistorySearchRepository enrollmentHistorySearchRepository;

    public EnrollmentHistoryServiceImpl(EnrollmentHistoryRepository enrollmentHistoryRepository, EnrollmentHistoryMapper enrollmentHistoryMapper, EnrollmentHistorySearchRepository enrollmentHistorySearchRepository) {
        this.enrollmentHistoryRepository = enrollmentHistoryRepository;
        this.enrollmentHistoryMapper = enrollmentHistoryMapper;
        this.enrollmentHistorySearchRepository = enrollmentHistorySearchRepository;
    }

    /**
     * Save a enrollmentHistory.
     *
     * @param enrollmentHistoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnrollmentHistoryDTO save(EnrollmentHistoryDTO enrollmentHistoryDTO) {
        log.debug("Request to save EnrollmentHistory : {}", enrollmentHistoryDTO);
        EnrollmentHistory enrollmentHistory = enrollmentHistoryMapper.toEntity(enrollmentHistoryDTO);
        enrollmentHistory = enrollmentHistoryRepository.save(enrollmentHistory);
        EnrollmentHistoryDTO result = enrollmentHistoryMapper.toDto(enrollmentHistory);
        enrollmentHistorySearchRepository.save(enrollmentHistory);
        return result;
    }

    /**
     *  Get all the enrollmentHistories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnrollmentHistories");
        return enrollmentHistoryRepository.findAll(pageable)
            .map(enrollmentHistoryMapper::toDto);
    }

    /**
     *  Get one enrollmentHistory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EnrollmentHistoryDTO findOne(Long id) {
        log.debug("Request to get EnrollmentHistory : {}", id);
        EnrollmentHistory enrollmentHistory = enrollmentHistoryRepository.findOne(id);
        return enrollmentHistoryMapper.toDto(enrollmentHistory);
    }

    /**
     *  Delete the  enrollmentHistory by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EnrollmentHistory : {}", id);
        enrollmentHistoryRepository.delete(id);
        enrollmentHistorySearchRepository.delete(id);
    }

    /**
     * Search for the enrollmentHistory corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnrollmentHistoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EnrollmentHistories for query {}", query);
        Page<EnrollmentHistory> result = enrollmentHistorySearchRepository.search(queryStringQuery(query), pageable);
        return result.map(enrollmentHistoryMapper::toDto);
    }
}
