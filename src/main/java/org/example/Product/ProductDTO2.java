package org.example.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO2 {

    private Integer product_id;

    private String name;

    private List<String> imageURL;
    public static ProductDTO2 fromEntity(Product product) {
        List<String> imageURLs = new ArrayList<>();
        imageURLs.add("1");
        return ProductDTO2.builder()
                .product_id(product.getId())
                .name(product.getProductName())
                .imageURL(imageURLs)
                .build();
    }

}
