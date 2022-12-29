package ru.gb.wintermarket.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.wintermarket.api.dto.ProductDto;
import ru.gb.wintermarket.api.exceptions.ResourceNotFoundException;
import ru.gb.wintermarket.carts.integrations.ProductServiceIntegration;
import ru.gb.wintermarket.carts.model.Cart;
import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }
    public Cart getCurrentCart() {
        return tempCart;
    }
    public void add(Long productId){
        ProductDto productDto = productServiceIntegration.
                getProductById(productId);
        tempCart.add(productDto);
    }

    public void increaseProductInCart(Long id) {
       ProductDto productDto = productServiceIntegration.getProductById(id);
        tempCart.increaseProduct(productDto);
    }

    public void decreaseProductInCart(Long id) {
        ProductDto productDto = productServiceIntegration.getProductById(id);
        tempCart.decreaseProduct(productDto);
    }

    public void removeProductById(Long id) {
        ProductDto productDto = productServiceIntegration.getProductById(id);
        tempCart.remove(productDto);
    }

    public void clear() {
        tempCart.clear();
    }
}
