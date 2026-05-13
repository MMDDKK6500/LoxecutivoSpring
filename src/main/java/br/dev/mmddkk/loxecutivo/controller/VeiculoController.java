package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.Veiculo;
import br.dev.mmddkk.loxecutivo.repository.VeiculoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    // ✅ LISTAR
    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("veiculo/lista");
        mv.addObject("veiculos", veiculoRepository.findAll());
        return mv;
    }

    // ✅ FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("veiculo/form");
        mv.addObject("veiculo", new Veiculo());
        return mv;
    }

    // ✅ SALVAR NOVO
    @PostMapping
    public ModelAndView salvar(@Valid @ModelAttribute Veiculo veiculo,
                               BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("veiculo/form");
        }

        veiculoRepository.save(veiculo);
        return new ModelAndView("redirect:/");
    }

    // ✅ EDITAR
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable String id) {
        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(id);

        if (veiculoOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("veiculo/form");
            mv.addObject("veiculo", veiculoOpt.get());
            return mv;
        }

        return new ModelAndView("redirect:/");
    }

    // ✅ ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable String id,
                                  @Valid @ModelAttribute Veiculo veiculo,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("veiculo/form");
        }

        veiculo.setIdPlaca(id);
        veiculoRepository.save(veiculo);

        return new ModelAndView("redirect:/");
    }

    // ✅ DELETAR (simples → só redirect)
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable String id) {
        veiculoRepository.deleteById(id);
        return "redirect:/";
    }
}