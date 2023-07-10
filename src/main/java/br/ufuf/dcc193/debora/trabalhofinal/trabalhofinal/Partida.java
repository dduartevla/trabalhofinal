package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Campo não pode estar em branco.")
    private String partidaId;
    @OneToMany(mappedBy = "partida")
    private List<Transacao> transacoes;
    @OneToMany(mappedBy = "partida")
    private List<Conta> contas;

    public Partida(Long id, String partidaId, List<Transacao> transacoes, List<Conta> contas) {
        this.id = id;
        this.partidaId = partidaId;
        this.transacoes = transacoes;
        this.contas = contas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPartidaId(String partidaId) {
        this.partidaId = partidaId;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }

    public Partida() {
        this(null, null, new ArrayList<>(), new ArrayList<>());
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

}
