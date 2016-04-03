package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.CatPromotionPhoto;
import com.rocketscience.stapone.repository.CatPromotionPhotoRepository;
import com.rocketscience.stapone.repository.search.CatPromotionPhotoSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.CatPromotionPhotoDTO;
import com.rocketscience.stapone.web.rest.mapper.CatPromotionPhotoMapper;
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
 * REST controller for managing CatPromotionPhoto.
 */
@RestController
@RequestMapping("/api")
public class CatPromotionPhotoResource {

    private final Logger log = LoggerFactory.getLogger(CatPromotionPhotoResource.class);
        
    @Inject
    private CatPromotionPhotoRepository catPromotionPhotoRepository;
    
    @Inject
    private CatPromotionPhotoMapper catPromotionPhotoMapper;
    
    @Inject
    private CatPromotionPhotoSearchRepository catPromotionPhotoSearchRepository;
    
    /**
     * POST  /catPromotionPhotos -> Create a new catPromotionPhoto.
     */
    @RequestMapping(value = "/catPromotionPhotos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatPromotionPhotoDTO> createCatPromotionPhoto(@RequestBody CatPromotionPhotoDTO catPromotionPhotoDTO) throws URISyntaxException {
        log.debug("REST request to save CatPromotionPhoto : {}", catPromotionPhotoDTO);
        if (catPromotionPhotoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("catPromotionPhoto", "idexists", "A new catPromotionPhoto cannot already have an ID")).body(null);
        }
        CatPromotionPhoto catPromotionPhoto = catPromotionPhotoMapper.catPromotionPhotoDTOToCatPromotionPhoto(catPromotionPhotoDTO);
        catPromotionPhoto = catPromotionPhotoRepository.save(catPromotionPhoto);
        CatPromotionPhotoDTO result = catPromotionPhotoMapper.catPromotionPhotoToCatPromotionPhotoDTO(catPromotionPhoto);
        catPromotionPhotoSearchRepository.save(catPromotionPhoto);
        return ResponseEntity.created(new URI("/api/catPromotionPhotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("catPromotionPhoto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /catPromotionPhotos -> Updates an existing catPromotionPhoto.
     */
    @RequestMapping(value = "/catPromotionPhotos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatPromotionPhotoDTO> updateCatPromotionPhoto(@RequestBody CatPromotionPhotoDTO catPromotionPhotoDTO) throws URISyntaxException {
        log.debug("REST request to update CatPromotionPhoto : {}", catPromotionPhotoDTO);
        if (catPromotionPhotoDTO.getId() == null) {
            return createCatPromotionPhoto(catPromotionPhotoDTO);
        }
        CatPromotionPhoto catPromotionPhoto = catPromotionPhotoMapper.catPromotionPhotoDTOToCatPromotionPhoto(catPromotionPhotoDTO);
        catPromotionPhoto = catPromotionPhotoRepository.save(catPromotionPhoto);
        CatPromotionPhotoDTO result = catPromotionPhotoMapper.catPromotionPhotoToCatPromotionPhotoDTO(catPromotionPhoto);
        catPromotionPhotoSearchRepository.save(catPromotionPhoto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("catPromotionPhoto", catPromotionPhotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /catPromotionPhotos -> get all the catPromotionPhotos.
     */
    @RequestMapping(value = "/catPromotionPhotos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<CatPromotionPhotoDTO>> getAllCatPromotionPhotos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CatPromotionPhotos");
        Page<CatPromotionPhoto> page = catPromotionPhotoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/catPromotionPhotos");
        return new ResponseEntity<>(page.getContent().stream()
            .map(catPromotionPhotoMapper::catPromotionPhotoToCatPromotionPhotoDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /catPromotionPhotos/:id -> get the "id" catPromotionPhoto.
     */
    @RequestMapping(value = "/catPromotionPhotos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CatPromotionPhotoDTO> getCatPromotionPhoto(@PathVariable Long id) {
        log.debug("REST request to get CatPromotionPhoto : {}", id);
        CatPromotionPhoto catPromotionPhoto = catPromotionPhotoRepository.findOne(id);
        CatPromotionPhotoDTO catPromotionPhotoDTO = catPromotionPhotoMapper.catPromotionPhotoToCatPromotionPhotoDTO(catPromotionPhoto);
        return Optional.ofNullable(catPromotionPhotoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /catPromotionPhotos/:id -> delete the "id" catPromotionPhoto.
     */
    @RequestMapping(value = "/catPromotionPhotos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCatPromotionPhoto(@PathVariable Long id) {
        log.debug("REST request to delete CatPromotionPhoto : {}", id);
        catPromotionPhotoRepository.delete(id);
        catPromotionPhotoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("catPromotionPhoto", id.toString())).build();
    }

    /**
     * SEARCH  /_search/catPromotionPhotos/:query -> search for the catPromotionPhoto corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/catPromotionPhotos/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CatPromotionPhotoDTO> searchCatPromotionPhotos(@PathVariable String query) {
        log.debug("REST request to search CatPromotionPhotos for query {}", query);
        return StreamSupport
            .stream(catPromotionPhotoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(catPromotionPhotoMapper::catPromotionPhotoToCatPromotionPhotoDTO)
            .collect(Collectors.toList());
    }
}
