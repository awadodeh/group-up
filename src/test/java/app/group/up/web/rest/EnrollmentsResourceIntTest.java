package app.group.up.web.rest;

import app.group.up.BlogApp;

import app.group.up.domain.Enrollments;
import app.group.up.repository.EnrollmentsRepository;
import app.group.up.service.EnrollmentsService;
import app.group.up.repository.search.EnrollmentsSearchRepository;
import app.group.up.service.dto.EnrollmentsDTO;
import app.group.up.service.mapper.EnrollmentsMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnrollmentsResource REST controller.
 *
 * @see EnrollmentsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
public class EnrollmentsResourceIntTest {

    private static final String DEFAULT_PERSON_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CIRCLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CIRCLE_ID = "BBBBBBBBBB";

    @Autowired
    private EnrollmentsRepository enrollmentsRepository;

    @Autowired
    private EnrollmentsMapper enrollmentsMapper;

    @Autowired
    private EnrollmentsService enrollmentsService;

    @Autowired
    private EnrollmentsSearchRepository enrollmentsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnrollmentsMockMvc;

    private Enrollments enrollments;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnrollmentsResource enrollmentsResource = new EnrollmentsResource(enrollmentsService);
        this.restEnrollmentsMockMvc = MockMvcBuilders.standaloneSetup(enrollmentsResource)
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
    public static Enrollments createEntity(EntityManager em) {
        Enrollments enrollments = new Enrollments()
            .personId(DEFAULT_PERSON_ID)
            .circleId(DEFAULT_CIRCLE_ID);
        return enrollments;
    }

