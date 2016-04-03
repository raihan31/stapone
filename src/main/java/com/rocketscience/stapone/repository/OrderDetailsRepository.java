package com.rocketscience.stapone.repository;

import com.rocketscience.stapone.domain.OrderDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderDetails entity.
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

}
