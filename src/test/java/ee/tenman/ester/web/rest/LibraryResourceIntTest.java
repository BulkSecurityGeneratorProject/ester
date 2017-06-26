package ee.tenman.ester.web.rest;

import ee.tenman.ester.EsterApp;

import ee.tenman.ester.domain.Library;
import ee.tenman.ester.repository.LibraryRepository;
import ee.tenman.ester.service.LibraryService;
import ee.tenman.ester.service.dto.LibraryDTO;
import ee.tenman.ester.service.mapper.LibraryMapper;
import ee.tenman.ester.web.rest.errors.ExceptionTranslator;

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
 * Test class for the LibraryResource REST controller.
 *
 * @see LibraryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsterApp.class)
public class LibraryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private LibraryMapper libraryMapper;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLibraryMockMvc;

    private Library library;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LibraryResource libraryResource = new LibraryResource(libraryService);
        this.restLibraryMockMvc = MockMvcBuilders.standaloneSetup(libraryResource)
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
    public static Library createEntity(EntityManager em) {
        Library library = new Library()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .contact(DEFAULT_CONTACT);
        return library;
    }

    @Before
    public void initTest() {
        library = createEntity(em);
    }

    @Test
    @Transactional
    public void createLibrary() throws Exception {
        int databaseSizeBeforeCreate = libraryRepository.findAll().size();

        // Create the Library
        LibraryDTO libraryDTO = libraryMapper.toDto(library);
        restLibraryMockMvc.perform(post("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isCreated());

        // Validate the Library in the database
        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeCreate + 1);
        Library testLibrary = libraryList.get(libraryList.size() - 1);
        assertThat(testLibrary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLibrary.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testLibrary.getContact()).isEqualTo(DEFAULT_CONTACT);
    }

    @Test
    @Transactional
    public void createLibraryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = libraryRepository.findAll().size();

        // Create the Library with an existing ID
        library.setId(1L);
        LibraryDTO libraryDTO = libraryMapper.toDto(library);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLibraryMockMvc.perform(post("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRepository.findAll().size();
        // set the field null
        library.setName(null);

        // Create the Library, which fails.
        LibraryDTO libraryDTO = libraryMapper.toDto(library);

        restLibraryMockMvc.perform(post("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isBadRequest());

        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRepository.findAll().size();
        // set the field null
        library.setAddress(null);

        // Create the Library, which fails.
        LibraryDTO libraryDTO = libraryMapper.toDto(library);

        restLibraryMockMvc.perform(post("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isBadRequest());

        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = libraryRepository.findAll().size();
        // set the field null
        library.setContact(null);

        // Create the Library, which fails.
        LibraryDTO libraryDTO = libraryMapper.toDto(library);

        restLibraryMockMvc.perform(post("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isBadRequest());

        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLibraries() throws Exception {
        // Initialize the database
        libraryRepository.saveAndFlush(library);

        // Get all the libraryList
        restLibraryMockMvc.perform(get("/api/libraries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(library.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())));
    }

    @Test
    @Transactional
    public void getLibrary() throws Exception {
        // Initialize the database
        libraryRepository.saveAndFlush(library);

        // Get the library
        restLibraryMockMvc.perform(get("/api/libraries/{id}", library.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(library.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLibrary() throws Exception {
        // Get the library
        restLibraryMockMvc.perform(get("/api/libraries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLibrary() throws Exception {
        // Initialize the database
        libraryRepository.saveAndFlush(library);
        int databaseSizeBeforeUpdate = libraryRepository.findAll().size();

        // Update the library
        Library updatedLibrary = libraryRepository.findOne(library.getId());
        updatedLibrary
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .contact(UPDATED_CONTACT);
        LibraryDTO libraryDTO = libraryMapper.toDto(updatedLibrary);

        restLibraryMockMvc.perform(put("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isOk());

        // Validate the Library in the database
        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeUpdate);
        Library testLibrary = libraryList.get(libraryList.size() - 1);
        assertThat(testLibrary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLibrary.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testLibrary.getContact()).isEqualTo(UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void updateNonExistingLibrary() throws Exception {
        int databaseSizeBeforeUpdate = libraryRepository.findAll().size();

        // Create the Library
        LibraryDTO libraryDTO = libraryMapper.toDto(library);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLibraryMockMvc.perform(put("/api/libraries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(libraryDTO)))
            .andExpect(status().isCreated());

        // Validate the Library in the database
        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLibrary() throws Exception {
        // Initialize the database
        libraryRepository.saveAndFlush(library);
        int databaseSizeBeforeDelete = libraryRepository.findAll().size();

        // Get the library
        restLibraryMockMvc.perform(delete("/api/libraries/{id}", library.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Library> libraryList = libraryRepository.findAll();
        assertThat(libraryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Library.class);
        Library library1 = new Library();
        library1.setId(1L);
        Library library2 = new Library();
        library2.setId(library1.getId());
        assertThat(library1).isEqualTo(library2);
        library2.setId(2L);
        assertThat(library1).isNotEqualTo(library2);
        library1.setId(null);
        assertThat(library1).isNotEqualTo(library2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LibraryDTO.class);
        LibraryDTO libraryDTO1 = new LibraryDTO();
        libraryDTO1.setId(1L);
        LibraryDTO libraryDTO2 = new LibraryDTO();
        assertThat(libraryDTO1).isNotEqualTo(libraryDTO2);
        libraryDTO2.setId(libraryDTO1.getId());
        assertThat(libraryDTO1).isEqualTo(libraryDTO2);
        libraryDTO2.setId(2L);
        assertThat(libraryDTO1).isNotEqualTo(libraryDTO2);
        libraryDTO1.setId(null);
        assertThat(libraryDTO1).isNotEqualTo(libraryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(libraryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(libraryMapper.fromId(null)).isNull();
    }
}