    @Before
    public void initTest() {
        enrollmentsSearchRepository.deleteAll();
        enrollments = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollments() throws Exception {
        int databaseSizeBeforeCreate = enrollmentsRepository.findAll().size();

        // Create the Enrollments
        EnrollmentsDTO enrollmentsDTO = enrollmentsMapper.toDto(enrollments);
        restEnrollmentsMockMvc.perform(post("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Enrollments in the database
        List<Enrollments> enrollmentsList = enrollmentsRepository.findAll();
        assertThat(enrollmentsList).hasSize(databaseSizeBeforeCreate + 1);
        Enrollments testEnrollments = enrollmentsList.get(enrollmentsList.size() - 1);
        assertThat(testEnrollments.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testEnrollments.getCircleId()).isEqualTo(DEFAULT_CIRCLE_ID);

        // Validate the Enrollments in Elasticsearch
        Enrollments enrollmentsEs = enrollmentsSearchRepository.findOne(testEnrollments.getId());
        assertThat(enrollmentsEs).isEqualToComparingFieldByField(testEnrollments);
    }

    @Test
    @Transactional
    public void createEnrollmentsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentsRepository.findAll().size();

        // Create the Enrollments with an existing ID
        enrollments.setId(1L);
        EnrollmentsDTO enrollmentsDTO = enrollmentsMapper.toDto(enrollments);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentsMockMvc.perform(post("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enrollments in the database
        List<Enrollments> enrollmentsList = enrollmentsRepository.findAll();
        assertThat(enrollmentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnrollments() throws Exception {
        // Initialize the database
        enrollmentsRepository.saveAndFlush(enrollments);

        // Get all the enrollmentsList
        restEnrollmentsMockMvc.perform(get("/api/enrollments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollments.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.toString())))
            .andExpect(jsonPath("$.[*].circleId").value(hasItem(DEFAULT_CIRCLE_ID.toString())));
    }

    @Test
    @Transactional
    public void getEnrollments() throws Exception {
        // Initialize the database
        enrollmentsRepository.saveAndFlush(enrollments);

        // Get the enrollments
        restEnrollmentsMockMvc.perform(get("/api/enrollments/{id}", enrollments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enrollments.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.toString()))
            .andExpect(jsonPath("$.circleId").value(DEFAULT_CIRCLE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnrollments() throws Exception {
        // Get the enrollments
        restEnrollmentsMockMvc.perform(get("/api/enrollments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollments() throws Exception {
        // Initialize the database
        enrollmentsRepository.saveAndFlush(enrollments);
        enrollmentsSearchRepository.save(enrollments);
        int databaseSizeBeforeUpdate = enrollmentsRepository.findAll().size();

        // Update the enrollments
        Enrollments updatedEnrollments = enrollmentsRepository.findOne(enrollments.getId());
        updatedEnrollments
            .personId(UPDATED_PERSON_ID)
            .circleId(UPDATED_CIRCLE_ID);
        EnrollmentsDTO enrollmentsDTO = enrollmentsMapper.toDto(updatedEnrollments);

        restEnrollmentsMockMvc.perform(put("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentsDTO)))
            .andExpect(status().isOk());

        // Validate the Enrollments in the database
        List<Enrollments> enrollmentsList = enrollmentsRepository.findAll();
        assertThat(enrollmentsList).hasSize(databaseSizeBeforeUpdate);
        Enrollments testEnrollments = enrollmentsList.get(enrollmentsList.size() - 1);
        assertThat(testEnrollments.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testEnrollments.getCircleId()).isEqualTo(UPDATED_CIRCLE_ID);

        // Validate the Enrollments in Elasticsearch
        Enrollments enrollmentsEs = enrollmentsSearchRepository.findOne(testEnrollments.getId());
        assertThat(enrollmentsEs).isEqualToComparingFieldByField(testEnrollments);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollments() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentsRepository.findAll().size();

        // Create the Enrollments
        EnrollmentsDTO enrollmentsDTO = enrollmentsMapper.toDto(enrollments);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnrollmentsMockMvc.perform(put("/api/enrollments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Enrollments in the database
        List<Enrollments> enrollmentsList = enrollmentsRepository.findAll();
        assertThat(enrollmentsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEnrollments() throws Exception {
        // Initialize the database
        enrollmentsRepository.saveAndFlush(enrollments);
        enrollmentsSearchRepository.save(enrollments);
        int databaseSizeBeforeDelete = enrollmentsRepository.findAll().size();

        // Get the enrollments
        restEnrollmentsMockMvc.perform(delete("/api/enrollments/{id}", enrollments.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean enrollmentsExistsInEs = enrollmentsSearchRepository.exists(enrollments.getId());
        assertThat(enrollmentsExistsInEs).isFalse();

        // Validate the database is empty
        List<Enrollments> enrollmentsList = enrollmentsRepository.findAll();
        assertThat(enrollmentsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnrollments() throws Exception {
        // Initialize the database
        enrollmentsRepository.saveAndFlush(enrollments);
        enrollmentsSearchRepository.save(enrollments);

        // Search the enrollments
        restEnrollmentsMockMvc.perform(get("/api/_search/enrollments?query=id:" + enrollments.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollments.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.toString())))
            .andExpect(jsonPath("$.[*].circleId").value(hasItem(DEFAULT_CIRCLE_ID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enrollments.class);
        Enrollments enrollments1 = new Enrollments();
        enrollments1.setId(1L);
        Enrollments enrollments2 = new Enrollments();
        enrollments2.setId(enrollments1.getId());
        assertThat(enrollments1).isEqualTo(enrollments2);
        enrollments2.setId(2L);
        assertThat(enrollments1).isNotEqualTo(enrollments2);
        enrollments1.setId(null);
        assertThat(enrollments1).isNotEqualTo(enrollments2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentsDTO.class);
        EnrollmentsDTO enrollmentsDTO1 = new EnrollmentsDTO();
        enrollmentsDTO1.setId(1L);
        EnrollmentsDTO enrollmentsDTO2 = new EnrollmentsDTO();
        assertThat(enrollmentsDTO1).isNotEqualTo(enrollmentsDTO2);
        enrollmentsDTO2.setId(enrollmentsDTO1.getId());
        assertThat(enrollmentsDTO1).isEqualTo(enrollmentsDTO2);
        enrollmentsDTO2.setId(2L);
        assertThat(enrollmentsDTO1).isNotEqualTo(enrollmentsDTO2);
        enrollmentsDTO1.setId(null);
        assertThat(enrollmentsDTO1).isNotEqualTo(enrollmentsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enrollmentsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enrollmentsMapper.fromId(null)).isNull();
    }
}
