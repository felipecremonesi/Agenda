package com.agendamedica.controller;

import com.agendamedica.model.Paciente;
import com.agendamedica.repository.PacienteRepository;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteRepository repository;

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(repository.findAll());
    }

    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<Paciente> criar(@Valid @RequestBody Paciente paciente) {
        Paciente novo = repository.save(paciente);
        return ResponseEntity.ok(novo);
    }

    @SuppressWarnings("null")
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @Valid @RequestBody Paciente paciente) {
        return repository.findById(id)
                .map(p -> {
                    p.setNome(paciente.getNome());
                    p.setTelefone(paciente.getTelefone());
                    p.setEmail(paciente.getEmail());
                    Paciente atualizado = repository.save(p);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @SuppressWarnings("null")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}