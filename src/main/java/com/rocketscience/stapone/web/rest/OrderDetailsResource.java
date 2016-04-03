package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.OrderDetails;
import com.rocketscience.stapone.repository.OrderDetailsRepository;
import com.rocketscience.stapone.repository.search.OrderDetailsSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.OrderDetailsDTO;
import com.rocketscience.stapone.web.rest.mapper.OrderDetailsMapper;
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
 * REST controller for managing OrderDetails.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsResource.class);
        
    @Inject
    private OrderDetailsRepository orderDetailsRepository;
    
    @Inject
    private OrderDetailsMapper orderDetailsMapper;
    
    @Inject
    private OrderDetailsSearchRepository orderDetailsSearchRepository;
    
    /**
     * POST  /orderDetailss -> Create a new orderDetails.
     */
    @RequestMapping(value = "/orderDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetails : {}", orderDetailsDTO);
        if (orderDetailsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("orderDetails", "idexists", "A new orderDetails cannot already have an ID")).body(null);
        }
        OrderDetails orderDetails = orderDetailsMapper.orderDetailsDTOToOrderDetails(orderDetailsDTO);
        orderDetails = orderDetailsRepository.save(orderDetails);
        OrderDetailsDTO result = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);
        orderDetailsSearchRepository.save(orderDetails);
        return ResponseEntity.created(new URI("/api/orderDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("orderDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /orderDetailss -> Updates an existing orderDetails.
     */
    @RequestMapping(value = "/orderDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update OrderDetails : {}", orderDetailsDTO);
        if (orderDetailsDTO.getId() == null) {
            return createOrderDetails(orderDetailsDTO);
        }
        OrderDetails orderDetails = orderDetailsMapper.orderDetailsDTOToOrderDetails(orderDetailsDTO);
        orderDetails = orderDetailsRepository.save(orderDetails);
        OrderDetailsDTO result = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);
        orderDetailsSearchRepository.save(orderDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("orderDetails", orderDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orderDetailss -> get all the orderDetailss.
     */
    @RequestMapping(value = "/orderDetailss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderDetailsDTO>> getAllOrderDetailss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of OrderDetailss");
        Page<OrderDetails> page = orderDetailsRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orderDetailss");
        return new ResponseEntity<>(page.getContent().stream()
            .map(orderDetailsMapper::orderDetailsToOrderDetailsDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /orderDetailss/:id -> get the "id" orderDetails.
     */
    @RequestMapping(value = "/orderDetailss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderDetails : {}", id);
        OrderDetails orderDetails = orderDetailsRepository.findOne(id);
        OrderDetailsDTO orderDetailsDTO = orderDetailsMapper.orderDetailsToOrderDetailsDTO(orderDetails);
        return Optional.ofNullable(orderDetailsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /orderDetailss/:id -> delete the "id" orderDetails.
     */
    @RequestMapping(value = "/orderDetailss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetails : {}", id);
        orderDetailsRepository.delete(id);
        orderDetailsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("orderDetails", id.toString())).build();
    }

    /**
     * SEARCH  /_search/orderDetailss/:query -> search for the orderDetails corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/orderDetailss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<OrderDetailsDTO> searchOrderDetailss(@PathVariable String query) {
        log.debug("REST request to search OrderDetailss for query {}", query);
        return StreamSupport
            .stream(orderDetailsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(orderDetailsMapper::orderDetailsToOrderDetailsDTO)
            .collect(Collectors.toList());
    }
}
