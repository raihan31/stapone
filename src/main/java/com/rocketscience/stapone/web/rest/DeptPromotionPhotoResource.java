package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.DeptPromotionPhoto;
import com.rocketscience.stapone.repository.DeptPromotionPhotoRepository;
import com.rocketscience.stapone.repository.search.DeptPromotionPhotoSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.DeptPromotionPhotoDTO;
import com.rocketscience.stapone.web.rest.mapper.DeptPromotionPhotoMapper;
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
 * REST controller for managing DeptPromotionPhoto.
 */
@RestController
@RequestMapping("/api")
public class DeptPromotionPhotoResource {

    private final Logger log = LoggerFactory.getLogger(DeptPromotionPhotoResource.class);
        
    @Inject
    private DeptPromotionPhotoRepository deptPromotionPhotoRepository;
    
    @Inject
    private DeptPromotionPhotoMapper deptPromotionPhotoMapper;
    
    @Inject
    private DeptPromotionPhotoSearchRepository deptPromotionPhotoSearchRepository;
    
    /**
     * POST  /deptPromotionPhotos -> Create a new deptPromotionPhoto.
     */
    @RequestMapping(value = "/deptPromotionPhotos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeptPromotionPhotoDTO> createDeptPromotionPhoto(@RequestBody DeptPromotionPhotoDTO deptPromotionPhotoDTO) throws URISyntaxException {
        log.debug("REST request to save DeptPromotionPhoto : {}", deptPromotionPhotoDTO);
        if (deptPromotionPhotoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("deptPromotionPhoto", "idexists", "A new deptPromotionPhoto cannot already have an ID")).body(null);
        }
        DeptPromotionPhoto deptPromotionPhoto = deptPromotionPhotoMapper.deptPromotionPhotoDTOToDeptPromotionPhoto(deptPromotionPhotoDTO);
        deptPromotionPhoto = deptPromotionPhotoRepository.save(deptPromotionPhoto);
        DeptPromotionPhotoDTO result = deptPromotionPhotoMapper.deptPromotionPhotoToDeptPromotionPhotoDTO(deptPromotionPhoto);
        deptPromotionPhotoSearchRepository.save(deptPromotionPhoto);
        return ResponseEntity.created(new URI("/api/deptPromotionPhotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deptPromotionPhoto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deptPromotionPhotos -> Updates an existing deptPromotionPhoto.
     */
    @RequestMapping(value = "/deptPromotionPhotos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeptPromotionPhotoDTO> updateDeptPromotionPhoto(@RequestBody DeptPromotionPhotoDTO deptPromotionPhotoDTO) throws URISyntaxException {
        log.debug("REST request to update DeptPromotionPhoto : {}", deptPromotionPhotoDTO);
        if (deptPromotionPhotoDTO.getId() == null) {
            return createDeptPromotionPhoto(deptPromotionPhotoDTO);
        }
        DeptPromotionPhoto deptPromotionPhoto = deptPromotionPhotoMapper.deptPromotionPhotoDTOToDeptPromotionPhoto(deptPromotionPhotoDTO);
        deptPromotionPhoto = deptPromotionPhotoRepository.save(deptPromotionPhoto);
        DeptPromotionPhotoDTO result = deptPromotionPhotoMapper.deptPromotionPhotoToDeptPromotionPhotoDTO(deptPromotionPhoto);
        deptPromotionPhotoSearchRepository.save(deptPromotionPhoto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deptPromotionPhoto", deptPromotionPhotoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deptPromotionPhotos -> get all the deptPromotionPhotos.
     */
    @RequestMapping(value = "/deptPromotionPhotos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DeptPromotionPhotoDTO>> getAllDeptPromotionPhotos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of DeptPromotionPhotos");
        Page<DeptPromotionPhoto> page = deptPromotionPhotoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deptPromotionPhotos");
        return new ResponseEntity<>(page.getContent().stream()
            .map(deptPromotionPhotoMapper::deptPromotionPhotoToDeptPromotionPhotoDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /deptPromotionPhotos/:id -> get the "id" deptPromotionPhoto.
     */
    @RequestMapping(value = "/deptPromotionPhotos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeptPromotionPhotoDTO> getDeptPromotionPhoto(@PathVariable Long id) {
        log.debug("REST request to get DeptPromotionPhoto : {}", id);
        DeptPromotionPhoto deptPromotionPhoto = deptPromotionPhotoRepository.findOne(id);
        DeptPromotionPhotoDTO deptPromotionPhotoDTO = deptPromotionPhotoMapper.deptPromotionPhotoToDeptPromotionPhotoDTO(deptPromotionPhoto);
        return Optional.ofNullable(deptPromotionPhotoDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deptPromotionPhotos/:id -> delete the "id" deptPromotionPhoto.
     */
    @RequestMapping(value = "/deptPromotionPhotos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeptPromotionPhoto(@PathVariable Long id) {
        log.debug("REST request to delete DeptPromotionPhoto : {}", id);
        deptPromotionPhotoRepository.delete(id);
        deptPromotionPhotoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deptPromotionPhoto", id.toString())).build();
    }

    /**
     * SEARCH  /_search/deptPromotionPhotos/:query -> search for the deptPromotionPhoto corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/deptPromotionPhotos/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeptPromotionPhotoDTO> searchDeptPromotionPhotos(@PathVariable String query) {
        log.debug("REST request to search DeptPromotionPhotos for query {}", query);
        return StreamSupport
            .stream(deptPromotionPhotoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(deptPromotionPhotoMapper::deptPromotionPhotoToDeptPromotionPhotoDTO)
            .collect(Collectors.toList());
    }
}
