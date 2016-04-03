package com.rocketscience.stapone.repository;

import com.rocketscience.stapone.domain.CatPromotionPhoto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CatPromotionPhoto entity.
 */
public interface CatPromotionPhotoRepository extends JpaRepository<CatPromotionPhoto,Long> {

}
