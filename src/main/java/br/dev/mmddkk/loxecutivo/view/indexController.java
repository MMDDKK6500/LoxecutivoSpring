package br.dev.mmddkk.loxecutivo.view;

import br.dev.mmddkk.loxecutivo.model.Endereco;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class indexController {

    @RequestMapping("/")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("endereco", new Endereco());
        return mv;
    }

}
