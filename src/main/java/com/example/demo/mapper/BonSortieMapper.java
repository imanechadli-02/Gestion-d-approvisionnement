package com.example.demo.mapper;

import com.example.demo.dto.bonSortie.RequestBonSortieDTO;
import com.example.demo.dto.bonSortie.ResponseBonSortieDTO;
import com.example.demo.entity.BonSortie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonSortieDetailMapper.class)
public interface BonSortieMapper {

    // Pour convertir une entité en DTO de réponse
    @Mapping(source = "details", target = "details")
    ResponseBonSortieDTO toResponseDTO(BonSortie bonSortie);

    // Pour convertir une requête en entité (quand on crée un bon de sortie)
    BonSortie toEntity(RequestBonSortieDTO requestDTO);
}
