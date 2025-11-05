package com.agendamedica.controller;

import com.agendamedica.model.Paciente;
import com.agendamedica.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/pacientes")
public class PacienteViewController {

    private final PacienteRepository repository;

    public PacienteViewController(PacienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", repository.findAll());
        return "pacientes"; // lista todos
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "form"; // usa o mesmo form.html
    }

    @PostMapping
    public String salvar(@ModelAttribute Paciente paciente) {
        repository.save(paciente);
        return "redirect:/web/pacientes";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Paciente paciente = repository.findById(id).orElseThrow();
        model.addAttribute("paciente", paciente);
        return "form"; // usa o mesmo form.html
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/web/pacientes";
    }
}