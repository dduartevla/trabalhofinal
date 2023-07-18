package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

@Controller
@Transactional
public class PartidaController {

    public String getUltimoIdPartida(List<Partida> listaDePartidas) {

        Partida ultimaPartida = listaDePartidas.get(listaDePartidas.size() - 1);
        String ultimoIdPartida = ultimaPartida.getPartidaId();
        return ultimoIdPartida;
    }

    @Autowired
    PartidaRepository partidaRep;

    @Autowired
    ContaRepository repConta;

    @Autowired
    TransacaoRepository repTransacao;
    Partida partida;
    List<Conta> contas;

    @GetMapping({ "", "/", "/index.html" })
    public String index() {
        return "/index.html";
    }

    @GetMapping("/criaNovaPartida.html")
    public ModelAndView criaPartida() {
        partida = new Partida();
        Conta contaBanco = new Conta();
        contaBanco.setNomeConta("Banco");
        contaBanco.setSaldo(partida.getSaldoBanco());
        if (!repConta.existsByNomeConta("Banco"))
            repConta.save(contaBanco);
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

    @Transactional
    @GetMapping("/escolherPartida{i}")
    public ModelAndView escolherPartida() {
        List<Partida> listaDePartidas = partidaRep.findAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("escolherPartida.html");
        mv.addObject("listaDePartidas", listaDePartidas);
        return mv;
    }

    @Transactional
    @PostMapping("/escolheuPartida")
    public ModelAndView escolheuPartida(@RequestParam("partidaId") String partidaId) {
        System.out.println("ENTROUUUUUU>>>>>>>>>>>>");
        this.partida = partidaRep.findById(partidaId);
        System.out.println("OLHA AQUI->->->->->: " + partidaId);
        return criaConta(partida);
    }

    @GetMapping("/partidaEmProgresso")
    public ModelAndView criaPartidaGet() {
        List<Partida> listaDePartidas = partidaRep.findAll();
        String idUltimaPartidaCadastrada = getUltimoIdPartida(listaDePartidas);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("idUltimaPartidaCadastrada", idUltimaPartidaCadastrada);
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
        System.out.println("partida salgada" + this.partida.getSaldoBanco());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("criaNovaConta.html");
        mv.addObject("partida", partida);
        return mv;
    }

    @Transactional
    @PostMapping("/criaNovaConta")
    public ModelAndView criaConta(Partida partida, BindingResult bindingResult, @RequestParam String nomeConta,
            @RequestParam String senhaConta) {

        System.out.println("PARTIDA SALGADA" + this.partida.getSaldoBanco());
        partida = this.partida;

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

        this.contas = partida.getContas();

        // verifica se já existe uma conta com esse nome
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNomeConta().equals(nomeConta)) {
                System.out.println("achou uma conta: " + nomeConta);
                if (contas.get(i).getSenhaConta().equals(senhaConta)) {
                    // a conta já existe e a senha informada está correta
                    System.out.println("achou uma conta e senha confere: " + nomeConta);
                    partida.setContaQueJoga(contas.get(i));
                    partida.setNomeConta(nomeConta);
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
        partidaRep.save(partida);

        Conta novaConta = new Conta();
        novaConta.setNomeConta(nomeConta.trim());
        novaConta.setSenhaConta(senhaConta);
        novaConta.setSaldo(25000.00);
        partida.setContaQueJoga(novaConta);
        this.partida.setContaQueJoga(novaConta);
        partida.getContas().add(novaConta);
        partida.setNomeConta(nomeConta);
        novaConta.setPartida(partida);
        partidaRep.save(partida);
        if (!repConta.existsByNomeConta(nomeConta.trim()))
            repConta.save(novaConta);

        for (int i = 0; i < partida.getContas().size(); i++) {
            System.out.println(partida.getContas().get(i).getNomeConta());
        }

        List<Conta> listaDeContas = repConta.findAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        mv.addObject("listaDeContas", listaDeContas);
        return mv;
    }

    @Transactional
    @PostMapping("/transacao")
    public ModelAndView trasacao(Partida partida, BindingResult bindingResult, String contaDestino, String valor) {
        ModelAndView mv = new ModelAndView();

        if (bindingResult.hasErrors()) {
            ModelAndView mv1 = new ModelAndView();
            mv1.setViewName("partidaEmProgresso.html");
            mv1.addObject("partida", partida);
            return mv1;
        }

        if (contaDestino == null || contaDestino.trim().isEmpty()) {
            bindingResult.rejectValue("contaDestno", "NotEmpty", "A conta de destino é obrigatória");
            ModelAndView mv2 = new ModelAndView("partidaEmProgresso.html");
            mv2.addObject("partida", partida);
            return mv2;
        }

        if (valor == null || valor.trim().isEmpty()) {
            bindingResult.rejectValue("senhaConta", "NotEmpty", "A senha é obrigatória");
            ModelAndView mv3 = new ModelAndView("partidaEmProgresso.html.html");
            mv3.addObject("partida", partida);
            return mv3;
        }

        // cria nova Transação
        Transacao novaTransacao = new Transacao();

        // define o valor da transação
        Double dvalor = Double.parseDouble(valor.trim());
        novaTransacao.setValor(dvalor);

        partida = this.partida;

        // define a conta que está pagando
        novaTransacao.setContaEnvia(repConta.findBynomeConta(partida.getContaQueJoga().getNomeConta()));
        // atualiza saldo da conta que paga
        partida.getContaQueJoga().setSaldo(partida.getContaQueJoga().getSaldo() - dvalor);

        // define a conta que recebe
        novaTransacao.setContaRecebe(repConta.findBynomeConta(contaDestino.trim()));
        // atualiza o saldo da conta que recebe
        System.out.println("conta destino: " + contaDestino);

        for (int i = 0; i < partida.getContas().size(); i++) {
            System.out.println(partida.getContas().get(i).getNomeConta());
        }

        Conta conta = repConta.findBynomeConta(contaDestino.trim());
        // partida.findConta(contaDestino.trim());
        conta.setSaldo(conta.getSaldo() + dvalor);
        Double novoSaldo = conta.getSaldo();

        System.out.println("PartidaController 248");

        // define a data e hora
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        novaTransacao.setDateTime(dataHoraAtual);

        System.out.println("PartidaController 254");
        // adiciona transação na partida e nas contas (temporario);
        partida.getTransacoes().add(novaTransacao);
        repTransacao.save(novaTransacao);
        partida.getContaQueJoga().getTransacoes().add(novaTransacao);
        conta.getTransacoes().add(novaTransacao);
        LocalDateTime horarioTransacao = novaTransacao.getDateTime();
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = horarioTransacao.format(formatoData);

        System.out.println("PartidaController 260");
        partidaRep.save(partida);
        System.out.println("PartidaController 262");
        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        mv.addObject("novoSaldo", novoSaldo);
        mv.addObject("dataFormatada", dataFormatada);
        return mv;

    }

    @Transactional
    @PostMapping("/transacaoBanco")
    public ModelAndView transacaoBanco(@RequestParam String acao, Partida partida, BindingResult bindingResult,
            String valor) {
        ModelAndView mv = new ModelAndView();

        if (bindingResult.hasErrors()) {
            ModelAndView mv1 = new ModelAndView();
            mv1.setViewName("partidaEmProgresso.html");
            mv1.addObject("partida", partida);
            return mv1;
        }

        if (valor == null || valor.trim().isEmpty()) {
            bindingResult.rejectValue("senhaConta", "NotEmpty", "A senha é obrigatória");
            ModelAndView mv3 = new ModelAndView("partidaEmProgresso.html.html");
            mv3.addObject("partida", partida);
            return mv3;
        }

        // cria nova Transação
        Transacao novaTransacao = new Transacao();

        // define o valor da transação
        Double dvalor = Double.parseDouble(valor.trim());
        novaTransacao.setValor(dvalor);

        partida = this.partida;

        // define a data e hora
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        novaTransacao.setDateTime(dataHoraAtual);

        Conta contaBanco = repConta.findBynomeConta("Banco");

        if (acao.equals("receber")) {
            // define quem paga e quem recebe
            novaTransacao.setContaEnvia(repConta.findBynomeConta(partida.getContaQueJoga().getNomeConta()));
            novaTransacao.setContaRecebe(contaBanco);
            // atualiza saldo da conta que paga
            partida.getContaQueJoga().setSaldo(partida.getContaQueJoga().getSaldo() + dvalor);

            partida.setSaldoBanco(partida.getSaldoBanco() - dvalor);

            partida.getTransacoes().add(novaTransacao);
            repTransacao.save(novaTransacao);
            partida.getContaQueJoga().getTransacoes().add(novaTransacao);

        } else if (acao.equals("pagar")) {
            // define quem paga e quem recebe
            novaTransacao.setContaRecebe(repConta.findBynomeConta(partida.getContaQueJoga().getNomeConta()));
            novaTransacao.setContaEnvia(contaBanco);
            // atualiza saldo da conta que paga
            partida.getContaQueJoga().setSaldo(partida.getContaQueJoga().getSaldo() - dvalor);

            partida.setSaldoBanco(partida.getSaldoBanco() + dvalor);

            partida.getTransacoes().add(novaTransacao);
            repTransacao.save(novaTransacao);
            partida.getContaQueJoga().getTransacoes().add(novaTransacao);
        }

        System.out.println("Partida Controller 335");

        mv.setViewName("partidaEmProgresso.html");
        mv.addObject("partida", partida);
        return mv;
    }
}
