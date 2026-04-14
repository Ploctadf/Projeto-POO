package pt.domuscontrol.model.house;

import pt.domuscontrol.model.device.Dispositivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Divisao implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private TipoDivisao tipo;
    private final List<Dispositivo> dispositivos;

    public Divisao(String id, String nome, TipoDivisao tipo) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser vazio");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        if (tipo == null) throw new IllegalArgumentException("Tipo não pode ser nulo");
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.dispositivos = new ArrayList<>();
    }

    public void adicionarDispositivo(Dispositivo d) {
        if (d == null) throw new IllegalArgumentException("Dispositivo não pode ser nulo");
        if (dispositivos.stream().anyMatch(x -> x.getId().equals(d.getId())))
            throw new IllegalArgumentException("Dispositivo com id " + d.getId() + " já existe nesta divisão");
        dispositivos.add(d);
    }

    public boolean removerDispositivo(String idDispositivo) {
        return dispositivos.removeIf(d -> d.getId().equals(idDispositivo));
    }

    public List<Dispositivo> getDispositivos() {
        return Collections.unmodifiableList(dispositivos);
    }

    public int getNumDispositivos() { return dispositivos.size(); }

    public double getConsumoTotal() {
        return dispositivos.stream().mapToDouble(Dispositivo::getConsumoTotal).sum();
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public TipoDivisao getTipo() { return tipo; }

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setTipo(TipoDivisao tipo) {
        if (tipo == null) throw new IllegalArgumentException("Tipo não pode ser nulo");
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %d dispositivo(s)", id, nome, tipo, dispositivos.size());
    }
}
