package com.rocketscience.stapone.repository;

import com.rocketscience.stapone.domain.DeptPromotionPhoto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DeptPromotionPhoto entity.
 */
public interface DeptPromotionPhotoRepository extends JpaRepository<DeptPromotionPhoto,Long> {

}
