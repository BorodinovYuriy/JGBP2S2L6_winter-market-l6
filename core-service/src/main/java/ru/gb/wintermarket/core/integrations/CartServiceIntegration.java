package ru.gb.wintermarket.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gb.wintermarket.api.dto.CartDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<CartDto> getCurrentCart() {
        return Optional.
                ofNullable(restTemplate.
                        getForObject("http://localhost:8190/winter-carts/api/v1/cart/", CartDto.class));

    }

    public void clearCart() {
        restTemplate.delete("http://localhost:8190/winter-carts/api/v1/cart/");
    }
}
