package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.Orders;
import com.rocketscience.stapone.repository.OrdersRepository;
import com.rocketscience.stapone.repository.search.OrdersSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.OrdersDTO;
import com.rocketscience.stapone.web.rest.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Orders.
 */
@RestController
@RequestMapping("/api")
public class OrdersResource {

    private final Logger log = LoggerFactory.getLogger(OrdersResource.class);
        
    @Inject
    private OrdersRepository ordersRepository;
    
    @Inject
    private OrdersMapper ordersMapper;
    
    @Inject
    private OrdersSearchRepository ordersSearchRepository;
    
    /**
     * POST  /orderss -> Create a new orders.
     */
    @RequestMapping(value = "/orderss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrdersDTO> createOrders(@RequestBody OrdersDTO ordersDTO) throws URISyntaxException {
        log.debug("REST request to save Orders : {}", ordersDTO);
        if (ordersDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orders", "idexists", "A new orders cannot already have an ID")).body(null);
        }
        Orders orders = ordersMapper.ordersDTOToOrders(ordersDTO);
        orders = ordersRepository.save(orders);
        OrdersDTO result = ordersMapper.ordersToOrdersDTO(orders);
        ordersSearchRepository.save(orders);
        return ResponseEntity.created(new URI("/api/orderss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orders", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orderss -> Updates an existing orders.
     */
    @RequestMapping(value = "/orderss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrdersDTO> updateOrders(@RequestBody OrdersDTO ordersDTO) throws URISyntaxException {
        log.debug("REST request to update Orders : {}", ordersDTO);
        if (ordersDTO.getId() == null) {
            return createOrders(ordersDTO);
        }
        Orders orders = ordersMapper.ordersDTOToOrders(ordersDTO);
        orders = ordersRepository.save(orders);
        OrdersDTO result = ordersMapper.ordersToOrdersDTO(orders);
        ordersSearchRepository.save(orders);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orders", ordersDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orderss -> get all the orderss.
     */
    @RequestMapping(value = "/orderss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrdersDTO>> getAllOrderss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Orderss");
        Page<Orders> page = ordersRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderss");
        return new ResponseEntity<>(page.getContent().stream()
            .map(ordersMapper::ordersToOrdersDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderss/:id -> get the "id" orders.
     */
    @RequestMapping(value = "/orderss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrdersDTO> getOrders(@PathVariable Long id) {
        log.debug("REST request to get Orders : {}", id);
        Orders orders = ordersRepository.findOne(id);
        OrdersDTO ordersDTO = ordersMapper.ordersToOrdersDTO(orders);
        return Optional.ofNullable(ordersDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderss/:id -> delete the "id" orders.
     */
    @RequestMapping(value = "/orderss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
        log.debug("REST request to delete Orders : {}", id);
        ordersRepository.delete(id);
        ordersSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orders", id.toString())).build();
    }

    /**
     * SEARCH  /_search/orderss/:query -> search for the orders corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/orderss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OrdersDTO> searchOrderss(@PathVariable String query) {
        log.debug("REST request to search Orderss for query {}", query);
        return StreamSupport
            .stream(ordersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(ordersMapper::ordersToOrdersDTO)
            .collect(Collectors.toList());
    }
}
