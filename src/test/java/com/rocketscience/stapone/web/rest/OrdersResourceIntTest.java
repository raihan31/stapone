package com.rocketscience.stapone.web.rest;

import com.rocketscience.stapone.Application;
import com.rocketscience.stapone.domain.Orders;
import com.rocketscience.stapone.repository.OrdersRepository;
import com.rocketscience.stapone.repository.search.OrdersSearchRepository;
import com.rocketscience.stapone.web.rest.dto.OrdersDTO;
import com.rocketscience.stapone.web.rest.mapper.OrdersMapper;

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
 * Test class for the OrdersResource REST controller.
 *
 * @see OrdersResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class OrdersResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_DESCRIPTIONS = "AAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_TOTAL_PRODUCTS = 1;
    private static final Integer UPDATED_TOTAL_PRODUCTS = 2;

    private static final Boolean DEFAULT_IS_VIEWED = false;
    private static final Boolean UPDATED_IS_VIEWED = true;

    private static final Boolean DEFAULT_IS_DELIVERED = false;
    private static final Boolean UPDATED_IS_DELIVERED = true;

    private static final Boolean DEFAULT_IS_RECEIVED = false;
    private static final Boolean UPDATED_IS_RECEIVED = true;

    private static final BigDecimal DEFAULT_TOTAL_MONEY_RECEIVED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_MONEY_RECEIVED = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_PAID = false;
    private static final Boolean UPDATED_IS_PAID = true;
    private static final String DEFAULT_RECEIVED_DOCUMENT = "AAAAA";
    private static final String UPDATED_RECEIVED_DOCUMENT = "BBBBB";

    private static final Boolean DEFAULT_IS_CANCELED = false;
    private static final Boolean UPDATED_IS_CANCELED = true;

    private static final Boolean DEFAULT_IS_TAKEN = false;
    private static final Boolean UPDATED_IS_TAKEN = true;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = dateTimeFormatter.format(DEFAULT_CREATED_AT);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPDATED_AT_STR = dateTimeFormatter.format(DEFAULT_UPDATED_AT);

    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrdersMapper ordersMapper;

    @Inject
    private OrdersSearchRepository ordersSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restOrdersMockMvc;

    private Orders orders;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdersResource ordersResource = new OrdersResource();
        ReflectionTestUtils.setField(ordersResource, "ordersSearchRepository", ordersSearchRepository);
        ReflectionTestUtils.setField(ordersResource, "ordersRepository", ordersRepository);
        ReflectionTestUtils.setField(ordersResource, "ordersMapper", ordersMapper);
        this.restOrdersMockMvc = MockMvcBuilders.standaloneSetup(ordersResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        orders = new Orders();
        orders.setDescriptions(DEFAULT_DESCRIPTIONS);
        orders.setTotalPrice(DEFAULT_TOTAL_PRICE);
        orders.setTotalProducts(DEFAULT_TOTAL_PRODUCTS);
        orders.setIsViewed(DEFAULT_IS_VIEWED);
        orders.setIsDelivered(DEFAULT_IS_DELIVERED);
        orders.setIsReceived(DEFAULT_IS_RECEIVED);
        orders.setTotalMoneyReceived(DEFAULT_TOTAL_MONEY_RECEIVED);
        orders.setIsPaid(DEFAULT_IS_PAID);
        orders.setReceivedDocument(DEFAULT_RECEIVED_DOCUMENT);
        orders.setIsCanceled(DEFAULT_IS_CANCELED);
        orders.setIsTaken(DEFAULT_IS_TAKEN);
        orders.setCreatedAt(DEFAULT_CREATED_AT);
        orders.setUpdatedAt(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.ordersToOrdersDTO(orders);

        restOrdersMockMvc.perform(post("/api/orderss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
                .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = orderss.get(orderss.size() - 1);
        assertThat(testOrders.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
        assertThat(testOrders.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testOrders.getTotalProducts()).isEqualTo(DEFAULT_TOTAL_PRODUCTS);
        assertThat(testOrders.getIsViewed()).isEqualTo(DEFAULT_IS_VIEWED);
        assertThat(testOrders.getIsDelivered()).isEqualTo(DEFAULT_IS_DELIVERED);
        assertThat(testOrders.getIsReceived()).isEqualTo(DEFAULT_IS_RECEIVED);
        assertThat(testOrders.getTotalMoneyReceived()).isEqualTo(DEFAULT_TOTAL_MONEY_RECEIVED);
        assertThat(testOrders.getIsPaid()).isEqualTo(DEFAULT_IS_PAID);
        assertThat(testOrders.getReceivedDocument()).isEqualTo(DEFAULT_RECEIVED_DOCUMENT);
        assertThat(testOrders.getIsCanceled()).isEqualTo(DEFAULT_IS_CANCELED);
        assertThat(testOrders.getIsTaken()).isEqualTo(DEFAULT_IS_TAKEN);
        assertThat(testOrders.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrders.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrderss() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the orderss
        restOrdersMockMvc.perform(get("/api/orderss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
                .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())))
                .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].totalProducts").value(hasItem(DEFAULT_TOTAL_PRODUCTS)))
                .andExpect(jsonPath("$.[*].isViewed").value(hasItem(DEFAULT_IS_VIEWED.booleanValue())))
                .andExpect(jsonPath("$.[*].isDelivered").value(hasItem(DEFAULT_IS_DELIVERED.booleanValue())))
                .andExpect(jsonPath("$.[*].isReceived").value(hasItem(DEFAULT_IS_RECEIVED.booleanValue())))
                .andExpect(jsonPath("$.[*].totalMoneyReceived").value(hasItem(DEFAULT_TOTAL_MONEY_RECEIVED.intValue())))
                .andExpect(jsonPath("$.[*].isPaid").value(hasItem(DEFAULT_IS_PAID.booleanValue())))
                .andExpect(jsonPath("$.[*].receivedDocument").value(hasItem(DEFAULT_RECEIVED_DOCUMENT.toString())))
                .andExpect(jsonPath("$.[*].isCanceled").value(hasItem(DEFAULT_IS_CANCELED.booleanValue())))
                .andExpect(jsonPath("$.[*].isTaken").value(hasItem(DEFAULT_IS_TAKEN.booleanValue())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT_STR)));
    }

    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orderss/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.totalProducts").value(DEFAULT_TOTAL_PRODUCTS))
            .andExpect(jsonPath("$.isViewed").value(DEFAULT_IS_VIEWED.booleanValue()))
            .andExpect(jsonPath("$.isDelivered").value(DEFAULT_IS_DELIVERED.booleanValue()))
            .andExpect(jsonPath("$.isReceived").value(DEFAULT_IS_RECEIVED.booleanValue()))
            .andExpect(jsonPath("$.totalMoneyReceived").value(DEFAULT_TOTAL_MONEY_RECEIVED.intValue()))
            .andExpect(jsonPath("$.isPaid").value(DEFAULT_IS_PAID.booleanValue()))
            .andExpect(jsonPath("$.receivedDocument").value(DEFAULT_RECEIVED_DOCUMENT.toString()))
            .andExpect(jsonPath("$.isCanceled").value(DEFAULT_IS_CANCELED.booleanValue()))
            .andExpect(jsonPath("$.isTaken").value(DEFAULT_IS_TAKEN.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT_STR));
    }

    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orderss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

		int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        orders.setDescriptions(UPDATED_DESCRIPTIONS);
        orders.setTotalPrice(UPDATED_TOTAL_PRICE);
        orders.setTotalProducts(UPDATED_TOTAL_PRODUCTS);
        orders.setIsViewed(UPDATED_IS_VIEWED);
        orders.setIsDelivered(UPDATED_IS_DELIVERED);
        orders.setIsReceived(UPDATED_IS_RECEIVED);
        orders.setTotalMoneyReceived(UPDATED_TOTAL_MONEY_RECEIVED);
        orders.setIsPaid(UPDATED_IS_PAID);
        orders.setReceivedDocument(UPDATED_RECEIVED_DOCUMENT);
        orders.setIsCanceled(UPDATED_IS_CANCELED);
        orders.setIsTaken(UPDATED_IS_TAKEN);
        orders.setCreatedAt(UPDATED_CREATED_AT);
        orders.setUpdatedAt(UPDATED_UPDATED_AT);
        OrdersDTO ordersDTO = ordersMapper.ordersToOrdersDTO(orders);

        restOrdersMockMvc.perform(put("/api/orderss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
                .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = orderss.get(orderss.size() - 1);
        assertThat(testOrders.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
        assertThat(testOrders.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrders.getTotalProducts()).isEqualTo(UPDATED_TOTAL_PRODUCTS);
        assertThat(testOrders.getIsViewed()).isEqualTo(UPDATED_IS_VIEWED);
        assertThat(testOrders.getIsDelivered()).isEqualTo(UPDATED_IS_DELIVERED);
        assertThat(testOrders.getIsReceived()).isEqualTo(UPDATED_IS_RECEIVED);
        assertThat(testOrders.getTotalMoneyReceived()).isEqualTo(UPDATED_TOTAL_MONEY_RECEIVED);
        assertThat(testOrders.getIsPaid()).isEqualTo(UPDATED_IS_PAID);
        assertThat(testOrders.getReceivedDocument()).isEqualTo(UPDATED_RECEIVED_DOCUMENT);
        assertThat(testOrders.getIsCanceled()).isEqualTo(UPDATED_IS_CANCELED);
        assertThat(testOrders.getIsTaken()).isEqualTo(UPDATED_IS_TAKEN);
        assertThat(testOrders.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrders.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

		int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Get the orders
        restOrdersMockMvc.perform(delete("/api/orderss/{id}", orders.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Orders> orderss = ordersRepository.findAll();
        assertThat(orderss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
