package org.beli.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fees")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Fees {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String feePlatform; // Shopee, Tokopedia, etc.

    @Getter
    @Setter
    private String feeType;

    @Getter
    @Setter
    private Long feeAmount;

    @Getter
    @Setter
    private Long createdAt;

    @Getter
    @Setter
    private Long updatedAt;

}
