package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.Viagens;
import br.dev.mmddkk.loxecutivo.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/viagens")
public class ViagensController {

    @Autowired
    private ViagensRepository viagensRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private MotoristaRepository motoristaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private EventoRepository eventoRepository;

    // ✅ LISTAR
    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("viagens/lista");
        mv.addObject("viagens", viagensRepository.findAll());
        return mv;
    }

    // ✅ FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("viagens/form");
        mv.addObject("viagem", new Viagens());
        mv.addObject("enderecos", enderecoRepository.findAll());
        mv.addObject("motoristas", motoristaRepository.findAll());
        mv.addObject("veiculos", veiculoRepository.findAll());
        mv.addObject("eventos", eventoRepository.findAll());
        return mv;
    }

    // ✅ SALVAR NOVO
    @PostMapping
    public ModelAndView salvar(@Valid @ModelAttribute Viagens viagens,
                               BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("viagens/form");
        }

        viagensRepository.save(viagens);
        return new ModelAndView("redirect:/viagens");
    }

    // ✅ EDITAR
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        Optional<Viagens> viagensOpt = viagensRepository.findById(id);

        if (viagensOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("viagens/form");
            mv.addObject("viagem", viagensOpt.get());
            return mv;
        }

        return new ModelAndView("redirect:/viagens");
    }

    // ✅ ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Integer id,
                                  @Valid @ModelAttribute Viagens viagens,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("viagens/form");
        }

        viagens.setId(id);
        viagensRepository.save(viagens);

        return new ModelAndView("redirect:/viagens");
    }

    // ✅ DELETAR (simples → só redirect)
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        viagensRepository.deleteById(id);
        return "redirect:/viagens";
    }
}