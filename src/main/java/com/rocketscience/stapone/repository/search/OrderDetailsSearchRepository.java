package com.rocketscience.stapone.repository.search;

import com.rocketscience.stapone.domain.OrderDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrderDetails entity.
 */
public interface OrderDetailsSearchRepository extends ElasticsearchRepository<OrderDetails, Long> {
}
