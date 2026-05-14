package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.Passageiro;
import br.dev.mmddkk.loxecutivo.model.Viagens;
import br.dev.mmddkk.loxecutivo.repository.PassageiroRepository;
import br.dev.mmddkk.loxecutivo.repository.ViagensRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/passageiros")
public class PassageiroController {

    @Autowired
    private PassageiroRepository passageiroRepository;
    @Autowired
    private ViagensRepository viagensRepository;

    // ✅ LISTAR
    @GetMapping
    public ModelAndView listar() {
        ModelAndView mv = new ModelAndView("passageiro/lista");
        mv.addObject("passageiros", passageiroRepository.findAll());
        return mv;
    }

    // ✅ FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/novo")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("passageiro/form");
        mv.addObject("passageiro", new Passageiro());
        mv.addObject("viagens", viagensRepository.findAll());
        return mv;
    }

    @PostMapping
    public ModelAndView salvar(@Valid @ModelAttribute Passageiro passageiro, BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("passageiro/form");
        }

        // 1. Salva o passageiro
        passageiroRepository.save(passageiro);

        // 2. Busca a viagem completa para ter acesso ao Evento
        // Substitua 'getId()' pelo nome correto do campo ID na sua classe Viagens (ex: getIdViagem)
        Viagens viagemCompleta = viagensRepository.findById(passageiro.getIdViagem().getId())
                .orElseThrow(() -> new RuntimeException("Viagem não encontrada"));

        // 3. Agora você tem acesso aloo ID do Evento com segurança
        int eventoId = viagemCompleta.getIdEvento().getId();
        int viagemId = viagemCompleta.getId();

        return new ModelAndView("redirect:/" + eventoId + "/" + viagemId);
    }

    // ✅ EDITAR
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Integer id) {
        Optional<Passageiro> passageiroOpt = passageiroRepository.findById(id);

        if (passageiroOpt.isPresent()) {
            ModelAndView mv = new ModelAndView("passageiro/form");
            mv.addObject("passageiro", passageiroOpt.get());
            return mv;
        }

        return new ModelAndView("redirect:/passageiros");
    }

    // ✅ ATUALIZAR
    @PostMapping("/atualizar/{id}")
    public ModelAndView atualizar(@PathVariable Integer id,
                                  @Valid @ModelAttribute Passageiro passageiro,
                                  BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("passageiro/form");
        }

        passageiro.setId(id);
        passageiroRepository.save(passageiro);

        return new ModelAndView("redirect:/passageiros");
    }

    // ✅ DELETAR (simples → só redirect)
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Integer id) {
        passageiroRepository.deleteById(id);
        return "redirect:/passageiros";
    }
}