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
    private final StockRepository StockRepository;
    private final MouvementStockRepository MouvementStockRepository;

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
        if (!bonSortieRepository.existsById(id)) {
            throw new RuntimeException("Bon de sortie introuvable avec ID : " + id);
        }
        bonSortieRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void validerBonDeSortie(Long id) {
        // Récupérer le bon de sortie
        BonSortie bon = bonSortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de sortie introuvable avec ID : " + id));

        if (bon.getStatut() != StatutBon.BROULLION) {
            throw new RuntimeException("Seuls les bons en brouillon peuvent être validés.");
        }

        // Pour chaque détail du bon
        for (BonSortieDetail detail : bon.getDetails()) {
            Produit produit = detail.getProduit();
            int quantiteDemandee = detail.getQuantite();

            // Récupérer les stocks disponibles pour ce produit par FIFO
            List<Stock> stocks = StockRepository.findByProduitIdOrderByDateEntreeAsc(produit.getId());

            for (Stock s : stocks) {
                if (quantiteDemandee <= 0) break;

                int quantiteDisponible = s.getQuantite();
                int quantiteALever = Math.min(quantiteDemandee, quantiteDisponible);

                // Mettre à jour le stock
                s.setQuantite(quantiteDisponible - quantiteALever);
                StockRepository.save(s);

                // Créer un mouvement de stock
                MouvementStock mouvement = new MouvementStock();
                mouvement.setStock(s);
                mouvement.setQuantite(quantiteALever);
                mouvement.setTypeMouvement(TypeMouvement.SORTIE);
                mouvement.setDateMouvement(LocalDateTime.now());
                MouvementStockRepository.save(mouvement);

                quantiteDemandee -= quantiteALever;
            }

            if (quantiteDemandee > 0) {
                throw new RuntimeException("Stock insuffisant pour le produit : " + produit.getNom());
            }
        }

        // Mettre à jour le statut du bon
        bon.setStatut(StatutBon.VALIDE);
        bonSortieRepository.save(bon);
    }


    @Override
    public void annulerBonDeSortie(Long id) {
        BonSortie bon = bonSortieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon non trouvé"));

        if (bon.getStatut() != StatutBon.BROULLION) {
            throw new RuntimeException("Seuls les bons en brouillon peuvent être annulés");
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
