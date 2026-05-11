package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @RequestMapping("/")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("endereco", new Endereco());
        mv.addObject("motorista", new Motorista());
        mv.addObject("passageiro", new Passageiro());
        mv.addObject("veiculo", new Veiculo());
        mv.addObject("viagens", new Viagens());
        return mv;
    }

}
