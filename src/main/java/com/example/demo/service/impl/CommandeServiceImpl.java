package com.example.demo.service.impl;

import com.example.demo.dto.commande.RequestCommandeDTO;
import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.entity.Commande;
import com.example.demo.entity.CommandeProduit;
import com.example.demo.entity.Fournisseur;
import com.example.demo.entity.Produit;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CommandeMapper;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.repository.FournisseurRepository;
import com.example.demo.repository.ProduitRepository;
import com.example.demo.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;
    private final CommandeMapper commandeMapper;

    @Override
    @Transactional
    public ResponseCommandeDTO createCommande(RequestCommandeDTO requestDTO) {

        if(requestDTO == null){
            throw new IllegalArgumentException("La commande ne peut pas être nulle");
        }
        Fournisseur fournisseur = fournisseurRepository.findById(requestDTO.getFournisseurId())
                .orElseThrow(() -> new IllegalArgumentException("Fournisseur introuvable"));
        Commande commande =new Commande();
        commande.setDateCommande(requestDTO.getDateCommande());
        commande.setStatutCommande(requestDTO.getStatutCommande());
        commande.setFournisseur(fournisseur);

        List<CommandeProduit> commandeProduits =requestDTO.getProduits().stream()
                .map(produitDto->{
                    Produit produit =produitRepository.findById(produitDto.getId()).orElseThrow(() -> new IllegalArgumentException("Produits introuvable"));
                    CommandeProduit cp = new CommandeProduit();
                    cp.setCommande(commande);
                    cp.setProduit(produit);
                    cp.setQuantite(produitDto.getQuantite());
                    cp.setPrixUnitaire(produitDto.getPrixUnitaire());
                    return cp;
                }).toList();

        double montantTotal =commandeProduits.stream().mapToDouble(cm->cm.getQuantite()*cm.getPrixUnitaire()).sum();
        commande.setMontantTotal(montantTotal);
        commande.setCommandeProduits(commandeProduits);

        Commande savedCommande = commandeRepository.save(commande);

        return commandeMapper.toResponseDTO(savedCommande);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseCommandeDTO> findAllCommandes() {
        return commandeRepository.findAll().stream().map(commandeMapper::toResponseDTO).toList();
    }

    @Override
    public ResponseCommandeDTO findCommandeById(Long id) {

        return commandeMapper.toResponseDTO(commandeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Commande n'existe pas de id :"+id))) ;
    }

    @Override
    public ResponseCommandeDTO updateCommandeById(Long id, RequestCommandeDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteCommande(Long id) {
        if(!commandeRepository.existsById(id)){
            throw new ResourceNotFoundException("Commande n'existe pas de id : "+id);
        }
        commandeRepository.deleteById(id);
    }
}
