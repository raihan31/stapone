package com.rocketscience.stapone.web.rest;

import com.rocketscience.stapone.Application;
import com.rocketscience.stapone.domain.Products;
import com.rocketscience.stapone.repository.ProductsRepository;
import com.rocketscience.stapone.repository.search.ProductsSearchRepository;
import com.rocketscience.stapone.web.rest.dto.ProductsDTO;
import com.rocketscience.stapone.web.rest.mapper.ProductsMapper;

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
 * Test class for the ProductsResource REST controller.
 *
 * @see ProductsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProductsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_DESCRIPTIONS = "AAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBB";
    private static final String DEFAULT_MEASURED_UNIT = "AAAAA";
    private static final String UPDATED_MEASURED_UNIT = "BBBBB";

    private static final Integer DEFAULT_UNIT_ITEM = 1;
    private static final Integer UPDATED_UNIT_ITEM = 2;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_AVAILABLE_ITEM = 1;
    private static final Integer UPDATED_AVAILABLE_ITEM = 2;

    private static final Integer DEFAULT_SOLD_ITEM = 1;
    private static final Integer UPDATED_SOLD_ITEM = 2;

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Integer DEFAULT_SHARED_NO = 1;
    private static final Integer UPDATED_SHARED_NO = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = dateTimeFormatter.format(DEFAULT_CREATED_AT);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPDATED_AT_STR = dateTimeFormatter.format(DEFAULT_UPDATED_AT);

    @Inject
    private ProductsRepository productsRepository;

    @Inject
    private ProductsMapper productsMapper;

    @Inject
    private ProductsSearchRepository productsSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProductsMockMvc;

    private Products products;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductsResource productsResource = new ProductsResource();
        ReflectionTestUtils.setField(productsResource, "productsSearchRepository", productsSearchRepository);
        ReflectionTestUtils.setField(productsResource, "productsRepository", productsRepository);
        ReflectionTestUtils.setField(productsResource, "productsMapper", productsMapper);
        this.restProductsMockMvc = MockMvcBuilders.standaloneSetup(productsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        products = new Products();
        products.setName(DEFAULT_NAME);
        products.setLogo(DEFAULT_LOGO);
        products.setLogoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        products.setDescriptions(DEFAULT_DESCRIPTIONS);
        products.setMeasuredUnit(DEFAULT_MEASURED_UNIT);
        products.setUnitItem(DEFAULT_UNIT_ITEM);
        products.setPrice(DEFAULT_PRICE);
        products.setAvailableItem(DEFAULT_AVAILABLE_ITEM);
        products.setSoldItem(DEFAULT_SOLD_ITEM);
        products.setStatus(DEFAULT_STATUS);
        products.setSharedNo(DEFAULT_SHARED_NO);
        products.setCreatedAt(DEFAULT_CREATED_AT);
        products.setUpdatedAt(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createProducts() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products
        ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

        restProductsMockMvc.perform(post("/api/productss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
                .andExpect(status().isCreated());

        // Validate the Products in the database
        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeCreate + 1);
        Products testProducts = productss.get(productss.size() - 1);
        assertThat(testProducts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducts.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testProducts.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testProducts.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
        assertThat(testProducts.getMeasuredUnit()).isEqualTo(DEFAULT_MEASURED_UNIT);
        assertThat(testProducts.getUnitItem()).isEqualTo(DEFAULT_UNIT_ITEM);
        assertThat(testProducts.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProducts.getAvailableItem()).isEqualTo(DEFAULT_AVAILABLE_ITEM);
        assertThat(testProducts.getSoldItem()).isEqualTo(DEFAULT_SOLD_ITEM);
        assertThat(testProducts.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProducts.getSharedNo()).isEqualTo(DEFAULT_SHARED_NO);
        assertThat(testProducts.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testProducts.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setName(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

        restProductsMockMvc.perform(post("/api/productss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
                .andExpect(status().isBadRequest());

        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeasuredUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setMeasuredUnit(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

        restProductsMockMvc.perform(post("/api/productss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
                .andExpect(status().isBadRequest());

        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitItemIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setUnitItem(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

        restProductsMockMvc.perform(post("/api/productss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
                .andExpect(status().isBadRequest());

        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setPrice(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

        restProductsMockMvc.perform(post("/api/productss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
                .andExpect(status().isBadRequest());

        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductss() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productss
        restProductsMockMvc.perform(get("/api/productss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
                .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())))
                .andExpect(jsonPath("$.[*].measuredUnit").value(hasItem(DEFAULT_MEASURED_UNIT.toString())))
                .andExpect(jsonPath("$.[*].unitItem").value(hasItem(DEFAULT_UNIT_ITEM)))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].availableItem").value(hasItem(DEFAULT_AVAILABLE_ITEM)))
                .andExpect(jsonPath("$.[*].soldItem").value(hasItem(DEFAULT_SOLD_ITEM)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
                .andExpect(jsonPath("$.[*].sharedNo").value(hasItem(DEFAULT_SHARED_NO)))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT_STR)));
    }

    @Test
    @Transactional
    public void getProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get the products
        restProductsMockMvc.perform(get("/api/productss/{id}", products.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(products.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()))
            .andExpect(jsonPath("$.measuredUnit").value(DEFAULT_MEASURED_UNIT.toString()))
            .andExpect(jsonPath("$.unitItem").value(DEFAULT_UNIT_ITEM))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.availableItem").value(DEFAULT_AVAILABLE_ITEM))
            .andExpect(jsonPath("$.soldItem").value(DEFAULT_SOLD_ITEM))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.sharedNo").value(DEFAULT_SHARED_NO))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT_STR));
    }

    @Test
    @Transactional
    public void getNonExistingProducts() throws Exception {
        // Get the products
        restProductsMockMvc.perform(get("/api/productss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

		int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products
        products.setName(UPDATED_NAME);
        products.setLogo(UPDATED_LOGO);
        products.setLogoContentType(UPDATED_LOGO_CONTENT_TYPE);
        products.setDescriptions(UPDATED_DESCRIPTIONS);
        products.setMeasuredUnit(UPDATED_MEASURED_UNIT);
        products.setUnitItem(UPDATED_UNIT_ITEM);
        products.setPrice(UPDATED_PRICE);
        products.setAvailableItem(UPDATED_AVAILABLE_ITEM);
        products.setSoldItem(UPDATED_SOLD_ITEM);
        products.setStatus(UPDATED_STATUS);
        products.setSharedNo(UPDATED_SHARED_NO);
        products.setCreatedAt(UPDATED_CREATED_AT);
        products.setUpdatedAt(UPDATED_UPDATED_AT);
        ProductsDTO productsDTO = productsMapper.productsToProductsDTO(products);

        restProductsMockMvc.perform(put("/api/productss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
                .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productss.get(productss.size() - 1);
        assertThat(testProducts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducts.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testProducts.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testProducts.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
        assertThat(testProducts.getMeasuredUnit()).isEqualTo(UPDATED_MEASURED_UNIT);
        assertThat(testProducts.getUnitItem()).isEqualTo(UPDATED_UNIT_ITEM);
        assertThat(testProducts.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProducts.getAvailableItem()).isEqualTo(UPDATED_AVAILABLE_ITEM);
        assertThat(testProducts.getSoldItem()).isEqualTo(UPDATED_SOLD_ITEM);
        assertThat(testProducts.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProducts.getSharedNo()).isEqualTo(UPDATED_SHARED_NO);
        assertThat(testProducts.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testProducts.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void deleteProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

		int databaseSizeBeforeDelete = productsRepository.findAll().size();

        // Get the products
        restProductsMockMvc.perform(delete("/api/productss/{id}", products.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Products> productss = productsRepository.findAll();
        assertThat(productss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
