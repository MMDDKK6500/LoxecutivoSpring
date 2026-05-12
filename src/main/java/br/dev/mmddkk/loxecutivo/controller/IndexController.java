package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.*;
import br.dev.mmddkk.loxecutivo.repository.EnderecoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    private final EnderecoRepository enderecoRepository;

    public IndexController(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @RequestMapping("/")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("endereco", new Endereco());
        mv.addObject("motorista", new Motorista());
        mv.addObject("passageiro", new Passageiro());
        mv.addObject("veiculo", new Veiculo());
        mv.addObject("viagens", new Viagens());
        mv.addObject("evento", enderecoRepository.findById(0)); // ?????
        return mv;
    }

}
