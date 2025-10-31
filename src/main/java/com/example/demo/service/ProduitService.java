package com.example.demo.service;

import com.example.demo.entity.Produit;
import java.util.List;

public interface ProduitService {
    Produit createProduit(Produit produit);
    Produit updateProduit(Long id, Produit produit);
    void deleteProduit(Long id);
    Produit getProduitById(Long id);
    List<Produit> getAllProduits();
}
