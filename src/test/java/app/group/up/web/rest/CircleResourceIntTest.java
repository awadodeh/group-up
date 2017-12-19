package app.group.up.web.rest;

import app.group.up.BlogApp;

import app.group.up.domain.Circle;
import app.group.up.repository.CircleRepository;
import app.group.up.service.CircleService;
import app.group.up.repository.search.CircleSearchRepository;
import app.group.up.service.dto.CircleDTO;
import app.group.up.service.mapper.CircleMapper;
import app.group.up.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static app.group.up.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CircleResource REST controller.
 *
 * @see CircleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
public class CircleResourceIntTest {

    private static final String DEFAULT_CIRCLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CIRCLE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_CIRCLE_WORTH = 1L;
    private static final Long UPDATED_CIRCLE_WORTH = 2L;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_NUMBER_OF_MEMBERS = 1;
    private static final Integer UPDATED_NUMBER_OF_MEMBERS = 2;

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private CircleMapper circleMapper;

    @Autowired
    private CircleService circleService;

    @Autowired
    private CircleSearchRepository circleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCircleMockMvc;

    private Circle circle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CircleResource circleResource = new CircleResource(circleService);
        this.restCircleMockMvc = MockMvcBuilders.standaloneSetup(circleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Circle createEntity(EntityManager em) {
        Circle circle = new Circle()
            .circleName(DEFAULT_CIRCLE_NAME)
            .circleWorth(DEFAULT_CIRCLE_WORTH)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .numberOfMembers(DEFAULT_NUMBER_OF_MEMBERS);
        return circle;
    }

    @Before
    public void initTest() {
        circleSearchRepository.deleteAll();
        circle = createEntity(em);
    }

    @Test
    @Transactional
    public void createCircle() throws Exception {
        int databaseSizeBeforeCreate = circleRepository.findAll().size();

        // Create the Circle
        CircleDTO circleDTO = circleMapper.toDto(circle);
        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isCreated());

        // Validate the Circle in the database
        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeCreate + 1);
        Circle testCircle = circleList.get(circleList.size() - 1);
        assertThat(testCircle.getCircleName()).isEqualTo(DEFAULT_CIRCLE_NAME);
        assertThat(testCircle.getCircleWorth()).isEqualTo(DEFAULT_CIRCLE_WORTH);
        assertThat(testCircle.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCircle.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testCircle.getNumberOfMembers()).isEqualTo(DEFAULT_NUMBER_OF_MEMBERS);

