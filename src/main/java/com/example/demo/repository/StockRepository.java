package com.example.demo.repository;

import com.example.demo.entity.Commande;
import com.example.demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock,Long> {
    Boolean existsStockByNumeroLot(String numeroLot);
    List<Stock> findByProduitIdOrderByDateEntreeAsc(Long produitId);
    boolean existsByCommande(Commande commande);

}
