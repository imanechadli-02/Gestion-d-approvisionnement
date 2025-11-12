package com.example.demo.service;

import com.example.demo.dto.produit.RequestProduitDTO;
import com.example.demo.dto.produit.ResponseProduitDTO;
import com.example.demo.dto.stock.ResponseStockDTO;
import com.example.demo.entity.Stock;

import java.util.List;

public interface ProduitService {

    ResponseProduitDTO createProduit(RequestProduitDTO dto);

    ResponseProduitDTO updateProduit(Long id, RequestProduitDTO dto);

    void deleteProduit(Long id);

    ResponseProduitDTO getProduitById(Long id);

    List<ResponseProduitDTO> getAllProduits();

    public List<ResponseStockDTO> getStockByProduitId(Long produitId);

    public boolean nomExists(String nom);

    public boolean referenceExists(String reference);

}
