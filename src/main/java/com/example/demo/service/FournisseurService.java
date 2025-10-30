package com.example.demo.service;

import com.example.demo.dto.fournisseur.RequestFournisseurDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.entity.Fournisseur;
import com.example.demo.mapper.FournisseurMapper;
import com.example.demo.repository.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    @Transactional
    public ResponseFournisseurDTO createFournisseur(RequestFournisseurDTO requestDTO) {
        if(requestDTO == null){
            throw new IllegalArgumentException("le fournisseur ne peut pas être null");
        }
        Fournisseur fournisseur = fournisseurMapper.toEntity(requestDTO);
        Fournisseur saved = fournisseurRepository.save(fournisseur);
        return fournisseurMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ResponseFournisseurDTO> findAll() {
        return fournisseurRepository.findAll()
                .stream().map(fournisseurMapper::toResponseDTO).toList();
    }
}

