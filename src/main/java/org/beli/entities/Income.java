package org.beli.entities;

import jakarta.persistence.*;
import lombok.*;
import org.beli.enums.IncomeFrom;

@Setter
@Getter
@Entity
@Table(name = "incomes")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int month;

    private int year;

    private String incomeFrom;

    private String note;

    private String amount;
}
