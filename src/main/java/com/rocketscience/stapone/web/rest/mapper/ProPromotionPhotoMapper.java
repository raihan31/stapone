package com.rocketscience.stapone.web.rest.mapper;

import com.rocketscience.stapone.domain.*;
import com.rocketscience.stapone.web.rest.dto.ProPromotionPhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProPromotionPhoto and its DTO ProPromotionPhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProPromotionPhotoMapper {

    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    ProPromotionPhotoDTO proPromotionPhotoToProPromotionPhotoDTO(ProPromotionPhoto proPromotionPhoto);

    @Mapping(source = "productsId", target = "products")
    ProPromotionPhoto proPromotionPhotoDTOToProPromotionPhoto(ProPromotionPhotoDTO proPromotionPhotoDTO);

    default Products productsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Products products = new Products();
        products.setId(id);
        return products;
    }
}
