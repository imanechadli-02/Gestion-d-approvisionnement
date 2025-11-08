package com.example.demo.dto.bonSortieDetail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBonSortieDetailDTO {

    private Long id;
    private Long produitId;
    private String produitNom; // utile pour l'affichage
    private Integer quantite;
}
