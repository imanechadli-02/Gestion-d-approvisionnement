package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bon_sortie_ligne")
public class BonSortieDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bon_sortie_id", nullable = false)
    private BonSortie bonSortie;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(name = "quantite")
    private Integer quantite;


}
