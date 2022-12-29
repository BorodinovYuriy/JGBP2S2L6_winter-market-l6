package ru.gb.wintermarket.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.wintermarket.api.dto.ProductDto;
import ru.gb.wintermarket.api.exceptions.ResourceNotFoundException;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
//    private final RestTemplate restTemplate;//отправка синхронная(плохо)
    private final WebClient productServiceWebClient;

//    public Optional<ProductDto> getProductById(Long id) {
//        return Optional.
//                ofNullable(restTemplate.
//                        getForObject("http://localhost:8189/winter/api/v1/products/"+id, ProductDto.class));
//
//    }
public ProductDto getProductById(Long id) {
    return productServiceWebClient.get()
            .uri("/api/v1/products/" + id)
            .retrieve()//мы хотим получить ответ
            .onStatus(
                    httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                    clientResponse -> Mono.error(new ResourceNotFoundException("Товар не найден в продуктовом МС"))
                    )
            .bodyToMono(ProductDto.class)//хотим тело преобразовать к объекту//пока синхронно (не flux)
            .block();
}





















}
