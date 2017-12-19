package app.group.up.web.rest;

import app.group.up.BlogApp;

import app.group.up.domain.EnrollmentHistory;
import app.group.up.repository.EnrollmentHistoryRepository;
import app.group.up.service.EnrollmentHistoryService;
import app.group.up.repository.search.EnrollmentHistorySearchRepository;
import app.group.up.service.dto.EnrollmentHistoryDTO;
import app.group.up.service.mapper.EnrollmentHistoryMapper;
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

import app.group.up.domain.enumeration.Language;
/**
 * Test class for the EnrollmentHistoryResource REST controller.
 *
 * @see EnrollmentHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
public class EnrollmentHistoryResourceIntTest {

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    @Autowired
    private EnrollmentHistoryRepository enrollmentHistoryRepository;

    @Autowired
    private EnrollmentHistoryMapper enrollmentHistoryMapper;

    @Autowired
    private EnrollmentHistoryService enrollmentHistoryService;

    @Autowired
    private EnrollmentHistorySearchRepository enrollmentHistorySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnrollmentHistoryMockMvc;

    private EnrollmentHistory enrollmentHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnrollmentHistoryResource enrollmentHistoryResource = new EnrollmentHistoryResource(enrollmentHistoryService);
        this.restEnrollmentHistoryMockMvc = MockMvcBuilders.standaloneSetup(enrollmentHistoryResource)
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
    public static EnrollmentHistory createEntity(EntityManager em) {
        EnrollmentHistory enrollmentHistory = new EnrollmentHistory()
            .language(DEFAULT_LANGUAGE);
        return enrollmentHistory;
    }

    @Before
    public void initTest() {
        enrollmentHistorySearchRepository.deleteAll();
        enrollmentHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnrollmentHistory() throws Exception {
        int databaseSizeBeforeCreate = enrollmentHistoryRepository.findAll().size();

        // Create the EnrollmentHistory
        EnrollmentHistoryDTO enrollmentHistoryDTO = enrollmentHistoryMapper.toDto(enrollmentHistory);
        restEnrollmentHistoryMockMvc.perform(post("/api/enrollment-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentHistory in the database
        List<EnrollmentHistory> enrollmentHistoryList = enrollmentHistoryRepository.findAll();
        assertThat(enrollmentHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        EnrollmentHistory testEnrollmentHistory = enrollmentHistoryList.get(enrollmentHistoryList.size() - 1);
        assertThat(testEnrollmentHistory.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);

        // Validate the EnrollmentHistory in Elasticsearch
        EnrollmentHistory enrollmentHistoryEs = enrollmentHistorySearchRepository.findOne(testEnrollmentHistory.getId());
        assertThat(enrollmentHistoryEs).isEqualToComparingFieldByField(testEnrollmentHistory);
    }

    @Test
    @Transactional
    public void createEnrollmentHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enrollmentHistoryRepository.findAll().size();

        // Create the EnrollmentHistory with an existing ID
        enrollmentHistory.setId(1L);
        EnrollmentHistoryDTO enrollmentHistoryDTO = enrollmentHistoryMapper.toDto(enrollmentHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnrollmentHistoryMockMvc.perform(post("/api/enrollment-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnrollmentHistory in the database
        List<EnrollmentHistory> enrollmentHistoryList = enrollmentHistoryRepository.findAll();
        assertThat(enrollmentHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnrollmentHistories() throws Exception {
        // Initialize the database
        enrollmentHistoryRepository.saveAndFlush(enrollmentHistory);

        // Get all the enrollmentHistoryList
        restEnrollmentHistoryMockMvc.perform(get("/api/enrollment-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void getEnrollmentHistory() throws Exception {
        // Initialize the database
        enrollmentHistoryRepository.saveAndFlush(enrollmentHistory);

        // Get the enrollmentHistory
        restEnrollmentHistoryMockMvc.perform(get("/api/enrollment-histories/{id}", enrollmentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enrollmentHistory.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnrollmentHistory() throws Exception {
        // Get the enrollmentHistory
        restEnrollmentHistoryMockMvc.perform(get("/api/enrollment-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnrollmentHistory() throws Exception {
        // Initialize the database
        enrollmentHistoryRepository.saveAndFlush(enrollmentHistory);
        enrollmentHistorySearchRepository.save(enrollmentHistory);
        int databaseSizeBeforeUpdate = enrollmentHistoryRepository.findAll().size();

        // Update the enrollmentHistory
        EnrollmentHistory updatedEnrollmentHistory = enrollmentHistoryRepository.findOne(enrollmentHistory.getId());
        updatedEnrollmentHistory
            .language(UPDATED_LANGUAGE);
        EnrollmentHistoryDTO enrollmentHistoryDTO = enrollmentHistoryMapper.toDto(updatedEnrollmentHistory);

        restEnrollmentHistoryMockMvc.perform(put("/api/enrollment-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the EnrollmentHistory in the database
        List<EnrollmentHistory> enrollmentHistoryList = enrollmentHistoryRepository.findAll();
        assertThat(enrollmentHistoryList).hasSize(databaseSizeBeforeUpdate);
        EnrollmentHistory testEnrollmentHistory = enrollmentHistoryList.get(enrollmentHistoryList.size() - 1);
        assertThat(testEnrollmentHistory.getLanguage()).isEqualTo(UPDATED_LANGUAGE);

        // Validate the EnrollmentHistory in Elasticsearch
        EnrollmentHistory enrollmentHistoryEs = enrollmentHistorySearchRepository.findOne(testEnrollmentHistory.getId());
        assertThat(enrollmentHistoryEs).isEqualToComparingFieldByField(testEnrollmentHistory);
    }

    @Test
    @Transactional
    public void updateNonExistingEnrollmentHistory() throws Exception {
        int databaseSizeBeforeUpdate = enrollmentHistoryRepository.findAll().size();

        // Create the EnrollmentHistory
        EnrollmentHistoryDTO enrollmentHistoryDTO = enrollmentHistoryMapper.toDto(enrollmentHistory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnrollmentHistoryMockMvc.perform(put("/api/enrollment-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enrollmentHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the EnrollmentHistory in the database
        List<EnrollmentHistory> enrollmentHistoryList = enrollmentHistoryRepository.findAll();
        assertThat(enrollmentHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEnrollmentHistory() throws Exception {
        // Initialize the database
        enrollmentHistoryRepository.saveAndFlush(enrollmentHistory);
        enrollmentHistorySearchRepository.save(enrollmentHistory);
        int databaseSizeBeforeDelete = enrollmentHistoryRepository.findAll().size();

        // Get the enrollmentHistory
        restEnrollmentHistoryMockMvc.perform(delete("/api/enrollment-histories/{id}", enrollmentHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean enrollmentHistoryExistsInEs = enrollmentHistorySearchRepository.exists(enrollmentHistory.getId());
        assertThat(enrollmentHistoryExistsInEs).isFalse();

        // Validate the database is empty
        List<EnrollmentHistory> enrollmentHistoryList = enrollmentHistoryRepository.findAll();
        assertThat(enrollmentHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEnrollmentHistory() throws Exception {
        // Initialize the database
        enrollmentHistoryRepository.saveAndFlush(enrollmentHistory);
        enrollmentHistorySearchRepository.save(enrollmentHistory);

        // Search the enrollmentHistory
        restEnrollmentHistoryMockMvc.perform(get("/api/_search/enrollment-histories?query=id:" + enrollmentHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enrollmentHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentHistory.class);
        EnrollmentHistory enrollmentHistory1 = new EnrollmentHistory();
        enrollmentHistory1.setId(1L);
        EnrollmentHistory enrollmentHistory2 = new EnrollmentHistory();
        enrollmentHistory2.setId(enrollmentHistory1.getId());
        assertThat(enrollmentHistory1).isEqualTo(enrollmentHistory2);
        enrollmentHistory2.setId(2L);
        assertThat(enrollmentHistory1).isNotEqualTo(enrollmentHistory2);
        enrollmentHistory1.setId(null);
        assertThat(enrollmentHistory1).isNotEqualTo(enrollmentHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnrollmentHistoryDTO.class);
        EnrollmentHistoryDTO enrollmentHistoryDTO1 = new EnrollmentHistoryDTO();
        enrollmentHistoryDTO1.setId(1L);
        EnrollmentHistoryDTO enrollmentHistoryDTO2 = new EnrollmentHistoryDTO();
        assertThat(enrollmentHistoryDTO1).isNotEqualTo(enrollmentHistoryDTO2);
        enrollmentHistoryDTO2.setId(enrollmentHistoryDTO1.getId());
        assertThat(enrollmentHistoryDTO1).isEqualTo(enrollmentHistoryDTO2);
        enrollmentHistoryDTO2.setId(2L);
        assertThat(enrollmentHistoryDTO1).isNotEqualTo(enrollmentHistoryDTO2);
        enrollmentHistoryDTO1.setId(null);
        assertThat(enrollmentHistoryDTO1).isNotEqualTo(enrollmentHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(enrollmentHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(enrollmentHistoryMapper.fromId(null)).isNull();
    }
}
