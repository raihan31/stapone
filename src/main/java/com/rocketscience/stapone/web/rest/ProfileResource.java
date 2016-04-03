package com.rocketscience.stapone.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rocketscience.stapone.domain.Profile;
import com.rocketscience.stapone.repository.ProfileRepository;
import com.rocketscience.stapone.repository.search.ProfileSearchRepository;
import com.rocketscience.stapone.web.rest.util.HeaderUtil;
import com.rocketscience.stapone.web.rest.util.PaginationUtil;
import com.rocketscience.stapone.web.rest.dto.ProfileDTO;
import com.rocketscience.stapone.web.rest.mapper.ProfileMapper;
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
 * REST controller for managing Profile.
 */
@RestController
@RequestMapping("/api")
public class ProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfileResource.class);
        
    @Inject
    private ProfileRepository profileRepository;
    
    @Inject
    private ProfileMapper profileMapper;
    
    @Inject
    private ProfileSearchRepository profileSearchRepository;
    
    /**
     * POST  /profiles -> Create a new profile.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfileDTO> createProfile(@RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to save Profile : {}", profileDTO);
        if (profileDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("profile", "idexists", "A new profile cannot already have an ID")).body(null);
        }
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);
        profile = profileRepository.save(profile);
        ProfileDTO result = profileMapper.profileToProfileDTO(profile);
        profileSearchRepository.save(profile);
        return ResponseEntity.created(new URI("/api/profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("profile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /profiles -> Updates an existing profile.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws URISyntaxException {
        log.debug("REST request to update Profile : {}", profileDTO);
        if (profileDTO.getId() == null) {
            return createProfile(profileDTO);
        }
        Profile profile = profileMapper.profileDTOToProfile(profileDTO);
        profile = profileRepository.save(profile);
        ProfileDTO result = profileMapper.profileToProfileDTO(profile);
        profileSearchRepository.save(profile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("profile", profileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /profiles -> get all the profiles.
     */
    @RequestMapping(value = "/profiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProfileDTO>> getAllProfiles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Profiles");
        Page<Profile> page = profileRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/profiles");
        return new ResponseEntity<>(page.getContent().stream()
            .map(profileMapper::profileToProfileDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /profiles/:id -> get the "id" profile.
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) {
        log.debug("REST request to get Profile : {}", id);
        Profile profile = profileRepository.findOne(id);
        ProfileDTO profileDTO = profileMapper.profileToProfileDTO(profile);
        return Optional.ofNullable(profileDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /profiles/:id -> delete the "id" profile.
     */
    @RequestMapping(value = "/profiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        log.debug("REST request to delete Profile : {}", id);
        profileRepository.delete(id);
        profileSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profile", id.toString())).build();
    }

    /**
     * SEARCH  /_search/profiles/:query -> search for the profile corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/profiles/{query:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProfileDTO> searchProfiles(@PathVariable String query) {
        log.debug("REST request to search Profiles for query {}", query);
        return StreamSupport
            .stream(profileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(profileMapper::profileToProfileDTO)
            .collect(Collectors.toList());
    }
}
