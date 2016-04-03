package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.ProPromotionPhoto;
import com.rocketscience.stapone.repository.ProPromotionPhotoRepository;
import com.rocketscience.stapone.repository.search.ProPromotionPhotoSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.ProPromotionPhotoDTO;
import com.rocketscience.stapone.web.rest.mapper.ProPromotionPhotoMapper;
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
 * REST controller for managing ProPromotionPhoto.
 */
@RestController
@RequestMapping("/api")
public class ProPromotionPhotoResource {

    private final Logger log = LoggerFactory.getLogger(ProPromotionPhotoResource.class);
        
    @Inject
    private ProPromotionPhotoRepository proPromotionPhotoRepository;
    
    @Inject
    private ProPromotionPhotoMapper proPromotionPhotoMapper;
    
    @Inject
    private ProPromotionPhotoSearchRepository proPromotionPhotoSearchRepository;
    
    /**
     * POST  /proPromotionPhotos -> Create a new proPromotionPhoto.
     */
    @RequestMapping(value = "/proPromotionPhotos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProPromotionPhotoDTO> createProPromotionPhoto(@RequestBody ProPromotionPhotoDTO proPromotionPhotoDTO) throws URISyntaxException {
        log.debug("REST request to save ProPromotionPhoto : {}", proPromotionPhotoDTO);
        if (proPromotionPhotoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proPromotionPhoto", "idexists", "A new proPromotionPhoto cannot already have an ID")).body(null);
        }
        ProPromotionPhoto proPromotionPhoto = proPromotionPhotoMapper.proPromotionPhotoDTOToProPromotionPhoto(proPromotionPhotoDTO);
        proPromotionPhoto = proPromotionPhotoRepository.save(proPromotionPhoto);
        ProPromotionPhotoDTO result = proPromotionPhotoMapper.proPromotionPhotoToProPromotionPhotoDTO(proPromotionPhoto);
        proPromotionPhotoSearchRepository.save(proPromotionPhoto);
        return ResponseEntity.created(new URI("/api/proPromotionPhotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proPromotionPhoto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proPromotionPhotos -> Updates an existing proPromotionPhoto.
     */
    @RequestMapping(value = "/proPromotionPhotos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProPromotionPhotoDTO> updateProPromotionPhoto(@RequestBody ProPromotionPhotoDTO proPromotionPhotoDTO) throws URISyntaxException {
        log.debug("REST request to update ProPromotionPhoto : {}", proPromotionPhotoDTO);
        if (proPromotionPhotoDTO.getId() == null) {
            return createProPromotionPhoto(proPromotionPhotoDTO);
        }
        ProPromotionPhoto proPromotionPhoto = proPromotionPhotoMapper.proPromotionPhotoDTOToProPromotionPhoto(proPromotionPhotoDTO);
        proPromotionPhoto = proPromotionPhotoRepository.save(proPromotionPhoto);
        ProPromotionPhotoDTO result = proPromotionPhotoMapper.proPromotionPhotoToProPromotionPhotoDTO(proPromotionPhoto);
        proPromotionPhotoSearchRepository.save(proPromotionPhoto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proPromotionPhoto", proPromotionPhotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proPromotionPhotos -> get all the proPromotionPhotos.
     */
    @RequestMapping(value = "/proPromotionPhotos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProPromotionPhotoDTO>> getAllProPromotionPhotos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ProPromotionPhotos");
        Page<ProPromotionPhoto> page = proPromotionPhotoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proPromotionPhotos");
        return new ResponseEntity<>(page.getContent().stream()
            .map(proPromotionPhotoMapper::proPromotionPhotoToProPromotionPhotoDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /proPromotionPhotos/:id -> get the "id" proPromotionPhoto.
     */
    @RequestMapping(value = "/proPromotionPhotos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProPromotionPhotoDTO> getProPromotionPhoto(@PathVariable Long id) {
        log.debug("REST request to get ProPromotionPhoto : {}", id);
        ProPromotionPhoto proPromotionPhoto = proPromotionPhotoRepository.findOne(id);
        ProPromotionPhotoDTO proPromotionPhotoDTO = proPromotionPhotoMapper.proPromotionPhotoToProPromotionPhotoDTO(proPromotionPhoto);
        return Optional.ofNullable(proPromotionPhotoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proPromotionPhotos/:id -> delete the "id" proPromotionPhoto.
     */
    @RequestMapping(value = "/proPromotionPhotos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProPromotionPhoto(@PathVariable Long id) {
        log.debug("REST request to delete ProPromotionPhoto : {}", id);
        proPromotionPhotoRepository.delete(id);
        proPromotionPhotoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proPromotionPhoto", id.toString())).build();
    }

    /**
     * SEARCH  /_search/proPromotionPhotos/:query -> search for the proPromotionPhoto corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/proPromotionPhotos/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProPromotionPhotoDTO> searchProPromotionPhotos(@PathVariable String query) {
        log.debug("REST request to search ProPromotionPhotos for query {}", query);
        return StreamSupport
            .stream(proPromotionPhotoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(proPromotionPhotoMapper::proPromotionPhotoToProPromotionPhotoDTO)
            .collect(Collectors.toList());
    }
}
