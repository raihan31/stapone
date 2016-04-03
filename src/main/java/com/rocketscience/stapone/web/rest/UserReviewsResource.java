package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.UserReviews;
import com.rocketscience.stapone.repository.UserReviewsRepository;
import com.rocketscience.stapone.repository.search.UserReviewsSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.UserReviewsDTO;
import com.rocketscience.stapone.web.rest.mapper.UserReviewsMapper;
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
 * REST controller for managing UserReviews.
 */
@RestController
@RequestMapping("/api")
public class UserReviewsResource {

    private final Logger log = LoggerFactory.getLogger(UserReviewsResource.class);
        
    @Inject
    private UserReviewsRepository userReviewsRepository;
    
    @Inject
    private UserReviewsMapper userReviewsMapper;
    
    @Inject
    private UserReviewsSearchRepository userReviewsSearchRepository;
    
    /**
     * POST  /userReviewss -> Create a new userReviews.
     */
    @RequestMapping(value = "/userReviewss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserReviewsDTO> createUserReviews(@RequestBody UserReviewsDTO userReviewsDTO) throws URISyntaxException {
        log.debug("REST request to save UserReviews : {}", userReviewsDTO);
        if (userReviewsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userReviews", "idexists", "A new userReviews cannot already have an ID")).body(null);
        }
        UserReviews userReviews = userReviewsMapper.userReviewsDTOToUserReviews(userReviewsDTO);
        userReviews = userReviewsRepository.save(userReviews);
        UserReviewsDTO result = userReviewsMapper.userReviewsToUserReviewsDTO(userReviews);
        userReviewsSearchRepository.save(userReviews);
        return ResponseEntity.created(new URI("/api/userReviewss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userReviews", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userReviewss -> Updates an existing userReviews.
     */
    @RequestMapping(value = "/userReviewss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserReviewsDTO> updateUserReviews(@RequestBody UserReviewsDTO userReviewsDTO) throws URISyntaxException {
        log.debug("REST request to update UserReviews : {}", userReviewsDTO);
        if (userReviewsDTO.getId() == null) {
            return createUserReviews(userReviewsDTO);
        }
        UserReviews userReviews = userReviewsMapper.userReviewsDTOToUserReviews(userReviewsDTO);
        userReviews = userReviewsRepository.save(userReviews);
        UserReviewsDTO result = userReviewsMapper.userReviewsToUserReviewsDTO(userReviews);
        userReviewsSearchRepository.save(userReviews);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userReviews", userReviewsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userReviewss -> get all the userReviewss.
     */
    @RequestMapping(value = "/userReviewss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<UserReviewsDTO>> getAllUserReviewss(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of UserReviewss");
        Page<UserReviews> page = userReviewsRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userReviewss");
        return new ResponseEntity<>(page.getContent().stream()
            .map(userReviewsMapper::userReviewsToUserReviewsDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /userReviewss/:id -> get the "id" userReviews.
     */
    @RequestMapping(value = "/userReviewss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserReviewsDTO> getUserReviews(@PathVariable Long id) {
        log.debug("REST request to get UserReviews : {}", id);
        UserReviews userReviews = userReviewsRepository.findOne(id);
        UserReviewsDTO userReviewsDTO = userReviewsMapper.userReviewsToUserReviewsDTO(userReviews);
        return Optional.ofNullable(userReviewsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userReviewss/:id -> delete the "id" userReviews.
     */
    @RequestMapping(value = "/userReviewss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserReviews(@PathVariable Long id) {
        log.debug("REST request to delete UserReviews : {}", id);
        userReviewsRepository.delete(id);
        userReviewsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userReviews", id.toString())).build();
    }

    /**
     * SEARCH  /_search/userReviewss/:query -> search for the userReviews corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/userReviewss/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserReviewsDTO> searchUserReviewss(@PathVariable String query) {
        log.debug("REST request to search UserReviewss for query {}", query);
        return StreamSupport
            .stream(userReviewsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(userReviewsMapper::userReviewsToUserReviewsDTO)
            .collect(Collectors.toList());
    }
}
