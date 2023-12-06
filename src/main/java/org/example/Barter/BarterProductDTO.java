package org.example.Barter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Product.Product;
import org.example.Product.ProductDTO2;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BarterProductDTO {
    private Integer userId;
    private String username;
    private ProductDTO2 product;
    public static BarterProductDTO fromEntity(Product product) {
        return BarterProductDTO.builder()
                .userId(product.getSeller().getId())
                .username(product.getSeller().getUsername())
                .product(ProductDTO2.fromEntity(product))
                .build();
    }
}
