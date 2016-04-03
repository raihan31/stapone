package com.rocketscience.stapone.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the ProPromotionPhoto entity.
 */
public class ProPromotionPhotoDTO implements Serializable {

    private Long id;

    private String name;


    @Lob
    private byte[] photo;

    private String photoContentType;

    private String descriptions;


    private Long productsId;

    private String productsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }
    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public Long getProductsId() {
        return productsId;
    }

    public void setProductsId(Long productsId) {
        this.productsId = productsId;
    }

    public String getProductsName() {
        return productsName;
    }

    public void setProductsName(String productsName) {
        this.productsName = productsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProPromotionPhotoDTO proPromotionPhotoDTO = (ProPromotionPhotoDTO) o;

        if ( ! Objects.equals(id, proPromotionPhotoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProPromotionPhotoDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", photo='" + photo + "'" +
            ", descriptions='" + descriptions + "'" +
            '}';
    }
}
