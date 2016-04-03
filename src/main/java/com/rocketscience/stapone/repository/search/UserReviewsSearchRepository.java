package com.rocketscience.stapone.repository.search;

import com.rocketscience.stapone.domain.UserReviews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserReviews entity.
 */
public interface UserReviewsSearchRepository extends ElasticsearchRepository<UserReviews, Long> {
}
