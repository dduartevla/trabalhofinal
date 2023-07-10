package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PartidaController {

    @GetMapping({ "", "/", "/index.html" })
    public String index() {
        return "/index.html";
    }

    @GetMapping("/criaNovaPartida.html")
    public String criaPartida() {
        return "/criaNovaPartida.html";
    }

    // @PostMapping("/criaNovaPartida.html")
    // public String criaPartidaPost(){

    // }

}
