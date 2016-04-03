package com.rocketscience.stapone.web.rest.mapper;

import com.rocketscience.stapone.domain.*;
import com.rocketscience.stapone.web.rest.dto.ProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Profile and its DTO ProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfileMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    ProfileDTO profileToProfileDTO(Profile profile);

    @Mapping(source = "userId", target = "user")
    Profile profileDTOToProfile(ProfileDTO profileDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
