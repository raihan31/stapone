package com.rocketscience.stapone.web.rest.mapper;

import com.rocketscience.stapone.domain.*;
import com.rocketscience.stapone.web.rest.dto.CatPromotionPhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CatPromotionPhoto and its DTO CatPromotionPhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatPromotionPhotoMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    CatPromotionPhotoDTO catPromotionPhotoToCatPromotionPhotoDTO(CatPromotionPhoto catPromotionPhoto);

    @Mapping(source = "categoryId", target = "category")
    CatPromotionPhoto catPromotionPhotoDTOToCatPromotionPhoto(CatPromotionPhotoDTO catPromotionPhotoDTO);

    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
