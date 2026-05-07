package br.dev.mmddkk.loxecutivo.controller;

import br.dev.mmddkk.loxecutivo.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Lista {

    @Autowired
    private EnderecoRepository er;

    @RequestMapping("/lista")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("lista");
        mv.addObject("enderecos", er.findAll());
        return mv;
    }

}
