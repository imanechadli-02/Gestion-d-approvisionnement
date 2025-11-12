package com.example.demo.repository;

import com.example.demo.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByFournisseur_Id(Long fournisseurId);
}
