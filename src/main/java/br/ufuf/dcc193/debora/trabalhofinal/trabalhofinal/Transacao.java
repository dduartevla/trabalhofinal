package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // @NotBlank(message = "Este campo é obrigatório.")
    private String gameId;
    @NotBlank(message = "A conta que envia não pode estar em branco.")
    private String contaEnvia;
    @NotBlank(message = "A conta que recebe não pode estar em branco.")
    private String contaRecebe;
    private LocalDateTime dateTime;
    // @NotNull(message = "Este campo é obrigatório.")
    @PositiveOrZero(message = "valor deve ser maior ou igual a zero.")
    private Double valor;

    @ManyToOne
    private Partida partida;

    public Transacao() {
        this(null, null, null, null, 0.0);
    }

    public Transacao(String gameId, String contaEnvia, String contaRecebe, LocalDateTime dateTime, Double valor) {
        this.gameId = gameId;
        this.contaEnvia = contaEnvia;
        this.contaRecebe = contaRecebe;
        this.dateTime = dateTime;
        this.valor = valor;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getContaEnvia() {
        return contaEnvia;
    }

    public void setContaEnvia(String contaEnvia) {
        this.contaEnvia = contaEnvia;
    }

    public String getContaRecebe() {
        return contaRecebe;
    }

    public void setContaRecebe(String contaRecebe) {
        this.contaRecebe = contaRecebe;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

}
