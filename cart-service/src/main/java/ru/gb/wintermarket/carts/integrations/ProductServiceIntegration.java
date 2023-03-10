package ru.gb.wintermarket.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.gb.wintermarket.api.dto.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.
                ofNullable(restTemplate.
                        getForObject("http://localhost:8189/winter/api/v1/products/"+id, ProductDto.class));

    }
}
