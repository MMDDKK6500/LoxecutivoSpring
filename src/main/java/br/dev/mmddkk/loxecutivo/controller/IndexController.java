package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.*;
import br.dev.mmddkk.loxecutivo.repository.EnderecoRepository;
import br.dev.mmddkk.loxecutivo.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class IndexController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public IndexController(EnderecoRepository enderecoRepository, EventoRepository eventoRepository) {
        this.enderecoRepository = enderecoRepository;
        this.eventoRepository = eventoRepository;
    }

    @RequestMapping(value = {"/", "/{id}"})
    public ModelAndView form(@PathVariable Optional<Integer> id) {
        ModelAndView mv = new ModelAndView("index");

        //hack (fiquei 1 hora nisso)
        Evento evento;
        if (id.isPresent()) {
            evento = eventoRepository.findById(id.get()).orElse(new Evento());
        } else {
            evento = new Evento();
        }
        mv.addObject("enderecos", new Endereco());

        mv.addObject("evento", evento);
        return mv;
    }

}
