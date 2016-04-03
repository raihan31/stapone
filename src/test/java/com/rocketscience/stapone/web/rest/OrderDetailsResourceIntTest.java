package com.rocketscience.stapone.web.rest;

import com.rocketscience.stapone.Application;
import com.rocketscience.stapone.domain.OrderDetails;
import com.rocketscience.stapone.repository.OrderDetailsRepository;
import com.rocketscience.stapone.repository.search.OrderDetailsSearchRepository;
import com.rocketscience.stapone.web.rest.dto.OrderDetailsDTO;
import com.rocketscience.stapone.web.rest.mapper.OrderDetailsMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the OrderDetailsResource REST controller.
 *
 * @see OrderDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrderDetailsResourceIntTest {


    private static final Integer DEFAULT_NO_OF_PRODUCTS = 1;
    private static final Integer UPDATED_NO_OF_PRODUCTS = 2;

    @Inject
    private OrderDetailsRepository orderDetailsRepository;

    @Inject
    private OrderDetailsMapper orderDetailsMapper;

    @Inject
    private OrderDetailsSearchRepository orderDetailsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrderDetailsMockMvc;

    private OrderDetails orderDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrderDetailsResource orderDetailsResource = new OrderDetailsResource();
        ReflectionTestUtils.setField(orderDetailsResource, "orderDetailsSearchRepository", orderDetailsSearchRepository);
        ReflectionTestUtils.setField(orderDetailsResource, "orderDetailsRepository", orderDetailsRepository);
        ReflectionTestUtils.setField(orderDetailsResource, "orderDetailsMapper", orderDetailsMapper);
        this.restOrderDetailsMockMvc = MockMvcBuilders.standaloneSetup(orderDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        orderDetails = new OrderDetails();
        orderDetails.setNoOfProducts(DEFAULT_NO_OF_PRODUCTS);
    }

    @Test
    @Transactional
    public void createOrderDetails() throws Exception {
        int databaseSizeBeforeCreate = orderDetailsRepository.findAll().size();

        // Create the OrderDetails
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);

        restOrderDetailsMockMvc.perform(post("/api/orderDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO)))
                .andExpect(status().isCreated());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailss = orderDetailsRepository.findAll();
        assertThat(orderDetailss).hasSize(databaseSizeBeforeCreate + 1);
        OrderDetails testOrderDetails = orderDetailss.get(orderDetailss.size() - 1);
        assertThat(testOrderDetails.getNoOfProducts()).isEqualTo(DEFAULT_NO_OF_PRODUCTS);
    }

    @Test
    @Transactional
    public void getAllOrderDetailss() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get all the orderDetailss
        restOrderDetailsMockMvc.perform(get("/api/orderDetailss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].noOfProducts").value(hasItem(DEFAULT_NO_OF_PRODUCTS)));
    }

    @Test
    @Transactional
    public void getOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/orderDetailss/{id}", orderDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orderDetails.getId().intValue()))
            .andExpect(jsonPath("$.noOfProducts").value(DEFAULT_NO_OF_PRODUCTS));
    }

    @Test
    @Transactional
    public void getNonExistingOrderDetails() throws Exception {
        // Get the orderDetails
        restOrderDetailsMockMvc.perform(get("/api/orderDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

		int databaseSizeBeforeUpdate = orderDetailsRepository.findAll().size();

        // Update the orderDetails
        orderDetails.setNoOfProducts(UPDATED_NO_OF_PRODUCTS);
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);

        restOrderDetailsMockMvc.perform(put("/api/orderDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(orderDetailsDTO)))
                .andExpect(status().isOk());

        // Validate the OrderDetails in the database
        List<OrderDetails> orderDetailss = orderDetailsRepository.findAll();
        assertThat(orderDetailss).hasSize(databaseSizeBeforeUpdate);
        OrderDetails testOrderDetails = orderDetailss.get(orderDetailss.size() - 1);
        assertThat(testOrderDetails.getNoOfProducts()).isEqualTo(UPDATED_NO_OF_PRODUCTS);
    }

    @Test
    @Transactional
    public void deleteOrderDetails() throws Exception {
        // Initialize the database
        orderDetailsRepository.saveAndFlush(orderDetails);

		int databaseSizeBeforeDelete = orderDetailsRepository.findAll().size();

        // Get the orderDetails
        restOrderDetailsMockMvc.perform(delete("/api/orderDetailss/{id}", orderDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderDetails> orderDetailss = orderDetailsRepository.findAll();
        assertThat(orderDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
