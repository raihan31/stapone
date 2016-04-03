package com.rocketscience.stapone.web.rest;

import com.rocketscience.stapone.Application;
import com.rocketscience.stapone.domain.UserReviews;
import com.rocketscience.stapone.repository.UserReviewsRepository;
import com.rocketscience.stapone.repository.search.UserReviewsSearchRepository;
import com.rocketscience.stapone.web.rest.dto.UserReviewsDTO;
import com.rocketscience.stapone.web.rest.mapper.UserReviewsMapper;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the UserReviewsResource REST controller.
 *
 * @see UserReviewsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserReviewsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final BigDecimal DEFAULT_RATING = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATING = new BigDecimal(2);
    private static final String DEFAULT_COMMENTS = "AAAAA";
    private static final String UPDATED_COMMENTS = "BBBBB";

    private static final Boolean DEFAULT_IS_FAVOURITE = false;
    private static final Boolean UPDATED_IS_FAVOURITE = true;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = dateTimeFormatter.format(DEFAULT_CREATED_AT);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPDATED_AT_STR = dateTimeFormatter.format(DEFAULT_UPDATED_AT);

    @Inject
    private UserReviewsRepository userReviewsRepository;

    @Inject
    private UserReviewsMapper userReviewsMapper;

    @Inject
    private UserReviewsSearchRepository userReviewsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserReviewsMockMvc;

    private UserReviews userReviews;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserReviewsResource userReviewsResource = new UserReviewsResource();
        ReflectionTestUtils.setField(userReviewsResource, "userReviewsSearchRepository", userReviewsSearchRepository);
        ReflectionTestUtils.setField(userReviewsResource, "userReviewsRepository", userReviewsRepository);
        ReflectionTestUtils.setField(userReviewsResource, "userReviewsMapper", userReviewsMapper);
        this.restUserReviewsMockMvc = MockMvcBuilders.standaloneSetup(userReviewsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userReviews = new UserReviews();
        userReviews.setRating(DEFAULT_RATING);
        userReviews.setComments(DEFAULT_COMMENTS);
        userReviews.setIsFavourite(DEFAULT_IS_FAVOURITE);
        userReviews.setCreatedAt(DEFAULT_CREATED_AT);
        userReviews.setUpdatedAt(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createUserReviews() throws Exception {
        int databaseSizeBeforeCreate = userReviewsRepository.findAll().size();

        // Create the UserReviews
        UserReviewsDTO userReviewsDTO = userReviewsMapper.userReviewsToUserReviewsDTO(userReviews);

        restUserReviewsMockMvc.perform(post("/api/userReviewss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userReviewsDTO)))
                .andExpect(status().isCreated());

        // Validate the UserReviews in the database
        List<UserReviews> userReviewss = userReviewsRepository.findAll();
        assertThat(userReviewss).hasSize(databaseSizeBeforeCreate + 1);
        UserReviews testUserReviews = userReviewss.get(userReviewss.size() - 1);
        assertThat(testUserReviews.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testUserReviews.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testUserReviews.getIsFavourite()).isEqualTo(DEFAULT_IS_FAVOURITE);
        assertThat(testUserReviews.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserReviews.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserReviewss() throws Exception {
        // Initialize the database
        userReviewsRepository.saveAndFlush(userReviews);

        // Get all the userReviewss
        restUserReviewsMockMvc.perform(get("/api/userReviewss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userReviews.getId().intValue())))
                .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.intValue())))
                .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
                .andExpect(jsonPath("$.[*].isFavourite").value(hasItem(DEFAULT_IS_FAVOURITE.booleanValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT_STR)));
    }

    @Test
    @Transactional
    public void getUserReviews() throws Exception {
        // Initialize the database
        userReviewsRepository.saveAndFlush(userReviews);

        // Get the userReviews
        restUserReviewsMockMvc.perform(get("/api/userReviewss/{id}", userReviews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userReviews.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.isFavourite").value(DEFAULT_IS_FAVOURITE.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT_STR));
    }

    @Test
    @Transactional
    public void getNonExistingUserReviews() throws Exception {
        // Get the userReviews
        restUserReviewsMockMvc.perform(get("/api/userReviewss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserReviews() throws Exception {
        // Initialize the database
        userReviewsRepository.saveAndFlush(userReviews);

		int databaseSizeBeforeUpdate = userReviewsRepository.findAll().size();

        // Update the userReviews
        userReviews.setRating(UPDATED_RATING);
        userReviews.setComments(UPDATED_COMMENTS);
        userReviews.setIsFavourite(UPDATED_IS_FAVOURITE);
        userReviews.setCreatedAt(UPDATED_CREATED_AT);
        userReviews.setUpdatedAt(UPDATED_UPDATED_AT);
        UserReviewsDTO userReviewsDTO = userReviewsMapper.userReviewsToUserReviewsDTO(userReviews);

        restUserReviewsMockMvc.perform(put("/api/userReviewss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userReviewsDTO)))
                .andExpect(status().isOk());

        // Validate the UserReviews in the database
        List<UserReviews> userReviewss = userReviewsRepository.findAll();
        assertThat(userReviewss).hasSize(databaseSizeBeforeUpdate);
        UserReviews testUserReviews = userReviewss.get(userReviewss.size() - 1);
        assertThat(testUserReviews.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testUserReviews.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testUserReviews.getIsFavourite()).isEqualTo(UPDATED_IS_FAVOURITE);
        assertThat(testUserReviews.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserReviews.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void deleteUserReviews() throws Exception {
        // Initialize the database
        userReviewsRepository.saveAndFlush(userReviews);

		int databaseSizeBeforeDelete = userReviewsRepository.findAll().size();

        // Get the userReviews
        restUserReviewsMockMvc.perform(delete("/api/userReviewss/{id}", userReviews.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserReviews> userReviewss = userReviewsRepository.findAll();
        assertThat(userReviewss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
