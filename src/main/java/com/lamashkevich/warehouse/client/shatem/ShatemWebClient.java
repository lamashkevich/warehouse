package com.lamashkevich.warehouse.client.shatem;

import com.lamashkevich.warehouse.client.shatem.payload.ArticleCard;
import com.lamashkevich.warehouse.client.shatem.payload.ArticleFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ShatemWebClient implements ShatemClient {

    private final WebClient webClient;

    @Override
    public List<ArticleCard> searchByQuery(String query) {
        return this.webClient
                .get()
                .uri("articles/search?searchString=%s".formatted(query))
                .retrieve()
                .bodyToFlux(ArticleCard.class)
                .collectList()
                .block();
    }

    @Override
    public List<ArticleCard> searchByFilter(ArticleFilter request) {
        return this.webClient
                .post()
                .uri("articles/search")
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(ArticleCard.class)
                .collectList()
                .block();
    }

    @Override
    public List<ArticleCard> searchAnalog(Long id) {
        return this.webClient
                .get()
                .uri("articles/%d/analogs".formatted(id))
                .retrieve()
                .bodyToFlux(ArticleCard.class)
                .collectList()
                .block();
    }

}
