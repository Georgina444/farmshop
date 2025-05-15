package com.georgina.farmshop.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    private String locality;

    private String address;

    private String city;

    private String country;

    // postal Code
    private String pinCode;

    private String phoneNumber;


}
