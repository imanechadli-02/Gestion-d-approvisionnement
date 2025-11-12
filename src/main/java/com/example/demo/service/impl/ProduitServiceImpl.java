package com.example.demo.service.impl;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Produit;
import com.example.demo.entity.Stock;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ProduitMapper;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.repository.StockRepository;
import com.example.demo.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final StockRepository stockRepository;


    @Transactional
    @Override
    public ResponseProduitDTO createProduit(RequestProduitDTO dto) {
        List<String> erreurs = new ArrayList<>();

        if (nomExists(dto.getNom())) {
            erreurs.add("Le nom du produit existe déjà");
        }
        if (referenceExists(dto.getReference())) {
            erreurs.add("La référence du produit existe déjà");
        }

        if (!erreurs.isEmpty()) {
            throw new DuplicateResourceException(erreurs);
        }

        Produit produit = produitMapper.toEntity(dto);
        Produit saved = produitRepository.save(produit);
        return produitMapper.toResponseDTO(saved);
    }

    @Transactional
    @Override
    public ResponseProduitDTO updateProduit(Long id, RequestProduitDTO dto) {
        Produit existing = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec id : " + id));

        produitMapper.updateEntityFromDTO(dto, existing);
        Produit updated = produitRepository.save(existing);
        return produitMapper.toResponseDTO(updated);
    }

    @Transactional
    @Override
    public void deleteProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit non trouvé avec id : " + id);
        }
        produitRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseStockDTO> getStockByProduitId(Long produitId) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec id : " + produitId));

        List<Stock> stocks = stockRepository.findByProduitIdOrderByDateEntreeAsc(produitId);

        // Remplir uniquement id et numeroLot
        return stocks.stream()
                .map(s -> new ResponseStockDTO(s.getId(), s.getNumeroLot(), null, null, null, null, null, null, null, null))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseProduitDTO getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec id : " + id));
        return produitMapper.toResponseDTO(produit);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseProduitDTO> getAllProduits() {
        return produitRepository.findAll()
                .stream().map(produitMapper::toResponseDTO).toList();
    }

    @Override
    public boolean nomExists(String nom) {
        return produitRepository.existsByNom(nom);
    }

    @Override
    public boolean referenceExists(String reference) {
        return produitRepository.existsByReference(reference);
    }



}
