package org.beli.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String productType; // Shirt, Pants, etc.

    @Getter
    @Setter
    private String formType; // Oversize, fitSize.

    @Getter
    @Setter
    private String phaseCode;

    @Getter
    @Setter
    private Long entryDate;

    @Getter
    @Setter
    private Long amount;

    @Getter
    @Setter
    private Long transferFee;

    @Getter
    @Setter
    private Long remainingAmount;

    @Getter
    @Setter
    private Long createdAt;

    @Getter
    @Setter
    private Long updatedAt;
}
