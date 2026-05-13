package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.Motorista;
import br.dev.mmddkk.loxecutivo.repository.MotoristaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/motoristas")
public class MotoristaController {

    @Autowired
    private MotoristaRepository motoristaRepository;

    // ✅ LISTAR
    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("motorista/lista");
        mv.addObject("motoristas", motoristaRepository.findAll());
        return mv;
    }

    // ✅ FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("motorista/form");
        mv.addObject("motorista", new Motorista());
        return mv;
    }

    // ✅ SALVAR NOVO
    @PostMapping
    public ModelAndView salvar(@Valid @ModelAttribute Motorista motorista,
                               BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("motorista/form");
        }

        motoristaRepository.save(motorista);
        return new ModelAndView("redirect:/");
    }

    // ✅ EDITAR
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        Optional<Motorista> motoristaOpt = motoristaRepository.findById(id);

        if (motoristaOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("motorista/form");
            mv.addObject("motorista", motoristaOpt.get());
            return mv;
        }

        return new ModelAndView("redirect:/motoristas");
    }

    // ✅ ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Integer id,
                                  @Valid @ModelAttribute Motorista motorista,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("motorista/form");
        }

        motorista.setId(id);
        motoristaRepository.save(motorista);

        return new ModelAndView("redirect:/motoristas");
    }

    // ✅ DELETAR (simples → só redirect)
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        motoristaRepository.deleteById(id);
        return "redirect:/motoristas";
    }
}