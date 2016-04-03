package com.rocketscience.stapone.repository.search;

import com.rocketscience.stapone.domain.DeptPromotionPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DeptPromotionPhoto entity.
 */
public interface DeptPromotionPhotoSearchRepository extends ElasticsearchRepository<DeptPromotionPhoto, Long> {
}
