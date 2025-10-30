package com.example.demo.controller;


import com.example.demo.dto.fournisseur.RequestFournisseurDTO;
import com.example.demo.dto.fournisseur.ResponseFournisseurDTO;
import com.example.demo.service.FournisseurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseur")
@RequiredArgsConstructor

public class FournisseurController {
    private final FournisseurService fournisseurService;

    @PostMapping
    public ResponseEntity<ResponseFournisseurDTO> createFournisseur(
            @Valid @RequestBody RequestFournisseurDTO requestdto){
        ResponseFournisseurDTO response = fournisseurService.createFournisseur(requestdto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseFournisseurDTO>>  getALLFournisseur(){
        List<ResponseFournisseurDTO> fournisseursDTO = fournisseurService.findAll();
        return new ResponseEntity<>(fournisseursDTO, HttpStatus.OK);
    }
}
