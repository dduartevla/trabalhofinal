package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Campo não pode estar em branco.")
    private String nomeConta;
    @ManyToOne
    private Partida partida;
    @OneToMany(mappedBy = "contaEnvia")
    private List<Transacao> transacoes;
    private Double saldo;

    public Conta() {
        this(null, null, null, new ArrayList<>(), 0.00);
    }

    public Conta(Long id, @NotBlank(message = "Campo não pode estar em branco.") String nomeConta, Partida partida,
            List<Transacao> transacoes, Double saldo) {
        this.id = id;
        this.nomeConta = nomeConta;
        this.partida = partida;
        this.transacoes = transacoes;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeConta() {
        return nomeConta;
    }

    public void setNomeConta(String nomeConta) {
        this.nomeConta = nomeConta;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public List<Transacao> getTrasacoes() {
        return transacoes;
    }

    public void setTrasacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

}
