package com.lamashkevich.warehouse.client.shatem.payload;

import java.util.List;

public record ArticleFilter(List<Key> keys) {
    public record Key(String articleCode, String tradeMarkName) {}
}
