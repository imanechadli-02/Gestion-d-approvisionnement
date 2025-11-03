package com.example.demo.service;

import com.example.demo.dto.commande.RequestCommandeDTO;
import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;

import java.util.List;

public interface CommandeService {
    ResponseCommandeDTO createCommande(RequestCommandeDTO requestDTO);
    List<ResponseFournisseurDTO> findAllProduits();
    ResponseCommandeDTO findCommandeById(Long id);
    ResponseCommandeDTO updateCommandeById(Long id, RequestCommandeDTO requestDTO);
    void deleteCommande(Long id);
}
