package com.example.demo.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fournisseur")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La raison social du fournisseur est obligatoire")
    private String raison_sociale;

    @NotBlank(message = "l'adress est obligatoire")
    private String adresse;

    @NotBlank(message = "personne_contact est obligatoire")
    private String personne_contact;

    @NotBlank(message = "le email est obligatoire")
    @Email(message = "email non valide")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "le telephone est obligatoire")
    private String telephone;

    @NotBlank(message = "ville est obligatoire")
    private String ville;

    @NotBlank(message = "ice est obligatoire")
    @Column(nullable=false)
    private String ice;

}
