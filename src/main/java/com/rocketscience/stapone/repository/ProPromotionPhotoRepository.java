package com.rocketscience.stapone.repository;

import com.rocketscience.stapone.domain.ProPromotionPhoto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProPromotionPhoto entity.
 */
public interface ProPromotionPhotoRepository extends JpaRepository<ProPromotionPhoto,Long> {

}
