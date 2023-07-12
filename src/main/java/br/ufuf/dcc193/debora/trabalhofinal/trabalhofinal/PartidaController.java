package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

import jakarta.validation.Valid;

@Controller
public class PartidaController {

    Partida partida;

    @Autowired
    PartidaRepository partidaRep;

    @GetMapping({ "", "/", "/index.html" })
    public String index() {
        return "/index.html";
    }

    @GetMapping("/criaNovaPartida.html")
    public ModelAndView criaPartida() {
        partida = new Partida();
        ModelAndView mv = new ModelAndView();
        mv.addObject("partida", partida);
        mv.setViewName("criaNovaPartida");
        return mv;
    }

    @PostMapping("/criaNovaPartida.html")
    public ModelAndView criaPartida(@Valid Partida partida, BindingResult binding) {
        ModelAndView mv = new ModelAndView();
        if (binding.hasErrors()) {
            mv.setViewName("criaNovaPartida.html");
            mv.addObject("partida", partida);
            return mv;
        }
        mv.setViewName("redirect:partidaEmProgresso.html");
        partidaRep.save(partida);
        mv.addObject("partida", partida);
        return mv;
    }

    @GetMapping("/escolherPartida.html")
    public ModelAndView escolherPartida() {
        List<Partida> listaDePartidas = partidaRep.findAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("escolherPartida.html");
        mv.addObject("listaDePartidas", listaDePartidas);
        return mv;
    }

    @GetMapping("/partidaEmProgresso")
    public ModelAndView criaPartidaGet() {
        partida = new Partida();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        return mv;
    }

    @PostMapping("/partidaEmProgresso")
    public ModelAndView criaPartidaPost(@Valid Partida partida, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("partida", partida);
            return mv;
        }

        ModelAndView mv = new ModelAndView();
        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        return mv;
    }

    @PostMapping("/criaNovaConta")
    public ModelAndView criaConta(@Valid Partida partida, BindingResult bindingResult) {

        ModelAndView mv = new ModelAndView();

        mv.setViewName("criaNovaConta.html");
        return mv;
    }
}
