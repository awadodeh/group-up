package app.group.up.service.impl;

import app.group.up.service.ReviewService;
import app.group.up.domain.Review;
import app.group.up.repository.ReviewRepository;
import app.group.up.repository.search.ReviewSearchRepository;
import app.group.up.service.dto.ReviewDTO;
import app.group.up.service.mapper.ReviewMapper;
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
 * Service Implementation for managing Review.
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;

    private final ReviewMapper reviewMapper;

    private final ReviewSearchRepository reviewSearchRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMapper reviewMapper, ReviewSearchRepository reviewSearchRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.reviewSearchRepository = reviewSearchRepository;
    }

    /**
     * Save a review.
     *
     * @param reviewDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReviewDTO save(ReviewDTO reviewDTO) {
        log.debug("Request to save Review : {}", reviewDTO);
        Review review = reviewMapper.toEntity(reviewDTO);
        review = reviewRepository.save(review);
        ReviewDTO result = reviewMapper.toDto(review);
        reviewSearchRepository.save(review);
        return result;
    }

    /**
     *  Get all the reviews.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        log.debug("Request to get all Reviews");
        return reviewRepository.findAll().stream()
            .map(reviewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one review by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReviewDTO findOne(Long id) {
        log.debug("Request to get Review : {}", id);
        Review review = reviewRepository.findOne(id);
        return reviewMapper.toDto(review);
    }

    /**
     *  Delete the  review by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        reviewRepository.delete(id);
        reviewSearchRepository.delete(id);
    }

    /**
     * Search for the review corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDTO> search(String query) {
        log.debug("Request to search Reviews for query {}", query);
        return StreamSupport
            .stream(reviewSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(reviewMapper::toDto)
            .collect(Collectors.toList());
    }
}
