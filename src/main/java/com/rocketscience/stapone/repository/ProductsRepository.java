package com.rocketscience.stapone.repository;

import com.rocketscience.stapone.domain.Products;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Products entity.
 */
public interface ProductsRepository extends JpaRepository<Products,Long> {

}
