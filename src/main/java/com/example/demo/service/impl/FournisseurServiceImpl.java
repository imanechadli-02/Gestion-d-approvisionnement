package com.example.demo.service.impl;

import com.example.demo.dto.fournisseur.RequestFournisseurDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.entity.Fournisseur;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.FournisseurMapper;
import com.example.demo.repository.FournisseurRepository;
import com.example.demo.service.FournisseurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FournisseurServiceImpl implements FournisseurService {

    private final FournisseurRepository fournisseurRepository;
    private final FournisseurMapper fournisseurMapper;

    @Transactional
    @Override
    public ResponseFournisseurDTO createFournisseur(RequestFournisseurDTO requestDTO) {
        if(requestDTO == null){
            throw new IllegalArgumentException("le fournisseur ne peut pas être null");
        }
        Fournisseur fournisseur = fournisseurMapper.toEntity(requestDTO);
        Fournisseur saved = fournisseurRepository.save(fournisseur);
        return fournisseurMapper.toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResponseFournisseurDTO> FindALLFournisseur() {
        return fournisseurRepository.findAll()
                .stream().map(fournisseurMapper::toResponseDTO).toList();
    }

    @Transactional(readOnly = true)
    public ResponseFournisseurDTO findFournisserById(Long id) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec id : " + id));

        return fournisseurMapper.toResponseDTO(fournisseur);
    }

    @Transactional
    public ResponseFournisseurDTO updateFournisseur(Long id, RequestFournisseurDTO requestDTO) {
        Fournisseur fournisseur = fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec id : " + id));

        fournisseurMapper.updateEntityFromDTO(requestDTO, fournisseur);

        Fournisseur updatedFournisseur = fournisseurRepository.save(fournisseur);

        return fournisseurMapper.toResponseDTO(updatedFournisseur);
    }

    @Transactional
    public void deleteFournisseur(Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fournisseur non trouvé avec id : " + id);
        }
        fournisseurRepository.deleteById(id);
    }

}

