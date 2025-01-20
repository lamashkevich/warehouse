package com.lamashkevich.warehouse.client.shatem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {
    private Long id;
    private String code;
    private String tradeMarkName;
    private String name;
    private String description;
    private String unitOfMeasure;
    private boolean isRepaired;
}
