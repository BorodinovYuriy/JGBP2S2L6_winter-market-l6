package ru.gb.wintermarket.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.wintermarket.api.dto.CartDto;
import ru.gb.wintermarket.carts.converters.CartConverter;
import ru.gb.wintermarket.carts.model.Cart;
import ru.gb.wintermarket.carts.services.CartService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/add/{id}")
    public void addToCart(@PathVariable Long id){
        cartService.add(id);
    }
    @GetMapping()
    public CartDto getCurrentCart(){
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }

    @PutMapping("/increase/{id}")
    public void increaseProductInCart(@PathVariable Long id){
        cartService.increaseProductInCart(id);
    }
    @PutMapping("/decrease/{id}")
    public void decreaseProductInCart(@PathVariable Long id){
        cartService.decreaseProductInCart(id);
    }
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id){
        cartService.removeProductById(id);

    }
    @DeleteMapping()
    public void clearCart(){
        cartService.clear();

    }

}

