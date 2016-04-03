package com.rocketscience.stapone.repository.search;

import com.rocketscience.stapone.domain.CatPromotionPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CatPromotionPhoto entity.
 */
public interface CatPromotionPhotoSearchRepository extends ElasticsearchRepository<CatPromotionPhoto, Long> {
}
