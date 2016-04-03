package com.rocketscience.stapone.web.rest.mapper;

import com.rocketscience.stapone.domain.*;
import com.rocketscience.stapone.web.rest.dto.OrderDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrderDetails and its DTO OrderDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailsMapper {

    @Mapping(source = "orders.id", target = "ordersId")
    @Mapping(source = "orders.descriptions", target = "ordersDescriptions")
    @Mapping(source = "products.id", target = "productsId")
    @Mapping(source = "products.name", target = "productsName")
    OrderDetailsDTO orderDetailsToOrderDetailsDTO(OrderDetails orderDetails);

    @Mapping(source = "ordersId", target = "orders")
    @Mapping(source = "productsId", target = "products")
    OrderDetails orderDetailsDTOToOrderDetails(OrderDetailsDTO orderDetailsDTO);

    default Orders ordersFromId(Long id) {
        if (id == null) {
            return null;
        }
        Orders orders = new Orders();
        orders.setId(id);
        return orders;
    }

    default Products productsFromId(Long id) {
        if (id == null) {
            return null;
        }
        Products products = new Products();
        products.setId(id);
        return products;
    }
}
