package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

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
    @NotBlank(message = "Este campo é obrigatório.")
    @PositiveOrZero(message = "valor deve ser maior ou igual a zero.")
    private Double saldoBanco;

    public Partida(Long id, String partidaId, List<Transacao> transacoes, List<Conta> contas, Double saldoBanco) {
        this.id = id;
        this.partidaId = partidaId;
        this.transacoes = transacoes;
        this.contas = contas;
        this.saldoBanco = saldoBanco;
    }

    public Partida() {
        this(null, null, new ArrayList<Transacao>(), new ArrayList<Conta>(), null);
        setPartidaId();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPartidaId() {
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

    public void setPartidaId(String partidaId) {
        this.partidaId = partidaId;
    }

    public Double getSaldoBanco() {
        return saldoBanco;
    }

    public void setSaldoBanco(Double saldoBanco) {
        this.saldoBanco = saldoBanco;
    }

}
