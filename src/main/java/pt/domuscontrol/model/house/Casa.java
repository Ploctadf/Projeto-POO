package pt.domuscontrol.model.house;

import pt.domuscontrol.model.device.Dispositivo;
import pt.domuscontrol.model.user.Utilizador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Casa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String nome;
    private String morada;
    private final List<Divisao> divisoes;
    private final List<Utilizador> proprietarios;
    private final List<Utilizador> usufrutuarios;

    public Casa(String id, String nome, String morada) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID não pode ser vazio");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        if (morada == null || morada.isBlank()) throw new IllegalArgumentException("Morada não pode ser vazia");
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.divisoes = new ArrayList<>();
        this.proprietarios = new ArrayList<>();
        this.usufrutuarios = new ArrayList<>();
    }

    // --- Divisões ---
    public void adicionarDivisao(Divisao d) {
        if (d == null) throw new IllegalArgumentException("Divisão não pode ser nula");
        if (divisoes.stream().anyMatch(x -> x.getId().equals(d.getId())))
            throw new IllegalArgumentException("Divisão com id " + d.getId() + " já existe");
        divisoes.add(d);
    }

    public boolean removerDivisao(String idDivisao) {
        return divisoes.removeIf(d -> d.getId().equals(idDivisao));
    }

    public List<Divisao> getDivisoes() {
        return Collections.unmodifiableList(divisoes);
    }

    // --- Utilizadores ---
    public void adicionarProprietario(Utilizador u) {
        if (u == null) throw new IllegalArgumentException("Utilizador não pode ser nulo");
        if (proprietarios.stream().noneMatch(x -> x.getId().equals(u.getId())))
            proprietarios.add(u);
    }

    public void adicionarUsufrutuario(Utilizador u) {
        if (u == null) throw new IllegalArgumentException("Utilizador não pode ser nulo");
        if (usufrutuarios.stream().noneMatch(x -> x.getId().equals(u.getId())))
            usufrutuarios.add(u);
    }

    public boolean removerUtilizador(String idUtilizador) {
        boolean r1 = proprietarios.removeIf(u -> u.getId().equals(idUtilizador));
        boolean r2 = usufrutuarios.removeIf(u -> u.getId().equals(idUtilizador));
        return r1 || r2;
    }

    public boolean isProprietario(String idUtilizador) {
        return proprietarios.stream().anyMatch(u -> u.getId().equals(idUtilizador));
    }

    public List<Utilizador> getProprietarios() { return Collections.unmodifiableList(proprietarios); }
    public List<Utilizador> getUsufrutuarios() { return Collections.unmodifiableList(usufrutuarios); }

    // --- Consumo total ---
    public double getConsumoTotal() {
        return divisoes.stream().mapToDouble(Divisao::getConsumoTotal).sum();
    }

    // --- Lista todos os dispositivos da casa ---
    public List<Dispositivo> getTodosDispositivos() {
        List<Dispositivo> todos = new ArrayList<>();
        for (Divisao d : divisoes) todos.addAll(d.getDispositivos());
        return Collections.unmodifiableList(todos);
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getMorada() { return morada; }

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setMorada(String morada) {
        if (morada == null || morada.isBlank()) throw new IllegalArgumentException("Morada não pode ser vazia");
        this.morada = morada;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | %d divisão(ões)", id, nome, morada, divisoes.size());
    }
}
