package com.georgina.farmshop.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String categoryName;


    @NotNull
    @Column(unique = true)
    private String categoryId;

    @ManyToOne
    private Category parentCategory;

    @NotNull
    private Integer level;

    // level 1: milk products, herbs, eggs, meat, veggies, fruits
    // level 2: milk products -> cow, sheep, goat
    // level 3: cow -> milk, cheese, cream cheese ..

}
