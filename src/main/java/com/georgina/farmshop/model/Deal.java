package com.georgina.farmshop.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // which category are you using the discount on
    private Integer discount;

    @OneToOne
    private HomeCategory category;

}
