package com.example.demo.entity;


import com.example.demo.entity.enums.StatutBon;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bon_sortie")
public class BonSortie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_bon", length = 50)
    private String numeroBon;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @Column(name = "atelier", length = 100)
    private String atelier;

    @Column(name = "motif", length = 50)
    private String motif;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutBon statut;

    @OneToMany(mappedBy = "bonSortie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonSortieDetail> details = new ArrayList<>();

//    public void addDetail(BonSortieDetail detail) {
//        details.add(detail);
//        detail.setBonSortie(this);
//    }
}