        // Validate the Circle in Elasticsearch
        Circle circleEs = circleSearchRepository.findOne(testCircle.getId());
        assertThat(circleEs).isEqualToComparingFieldByField(testCircle);
    }

    @Test
    @Transactional
    public void createCircleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = circleRepository.findAll().size();

        // Create the Circle with an existing ID
        circle.setId(1L);
        CircleDTO circleDTO = circleMapper.toDto(circle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Circle in the database
        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCircleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = circleRepository.findAll().size();
        // set the field null
        circle.setCircleName(null);

        // Create the Circle, which fails.
        CircleDTO circleDTO = circleMapper.toDto(circle);

        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isBadRequest());

        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCircleWorthIsRequired() throws Exception {
        int databaseSizeBeforeTest = circleRepository.findAll().size();
        // set the field null
        circle.setCircleWorth(null);

        // Create the Circle, which fails.
        CircleDTO circleDTO = circleMapper.toDto(circle);

        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isBadRequest());

        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = circleRepository.findAll().size();
        // set the field null
        circle.setStartDate(null);

        // Create the Circle, which fails.
        CircleDTO circleDTO = circleMapper.toDto(circle);

        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isBadRequest());

        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = circleRepository.findAll().size();
        // set the field null
        circle.setEndDate(null);

        // Create the Circle, which fails.
        CircleDTO circleDTO = circleMapper.toDto(circle);

        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isBadRequest());

        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfMembersIsRequired() throws Exception {
        int databaseSizeBeforeTest = circleRepository.findAll().size();
        // set the field null
        circle.setNumberOfMembers(null);

        // Create the Circle, which fails.
        CircleDTO circleDTO = circleMapper.toDto(circle);

        restCircleMockMvc.perform(post("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isBadRequest());

        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCircles() throws Exception {
        // Initialize the database
        circleRepository.saveAndFlush(circle);

        // Get all the circleList
        restCircleMockMvc.perform(get("/api/circles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(circle.getId().intValue())))
            .andExpect(jsonPath("$.[*].circleName").value(hasItem(DEFAULT_CIRCLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].circleWorth").value(hasItem(DEFAULT_CIRCLE_WORTH.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].numberOfMembers").value(hasItem(DEFAULT_NUMBER_OF_MEMBERS)));
    }

    @Test
    @Transactional
    public void getCircle() throws Exception {
        // Initialize the database
        circleRepository.saveAndFlush(circle);

        // Get the circle
        restCircleMockMvc.perform(get("/api/circles/{id}", circle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(circle.getId().intValue()))
            .andExpect(jsonPath("$.circleName").value(DEFAULT_CIRCLE_NAME.toString()))
            .andExpect(jsonPath("$.circleWorth").value(DEFAULT_CIRCLE_WORTH.intValue()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.numberOfMembers").value(DEFAULT_NUMBER_OF_MEMBERS));
    }

    @Test
    @Transactional
    public void getNonExistingCircle() throws Exception {
        // Get the circle
        restCircleMockMvc.perform(get("/api/circles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCircle() throws Exception {
        // Initialize the database
        circleRepository.saveAndFlush(circle);
        circleSearchRepository.save(circle);
        int databaseSizeBeforeUpdate = circleRepository.findAll().size();

        // Update the circle
        Circle updatedCircle = circleRepository.findOne(circle.getId());
        updatedCircle
            .circleName(UPDATED_CIRCLE_NAME)
            .circleWorth(UPDATED_CIRCLE_WORTH)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .numberOfMembers(UPDATED_NUMBER_OF_MEMBERS);
        CircleDTO circleDTO = circleMapper.toDto(updatedCircle);

        restCircleMockMvc.perform(put("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isOk());

        // Validate the Circle in the database
        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeUpdate);
        Circle testCircle = circleList.get(circleList.size() - 1);
        assertThat(testCircle.getCircleName()).isEqualTo(UPDATED_CIRCLE_NAME);
        assertThat(testCircle.getCircleWorth()).isEqualTo(UPDATED_CIRCLE_WORTH);
        assertThat(testCircle.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCircle.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testCircle.getNumberOfMembers()).isEqualTo(UPDATED_NUMBER_OF_MEMBERS);

        // Validate the Circle in Elasticsearch
        Circle circleEs = circleSearchRepository.findOne(testCircle.getId());
        assertThat(circleEs).isEqualToComparingFieldByField(testCircle);
    }

    @Test
    @Transactional
    public void updateNonExistingCircle() throws Exception {
        int databaseSizeBeforeUpdate = circleRepository.findAll().size();

        // Create the Circle
        CircleDTO circleDTO = circleMapper.toDto(circle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCircleMockMvc.perform(put("/api/circles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(circleDTO)))
            .andExpect(status().isCreated());

        // Validate the Circle in the database
        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCircle() throws Exception {
        // Initialize the database
        circleRepository.saveAndFlush(circle);
        circleSearchRepository.save(circle);
        int databaseSizeBeforeDelete = circleRepository.findAll().size();

        // Get the circle
        restCircleMockMvc.perform(delete("/api/circles/{id}", circle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean circleExistsInEs = circleSearchRepository.exists(circle.getId());
        assertThat(circleExistsInEs).isFalse();

        // Validate the database is empty
        List<Circle> circleList = circleRepository.findAll();
        assertThat(circleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCircle() throws Exception {
        // Initialize the database
        circleRepository.saveAndFlush(circle);
        circleSearchRepository.save(circle);

        // Search the circle
        restCircleMockMvc.perform(get("/api/_search/circles?query=id:" + circle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(circle.getId().intValue())))
            .andExpect(jsonPath("$.[*].circleName").value(hasItem(DEFAULT_CIRCLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].circleWorth").value(hasItem(DEFAULT_CIRCLE_WORTH.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].numberOfMembers").value(hasItem(DEFAULT_NUMBER_OF_MEMBERS)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Circle.class);
        Circle circle1 = new Circle();
        circle1.setId(1L);
        Circle circle2 = new Circle();
        circle2.setId(circle1.getId());
        assertThat(circle1).isEqualTo(circle2);
        circle2.setId(2L);
        assertThat(circle1).isNotEqualTo(circle2);
        circle1.setId(null);
        assertThat(circle1).isNotEqualTo(circle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CircleDTO.class);
        CircleDTO circleDTO1 = new CircleDTO();
        circleDTO1.setId(1L);
        CircleDTO circleDTO2 = new CircleDTO();
        assertThat(circleDTO1).isNotEqualTo(circleDTO2);
        circleDTO2.setId(circleDTO1.getId());
        assertThat(circleDTO1).isEqualTo(circleDTO2);
        circleDTO2.setId(2L);
        assertThat(circleDTO1).isNotEqualTo(circleDTO2);
        circleDTO1.setId(null);
        assertThat(circleDTO1).isNotEqualTo(circleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(circleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(circleMapper.fromId(null)).isNull();
    }
}
