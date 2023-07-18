package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Campo não pode estar em branco.")
    private String partidaId;
    @OneToMany(mappedBy = "partida", fetch = FetchType.EAGER)
    private List<Transacao> transacoes;
    @OneToMany(mappedBy = "partida", fetch = FetchType.EAGER)
    private List<Conta> contas;
    @NotNull(message = "Este campo é obrigatório.")
    @PositiveOrZero(message = "valor deve ser maior ou igual a zero.")
    private Double saldoBanco;
    private boolean ePrivada;
    @NotBlank(message = "Partidas privadas devem ter uma senha.")
    private String senhaPartida;
    private String nomeConta;
    private String senhaConta;
    @OneToOne
    @Cascade(CascadeType.ALL)
    private Conta contaQueJoga;

    public Partida(Long id, @NotBlank(message = "Campo não pode estar em branco.") String partidaId,
            List<Transacao> transacoes, List<Conta> contas,
            @NotNull(message = "Este campo é obrigatório.") @PositiveOrZero(message = "valor deve ser maior ou igual a zero.") Double saldoBanco,
            boolean ePrivada, @NotBlank(message = "Partidas privadas devem ter uma senha.") String senhaPartida,
            Conta contaQueJoga) {
        this.id = id;
        this.partidaId = partidaId;
        if (transacoes == null) {
            this.transacoes = new ArrayList<Transacao>();
        } else {
            this.transacoes = transacoes;
        }
        this.contas = contas;
        this.saldoBanco = saldoBanco;
        this.ePrivada = ePrivada;
        this.senhaPartida = senhaPartida;
        this.contaQueJoga = contaQueJoga;
    }

    public Partida() {
        this(null, null, new ArrayList<Transacao>(), new ArrayList<Conta>(), null, false, null, null);
        setPartidaRandomId();
    }

    public Conta findConta(String conta) {
        Conta nConta = new Conta();

        for (int i = 0; i < this.contas.size(); i++) {
            if (this.contas.get(i).getNomeConta().equals(conta)) {
                System.out.println("achou a conta: " + conta);

                return this.contas.get(i);
            }
        }
        System.out.println("não achou a conta");
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isePrivada() {
        return ePrivada;
    }

    public void setePrivada(boolean ePrivada) {
        this.ePrivada = ePrivada;
    }

    public String getSenhaPartida() {
        return senhaPartida;
    }

    public void setSenhaPartida(String senhaPartida) {
        this.senhaPartida = senhaPartida;
    }

    public void setPartidaRandomId(String partidaId) {
        this.partidaId = partidaId;
    }

    public void setPartidaRandomId() {
        this.partidaId = generateRandomId();
    }

    public String generateRandomId() {
        Random random = new Random();
        byte[] buffer = new byte[3];
        random.nextBytes(buffer);
        return bytesToHex(buffer);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString().toUpperCase();
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public Long getId() {
        return id;
    }

    public String getPartidaId() {
        return partidaId;
    }

    public List<Transacao> getTransacoes() {
        // implementar uma ordenação aqui
        return transacoes;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public Double getSaldoBanco() {
        return saldoBanco;
    }

    public void setSaldoBanco(Double saldoBanco) {
        this.saldoBanco = saldoBanco;
    }

    public String getNomeConta() {
        return nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }

    public String getSenhaConta() {
        return senhaConta;
    }

    public void setSenhaConta(String senhaConta) {
        this.senhaConta = senhaConta;
    }

    public Conta getContaQueJoga() {
        return contaQueJoga;
    }

    public void setContaQueJoga(Conta contaQueJoga) {
        this.contaQueJoga = contaQueJoga;
    }

}
