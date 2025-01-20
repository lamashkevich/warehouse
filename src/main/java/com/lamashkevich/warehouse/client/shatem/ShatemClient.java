package com.lamashkevich.warehouse.client.shatem;

import com.lamashkevich.warehouse.client.shatem.payload.ArticleCard;
import com.lamashkevich.warehouse.client.shatem.payload.ArticleFilter;

import java.util.List;

public interface ShatemClient {

    List<ArticleCard> searchByQuery(String query);

    List<ArticleCard> searchByFilter(ArticleFilter request);

    List<ArticleCard> searchAnalog(Long id);

}
