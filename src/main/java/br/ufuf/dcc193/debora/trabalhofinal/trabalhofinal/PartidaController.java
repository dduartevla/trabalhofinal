package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
public class PartidaController {

    Partida partida;

    @GetMapping({ "", "/", "/index.html" })
    public String index() {
        return "/index.html";
    }

    @GetMapping("/criaNovaPartida.html")
    public ModelAndView criaPartida() {
        partida = new Partida();
        ModelAndView mv = new ModelAndView("criaNovaPartida.html");
        mv.addObject("partida", partida);
        return mv;
    }

    @PostMapping("/partidaEmProgresso.html")
    public ModelAndView criaPartidaPost(@Valid Partida partida, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("criaNovaPartida.html");
            mv.addObject("partida", partida);
            return mv;
        }

        // Lógica para processar a criação da partida

        ModelAndView mv = new ModelAndView("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        return mv;
    }

}
