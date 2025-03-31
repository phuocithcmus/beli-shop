package org.beli.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "revenues")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Revenues {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String channel; // Shopee, Tokopedia, etc.

    @Getter
    @Setter
    private Long price;

    @Getter
    @Setter
    private Long sellPrice;

    @Getter
    @Setter
    private Long revenue;

    @Getter
    @Setter
    private String productId;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private Long createdAt;

    @Getter
    @Setter
    private Long updatedAt;

    @Getter
    @Setter
    private String fees;
}
