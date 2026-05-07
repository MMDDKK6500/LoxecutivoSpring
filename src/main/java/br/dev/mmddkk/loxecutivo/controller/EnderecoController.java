package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.Endereco;
import br.dev.mmddkk.loxecutivo.repository.EnderecoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    // ✅ LISTAR
    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("endereco/lista");
        mv.addObject("enderecos", enderecoRepository.findAll());
        return mv;
    }

    // ✅ FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("endereco/form");
        mv.addObject("endereco", new Endereco());
        return mv;
    }

    // ✅ SALVAR NOVO
    @PostMapping
    public ModelAndView salvar(@Valid @ModelAttribute Endereco endereco,
                               BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("endereco/form");
        }

        enderecoRepository.save(endereco);
        return new ModelAndView("redirect:/enderecos");
    }

    // ✅ EDITAR
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);

        if (enderecoOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("endereco/form");
            mv.addObject("endereco", enderecoOpt.get());
            return mv;
        }

        return new ModelAndView("redirect:/enderecos");
    }

    // ✅ ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Integer id,
                                  @Valid @ModelAttribute Endereco endereco,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("endereco/form");
        }

        endereco.setId(id);
        enderecoRepository.save(endereco);

        return new ModelAndView("redirect:/enderecos");
    }

    // ✅ DELETAR (simples → só redirect)
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        enderecoRepository.deleteById(id);
        return "redirect:/enderecos";
    }
}