package com.example.demo.controller;


import com.example.demo.dto.commande.RequestCommandeDTO;
import com.example.demo.dto.commande.ResponseCommandeDTO;
import com.example.demo.entity.Commande;
import com.example.demo.repository.CommandeRepository;
import com.example.demo.service.CommandeService;
import com.example.demo.service.impl.CommandeServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/commandes")
@RequiredArgsConstructor
public class CommandeController {
    private final CommandeServiceImpl commandeService;
    @PostMapping
    public ResponseEntity<ResponseCommandeDTO> ajouterCommande(@Valid @RequestBody RequestCommandeDTO commandeDTO) {
        ResponseCommandeDTO responseCommandeDTO = commandeService.createCommande(commandeDTO);
        return new ResponseEntity<>(responseCommandeDTO, HttpStatus.CREATED);
    }

}
