package com.example.demo.mapper;

import com.example.demo.dto.bonSortieDetail.RequestBonSortieDetailDTO;
import com.example.demo.dto.bonSortieDetail.ResponseBonSortieDetailDTO;
import com.example.demo.entity.BonSortieDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BonSortieDetailMapper {

    // Entité -> DTO de réponse
    @Mapping(source = "produit.id", target = "produitId")
    @Mapping(source = "produit.nom", target = "produitNom")
    ResponseBonSortieDetailDTO toResponseDTO(BonSortieDetail detail);

    // DTO de requête -> Entité
    @Mapping(target = "produit.id", source = "produitId")
    BonSortieDetail toEntity(RequestBonSortieDetailDTO dto);
}
