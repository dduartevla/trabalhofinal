package br.ufuf.dcc193.debora.trabalhofinal.trabalhofinal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @OneToOne
    private Conta contaEnvia;
    @OneToOne
    private Conta contaRecebe;
    private LocalDateTime dateTime;
    // @NotNull(message = "Este campo é obrigatório.")
    @PositiveOrZero(message = "valor deve ser maior ou igual a zero.")
    private Double valor;

    @ManyToOne
    private Partida partida;

    public Transacao() {
        this(null, null, null, null, 0.0);
        
        this.dateTime = LocalDateTime.now();
        this.setDateTime(this.dateTime);
    }

    public Transacao(String gameId, Conta contaEnvia, Conta contaRecebe, LocalDateTime dateTime, Double valor) {
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

    public Conta getContaEnvia() {
        return contaEnvia;
    }

    public void setContaEnvia(Conta contaEnvia) {
        this.contaEnvia = contaEnvia;
    }

    public Conta getContaRecebe() {
        return contaRecebe;
    }

    public void setContaRecebe(Conta contaRecebe) {
        this.contaRecebe = contaRecebe;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
    public String getDataHoraTransacao(){
        
        LocalDateTime dataHoraTransacao = this.getDateTime();
        DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dataHoraTransacao.format(formatoDataHora);
        return dataFormatada;
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
