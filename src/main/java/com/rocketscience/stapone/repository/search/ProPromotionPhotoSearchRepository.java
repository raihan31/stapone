package com.rocketscience.stapone.repository.search;

import com.rocketscience.stapone.domain.ProPromotionPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProPromotionPhoto entity.
 */
public interface ProPromotionPhotoSearchRepository extends ElasticsearchRepository<ProPromotionPhoto, Long> {
}
