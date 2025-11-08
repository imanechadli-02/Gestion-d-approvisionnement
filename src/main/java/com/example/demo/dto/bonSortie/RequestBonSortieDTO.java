package com.example.demo.dto.bonSortie;

import com.example.demo.dto.bonSortieDetail.RequestBonSortieDetailDTO;
import com.example.demo.entity.enums.StatutBon;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestBonSortieDTO {

    private String numeroBon;

    @NotNull(message = "La date de sortie est obligatoire")
    private LocalDate dateSortie;

    @NotNull(message = "L'atelier est obligatoire")
    private String atelier;

    private String motif;

    @NotNull(message = "Le statut du bon est obligatoire")
    private StatutBon statut;

    @NotNull(message = "La liste des détails du bon de sortie est obligatoire")
    private List<RequestBonSortieDetailDTO> details;
}
