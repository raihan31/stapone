package com.rocketscience.stapone.web.rest;

import com.rocketscience.stapone.Application;
import com.rocketscience.stapone.domain.DeptPromotionPhoto;
import com.rocketscience.stapone.repository.DeptPromotionPhotoRepository;
import com.rocketscience.stapone.repository.search.DeptPromotionPhotoSearchRepository;
import com.rocketscience.stapone.web.rest.dto.DeptPromotionPhotoDTO;
import com.rocketscience.stapone.web.rest.mapper.DeptPromotionPhotoMapper;

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
 * Test class for the DeptPromotionPhotoResource REST controller.
 *
 * @see DeptPromotionPhotoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DeptPromotionPhotoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_DESCRIPTIONS = "AAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBB";

    @Inject
    private DeptPromotionPhotoRepository deptPromotionPhotoRepository;

    @Inject
    private DeptPromotionPhotoMapper deptPromotionPhotoMapper;

    @Inject
    private DeptPromotionPhotoSearchRepository deptPromotionPhotoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDeptPromotionPhotoMockMvc;

    private DeptPromotionPhoto deptPromotionPhoto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DeptPromotionPhotoResource deptPromotionPhotoResource = new DeptPromotionPhotoResource();
        ReflectionTestUtils.setField(deptPromotionPhotoResource, "deptPromotionPhotoSearchRepository", deptPromotionPhotoSearchRepository);
        ReflectionTestUtils.setField(deptPromotionPhotoResource, "deptPromotionPhotoRepository", deptPromotionPhotoRepository);
        ReflectionTestUtils.setField(deptPromotionPhotoResource, "deptPromotionPhotoMapper", deptPromotionPhotoMapper);
        this.restDeptPromotionPhotoMockMvc = MockMvcBuilders.standaloneSetup(deptPromotionPhotoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        deptPromotionPhoto = new DeptPromotionPhoto();
        deptPromotionPhoto.setName(DEFAULT_NAME);
        deptPromotionPhoto.setPhoto(DEFAULT_PHOTO);
        deptPromotionPhoto.setPhotoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        deptPromotionPhoto.setDescriptions(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void createDeptPromotionPhoto() throws Exception {
        int databaseSizeBeforeCreate = deptPromotionPhotoRepository.findAll().size();

        // Create the DeptPromotionPhoto
        DeptPromotionPhotoDTO deptPromotionPhotoDTO = deptPromotionPhotoMapper.deptPromotionPhotoToDeptPromotionPhotoDTO(deptPromotionPhoto);

        restDeptPromotionPhotoMockMvc.perform(post("/api/deptPromotionPhotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deptPromotionPhotoDTO)))
                .andExpect(status().isCreated());

        // Validate the DeptPromotionPhoto in the database
        List<DeptPromotionPhoto> deptPromotionPhotos = deptPromotionPhotoRepository.findAll();
        assertThat(deptPromotionPhotos).hasSize(databaseSizeBeforeCreate + 1);
        DeptPromotionPhoto testDeptPromotionPhoto = deptPromotionPhotos.get(deptPromotionPhotos.size() - 1);
        assertThat(testDeptPromotionPhoto.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeptPromotionPhoto.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testDeptPromotionPhoto.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testDeptPromotionPhoto.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void getAllDeptPromotionPhotos() throws Exception {
        // Initialize the database
        deptPromotionPhotoRepository.saveAndFlush(deptPromotionPhoto);

        // Get all the deptPromotionPhotos
        restDeptPromotionPhotoMockMvc.perform(get("/api/deptPromotionPhotos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(deptPromotionPhoto.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
                .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())));
    }

    @Test
    @Transactional
    public void getDeptPromotionPhoto() throws Exception {
        // Initialize the database
        deptPromotionPhotoRepository.saveAndFlush(deptPromotionPhoto);

        // Get the deptPromotionPhoto
        restDeptPromotionPhotoMockMvc.perform(get("/api/deptPromotionPhotos/{id}", deptPromotionPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(deptPromotionPhoto.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeptPromotionPhoto() throws Exception {
        // Get the deptPromotionPhoto
        restDeptPromotionPhotoMockMvc.perform(get("/api/deptPromotionPhotos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeptPromotionPhoto() throws Exception {
        // Initialize the database
        deptPromotionPhotoRepository.saveAndFlush(deptPromotionPhoto);

		int databaseSizeBeforeUpdate = deptPromotionPhotoRepository.findAll().size();

        // Update the deptPromotionPhoto
        deptPromotionPhoto.setName(UPDATED_NAME);
        deptPromotionPhoto.setPhoto(UPDATED_PHOTO);
        deptPromotionPhoto.setPhotoContentType(UPDATED_PHOTO_CONTENT_TYPE);
        deptPromotionPhoto.setDescriptions(UPDATED_DESCRIPTIONS);
        DeptPromotionPhotoDTO deptPromotionPhotoDTO = deptPromotionPhotoMapper.deptPromotionPhotoToDeptPromotionPhotoDTO(deptPromotionPhoto);

        restDeptPromotionPhotoMockMvc.perform(put("/api/deptPromotionPhotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(deptPromotionPhotoDTO)))
                .andExpect(status().isOk());

        // Validate the DeptPromotionPhoto in the database
        List<DeptPromotionPhoto> deptPromotionPhotos = deptPromotionPhotoRepository.findAll();
        assertThat(deptPromotionPhotos).hasSize(databaseSizeBeforeUpdate);
        DeptPromotionPhoto testDeptPromotionPhoto = deptPromotionPhotos.get(deptPromotionPhotos.size() - 1);
        assertThat(testDeptPromotionPhoto.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeptPromotionPhoto.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDeptPromotionPhoto.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testDeptPromotionPhoto.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void deleteDeptPromotionPhoto() throws Exception {
        // Initialize the database
        deptPromotionPhotoRepository.saveAndFlush(deptPromotionPhoto);

		int databaseSizeBeforeDelete = deptPromotionPhotoRepository.findAll().size();

        // Get the deptPromotionPhoto
        restDeptPromotionPhotoMockMvc.perform(delete("/api/deptPromotionPhotos/{id}", deptPromotionPhoto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DeptPromotionPhoto> deptPromotionPhotos = deptPromotionPhotoRepository.findAll();
        assertThat(deptPromotionPhotos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
