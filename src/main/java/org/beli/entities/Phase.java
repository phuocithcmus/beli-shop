package org.beli.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phases")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Phase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @Column(unique = true)
    private String phaseCode;

    @Getter
    @Setter
    private String phaseName;

    @Getter
    @Setter
    private Long createdAt;

    @Getter
    @Setter
    private Long updatedAt;
}
