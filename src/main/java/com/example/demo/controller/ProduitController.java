package com.example.demo.controller;

import com.example.demo.entity.Produit;
import com.example.demo.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @PostMapping
    public ResponseEntity<Produit> create(@RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.createProduit(produit));
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getAll() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> getById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> update(@PathVariable Long id, @RequestBody Produit produit) {
        return ResponseEntity.ok(produitService.updateProduit(id, produit));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
