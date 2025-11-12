package com.example.demo.service.impl;

import com.example.demo.dto.bonSortie.RequestBonSortieDTO;
import com.example.demo.dto.bonSortie.ResponseBonSortieDTO;
import com.example.demo.entity.*;
import com.example.demo.entity.enums.StatutBon;
import com.example.demo.entity.enums.TypeMouvement;
import com.example.demo.mapper.BonSortieMapper;
import com.example.demo.repository.*;
import com.example.demo.service.BonSortieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BonSortieServiceImpl implements BonSortieService {

    private final BonSortieRepository bonSortieRepository;
    private final BonSortieDetailRepository bonSortieDetailRepository;
    private final ProduitRepository produitRepository;
    private final BonSortieMapper bonSortieMapper;
    private final StockRepository stockRepository;
    private final MouvementStockRepository mouvementStockRepository;

    @Override
    public ResponseBonSortieDTO createBonDeSortie(RequestBonSortieDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("Le bon de sortie ne peut pas être nul");
        }

        BonSortie bonSortie = new BonSortie();
        bonSortie.setNumeroBon(requestDTO.getNumeroBon());
        bonSortie.setDateSortie(LocalDate.now());
        bonSortie.setAtelier(requestDTO.getAtelier());
        bonSortie.setMotif(requestDTO.getMotif());
        bonSortie.setStatut(StatutBon.BROULLION);

        requestDTO.getDetails().forEach(detailDTO -> {
            Produit produit = produitRepository.findById(detailDTO.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + detailDTO.getProduitId()));

            BonSortieDetail detail = new BonSortieDetail();
            detail.setProduit(produit);
            detail.setQuantite(detailDTO.getQuantite());
            detail.setBonSortie(bonSortie);

            bonSortie.getDetails().add(detail);
        });

        BonSortie saved = bonSortieRepository.save(bonSortie);
        return bonSortieMapper.toResponseDTO(saved);
    }

    @Override
    public List<ResponseBonSortieDTO> findAllBonsDeSortie() {
        return bonSortieRepository.findAll()
                .stream()
                .map(bonSortieMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseBonSortieDTO findBonDeSortieById(Long id) {
        BonSortie bonSortie = bonSortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de sortie introuvable avec ID : " + id));
        return bonSortieMapper.toResponseDTO(bonSortie);
    }

    @Override
    public ResponseBonSortieDTO updateBonDeSortie(Long id, RequestBonSortieDTO requestDTO) {
        BonSortie existing = bonSortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de sortie introuvable avec ID : " + id));

        // Vérification : un bon validé ne peut pas être modifié
        if (existing.getStatut() == StatutBon.VALIDE) {
            throw new IllegalStateException("Impossible de modifier un bon déjà validé.");
        }

        existing.setAtelier(requestDTO.getAtelier());
        existing.setMotif(requestDTO.getMotif());
        existing.getDetails().clear();

        requestDTO.getDetails().forEach(detailDTO -> {
            Produit produit = produitRepository.findById(detailDTO.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé : " + detailDTO.getProduitId()));

            BonSortieDetail detail = new BonSortieDetail();
            detail.setProduit(produit);
            detail.setQuantite(detailDTO.getQuantite());
            detail.setBonSortie(existing);
            existing.getDetails().add(detail);
        });

        BonSortie updated = bonSortieRepository.save(existing);
        return bonSortieMapper.toResponseDTO(updated);
    }


    @Override
    public void deleteBonDeSortie(Long id) {
        BonSortie bon = bonSortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de sortie introuvable avec ID : " + id));

        // Vérification : un bon validé ne peut pas être supprimé
        if (bon.getStatut() == StatutBon.VALIDE) {
            throw new IllegalStateException("Impossible de supprimer un bon déjà validé.");
        }

        bonSortieRepository.delete(bon);
    }

    @Override
    @Transactional
    public void validerBonDeSortie(Long bonId) {
        BonSortie bon = bonSortieRepository.findById(bonId)
                .orElseThrow(() -> new RuntimeException("Bon de sortie introuvable"));

        if (bon.getStatut() != StatutBon.BROULLION) {
            throw new IllegalStateException("Seuls les bons en brouillon peuvent être validés.");
        }

        for (BonSortieDetail detail : bon.getDetails()) {
            Produit produit = detail.getProduit();
            int quantiteDemandee = detail.getQuantite();

            List<Stock> lots = stockRepository.findByProduitIdOrderByDateEntreeAsc(produit.getId());

            for (Stock lot : lots) {
                if (quantiteDemandee <= 0) break;

                int quantiteDisponible = lot.getQuantite();
                if (quantiteDisponible <= 0) continue;

                int quantiteALever = Math.min(quantiteDemandee, quantiteDisponible);

                MouvementStock mouvement = new MouvementStock();
                mouvement.setStock(lot);
                mouvement.setQuantite(quantiteALever);
                mouvement.setTypeMouvement(TypeMouvement.SORTIE);
                mouvement.setDateMouvement(LocalDateTime.now());
                mouvementStockRepository.save(mouvement);

                lot.setQuantite(quantiteDisponible - quantiteALever);
                stockRepository.save(lot);

                quantiteDemandee -= quantiteALever;
            }

            if (quantiteDemandee > 0) {
                throw new IllegalStateException("Stock insuffisant pour le produit : " + produit.getNom());
            }
        }

        bon.setStatut(StatutBon.VALIDE);
        bonSortieRepository.save(bon);
    }




    @Override
    public void annulerBonDeSortie(Long id) {
        BonSortie bon = bonSortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de sortie introuvable avec ID : " + id));

        if (bon.getStatut() == StatutBon.VALIDE) {
            throw new IllegalStateException("Impossible d'annuler un bon déjà validé.");
        }

        if (bon.getStatut() != StatutBon.BROULLION) {
            throw new IllegalStateException("Seuls les bons en brouillon peuvent être annulés.");
        }

        bon.setStatut(StatutBon.ANNULE);
        bonSortieRepository.save(bon);
    }

    @Override
    public List<ResponseBonSortieDTO> findBonsByAtelier(String atelier) {
        List<BonSortie> bons = bonSortieRepository.findByAtelier(atelier);
        return bons.stream()
                .map(bonSortieMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
