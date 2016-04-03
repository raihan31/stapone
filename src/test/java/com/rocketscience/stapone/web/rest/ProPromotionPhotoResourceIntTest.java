package com.rocketscience.stapone.web.rest;

import com.rocketscience.stapone.Application;
import com.rocketscience.stapone.domain.ProPromotionPhoto;
import com.rocketscience.stapone.repository.ProPromotionPhotoRepository;
import com.rocketscience.stapone.repository.search.ProPromotionPhotoSearchRepository;
import com.rocketscience.stapone.web.rest.dto.ProPromotionPhotoDTO;
import com.rocketscience.stapone.web.rest.mapper.ProPromotionPhotoMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProPromotionPhotoResource REST controller.
 *
 * @see ProPromotionPhotoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProPromotionPhotoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_DESCRIPTIONS = "AAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBB";

    @Inject
    private ProPromotionPhotoRepository proPromotionPhotoRepository;

    @Inject
    private ProPromotionPhotoMapper proPromotionPhotoMapper;

    @Inject
    private ProPromotionPhotoSearchRepository proPromotionPhotoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProPromotionPhotoMockMvc;

    private ProPromotionPhoto proPromotionPhoto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProPromotionPhotoResource proPromotionPhotoResource = new ProPromotionPhotoResource();
        ReflectionTestUtils.setField(proPromotionPhotoResource, "proPromotionPhotoSearchRepository", proPromotionPhotoSearchRepository);
        ReflectionTestUtils.setField(proPromotionPhotoResource, "proPromotionPhotoRepository", proPromotionPhotoRepository);
        ReflectionTestUtils.setField(proPromotionPhotoResource, "proPromotionPhotoMapper", proPromotionPhotoMapper);
        this.restProPromotionPhotoMockMvc = MockMvcBuilders.standaloneSetup(proPromotionPhotoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        proPromotionPhoto = new ProPromotionPhoto();
        proPromotionPhoto.setName(DEFAULT_NAME);
        proPromotionPhoto.setPhoto(DEFAULT_PHOTO);
        proPromotionPhoto.setPhotoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        proPromotionPhoto.setDescriptions(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void createProPromotionPhoto() throws Exception {
        int databaseSizeBeforeCreate = proPromotionPhotoRepository.findAll().size();

        // Create the ProPromotionPhoto
        ProPromotionPhotoDTO proPromotionPhotoDTO = proPromotionPhotoMapper.proPromotionPhotoToProPromotionPhotoDTO(proPromotionPhoto);

        restProPromotionPhotoMockMvc.perform(post("/api/proPromotionPhotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proPromotionPhotoDTO)))
                .andExpect(status().isCreated());

        // Validate the ProPromotionPhoto in the database
        List<ProPromotionPhoto> proPromotionPhotos = proPromotionPhotoRepository.findAll();
        assertThat(proPromotionPhotos).hasSize(databaseSizeBeforeCreate + 1);
        ProPromotionPhoto testProPromotionPhoto = proPromotionPhotos.get(proPromotionPhotos.size() - 1);
        assertThat(testProPromotionPhoto.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProPromotionPhoto.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testProPromotionPhoto.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testProPromotionPhoto.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void getAllProPromotionPhotos() throws Exception {
        // Initialize the database
        proPromotionPhotoRepository.saveAndFlush(proPromotionPhoto);

        // Get all the proPromotionPhotos
        restProPromotionPhotoMockMvc.perform(get("/api/proPromotionPhotos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proPromotionPhoto.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
                .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())));
    }

    @Test
    @Transactional
    public void getProPromotionPhoto() throws Exception {
        // Initialize the database
        proPromotionPhotoRepository.saveAndFlush(proPromotionPhoto);

        // Get the proPromotionPhoto
        restProPromotionPhotoMockMvc.perform(get("/api/proPromotionPhotos/{id}", proPromotionPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(proPromotionPhoto.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProPromotionPhoto() throws Exception {
        // Get the proPromotionPhoto
        restProPromotionPhotoMockMvc.perform(get("/api/proPromotionPhotos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProPromotionPhoto() throws Exception {
        // Initialize the database
        proPromotionPhotoRepository.saveAndFlush(proPromotionPhoto);

		int databaseSizeBeforeUpdate = proPromotionPhotoRepository.findAll().size();

        // Update the proPromotionPhoto
        proPromotionPhoto.setName(UPDATED_NAME);
        proPromotionPhoto.setPhoto(UPDATED_PHOTO);
        proPromotionPhoto.setPhotoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        proPromotionPhoto.setDescriptions(UPDATED_DESCRIPTIONS);
        ProPromotionPhotoDTO proPromotionPhotoDTO = proPromotionPhotoMapper.proPromotionPhotoToProPromotionPhotoDTO(proPromotionPhoto);

        restProPromotionPhotoMockMvc.perform(put("/api/proPromotionPhotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proPromotionPhotoDTO)))
                .andExpect(status().isOk());

        // Validate the ProPromotionPhoto in the database
        List<ProPromotionPhoto> proPromotionPhotos = proPromotionPhotoRepository.findAll();
        assertThat(proPromotionPhotos).hasSize(databaseSizeBeforeUpdate);
        ProPromotionPhoto testProPromotionPhoto = proPromotionPhotos.get(proPromotionPhotos.size() - 1);
        assertThat(testProPromotionPhoto.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProPromotionPhoto.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testProPromotionPhoto.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testProPromotionPhoto.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void deleteProPromotionPhoto() throws Exception {
        // Initialize the database
        proPromotionPhotoRepository.saveAndFlush(proPromotionPhoto);

		int databaseSizeBeforeDelete = proPromotionPhotoRepository.findAll().size();

        // Get the proPromotionPhoto
        restProPromotionPhotoMockMvc.perform(delete("/api/proPromotionPhotos/{id}", proPromotionPhoto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProPromotionPhoto> proPromotionPhotos = proPromotionPhotoRepository.findAll();
        assertThat(proPromotionPhotos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
