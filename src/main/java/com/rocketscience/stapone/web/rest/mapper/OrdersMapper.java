package com.rocketscience.stapone.web.rest.mapper;

import com.rocketscience.stapone.domain.*;
import com.rocketscience.stapone.web.rest.dto.OrdersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Orders and its DTO OrdersDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrdersMapper {

    @Mapping(source = "ordersProducts.id", target = "ordersProductsId")
    @Mapping(source = "ordersProducts.login", target = "ordersProductsLogin")
    OrdersDTO ordersToOrdersDTO(Orders orders);

    @Mapping(source = "ordersProductsId", target = "ordersProducts")
    Orders ordersDTOToOrders(OrdersDTO ordersDTO);

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
