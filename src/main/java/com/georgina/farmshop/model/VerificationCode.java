package com.georgina.farmshop.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VerificationCode {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp; // one time pin

    // ChatGPT suggestion
    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne
    private User user;

    // when seller wants to log in
    @OneToOne
    private Seller seller;






}
