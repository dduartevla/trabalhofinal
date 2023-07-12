package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
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

    @GetMapping("/criaNovaConta")
    public ModelAndView criaConta(Partida partida) {

        System.out.println("partida salgada" + partida.getSaldoBanco());
        this.partida = partida;
        ModelAndView mv = new ModelAndView();
        mv.setViewName("criaNovaConta.html");
        mv.addObject("partida", partida);
        return mv;
    }

    @PostMapping("/criaNovaConta")
    public ModelAndView criaConta(Partida partida, BindingResult bindingResult, @RequestParam String nomeConta,
            @RequestParam String senhaConta) {

        System.out.println("PARTIDA SALGADA" + partida.getSaldoBanco());

        if (nomeConta == null || nomeConta.trim().isEmpty()) {
            bindingResult.rejectValue("nomeConta", "NotEmpty", "O nome da conta é obrigatório");
            ModelAndView mv = new ModelAndView("criaNovaPartida.html");
            mv.addObject("partida", partida);
            return mv;
        }

        if (senhaConta == null || senhaConta.trim().isEmpty()) {
            bindingResult.rejectValue("senhaConta", "NotEmpty", "A senha é obrigatória");
            ModelAndView mv = new ModelAndView("criaNovaPartida.html");
            mv.addObject("partida", partida);
            return mv;
        }

        List<Conta> contas = new ArrayList<Conta>();
        contas = partida.getContas();

        // verifica se já existe uma conta com esse nome
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNomeConta().equals(nomeConta)) {
                System.out.println("achou uma conta: " + nomeConta);
                if (contas.get(i).getSenhaConta().equals(senhaConta)) {
                    // a conta já existe e a senha informada está correta
                    System.out.println("achou uma conta e senha confere: " + nomeConta);
                    partida.setContaQueJoga(contas.get(i));
                    ModelAndView mv = new ModelAndView();
                    mv.setViewName("partidaEmProgresso.html");
                    mv.addObject("partida", partida);
                    return mv;
                }
                // a conta já existe e a senha informada está incorreta
                bindingResult.rejectValue("senhaConta", "NotEmpty", "A senha está incorreta");
                ModelAndView mv = new ModelAndView("criaNovaPartida.html");
                mv.addObject("partida", partida);
                return mv;
            }
        }

        Conta novaConta = new Conta();
        novaConta.setNomeConta(nomeConta);
        novaConta.setSenhaConta(senhaConta);
        novaConta.setSaldo(25000.00);
        novaConta.setPartida(partida);

        partida.setContaQueJoga(novaConta);
        partida.getContas().add(novaConta);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        return mv;
    }
}
