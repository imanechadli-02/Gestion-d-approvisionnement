package com.example.demo.service.impl;

import com.example.demo.entity.Produit;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.service.ProduitService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public Produit updateProduit(Long id, Produit produit) {
        Produit existing = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        existing.setReference(produit.getReference());
        existing.setNom(produit.getNom());
        existing.setDescription(produit.getDescription());
        existing.setPrixUnitaire(produit.getPrixUnitaire());
        existing.setCategorie(produit.getCategorie());
        existing.setStockActuel(produit.getStockActuel());
        existing.setStockMinimum(produit.getStockMinimum());
        existing.setUniteMesure(produit.getUniteMesure());
        return produitRepository.save(existing);
    }

    @Override
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }

    @Override
    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
    }

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
}
