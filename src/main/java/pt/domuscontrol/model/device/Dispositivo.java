package pt.domuscontrol.model.device;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Dispositivo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String marca;
    private String modelo;
    private double consumoPorHora; // Wh
    private boolean ligado;
    private int numActivacoes;
    private long tempoLigadoSegundos;
    private LocalDateTime ultimaActivacao;

    protected Dispositivo(String id, String marca, String modelo, double consumoPorHora) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser vazio");
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("Marca não pode ser vazia");
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("Modelo não pode ser vazio");
        if (consumoPorHora < 0) throw new IllegalArgumentException("Consumo não pode ser negativo");
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoPorHora = consumoPorHora;
        this.ligado = false;
        this.numActivacoes = 0;
        this.tempoLigadoSegundos = 0;
        this.ultimaActivacao = null;
    }

    public void ligar(LocalDateTime agora) {
        if (!ligado) {
            ligado = true;
            numActivacoes++;
            ultimaActivacao = agora;
        }
    }

    public void desligar(LocalDateTime agora) {
        if (ligado) {
            ligado = false;
            if (ultimaActivacao != null) {
                tempoLigadoSegundos += Duration.between(ultimaActivacao, agora).getSeconds();
                ultimaActivacao = null;
            }
        }
    }

    public void toggle(LocalDateTime agora) {
        if (ligado) desligar(agora);
        else ligar(agora);
    }

    // Energia consumida total em Wh
    public double getConsumoTotal() {
        return (tempoLigadoSegundos / 3600.0) * consumoPorHora;
    }

    // Retorna descrição específica do tipo de dispositivo (polimorfismo)
    public abstract String descricao();

    // Getters
    public String getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getConsumoPorHora() { return consumoPorHora; }
    public boolean isLigado() { return ligado; }
    public int getNumActivacoes() { return numActivacoes; }
    public long getTempoLigadoSegundos() { return tempoLigadoSegundos; }

    // Setters com validação
    public void setMarca(String marca) {
        if (marca == null || marca.isBlank()) throw new IllegalArgumentException("Marca não pode ser vazia");
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.isBlank()) throw new IllegalArgumentException("Modelo não pode ser vazio");
        this.modelo = modelo;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        if (consumoPorHora < 0) throw new IllegalArgumentException("Consumo não pode ser negativo");
        this.consumoPorHora = consumoPorHora;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s - %s | Activações: %d | Consumo: %.1f Wh",
                id, marca, modelo,
                ligado ? "LIGADO" : "DESLIGADO",
                numActivacoes, getConsumoTotal());
    }
}
