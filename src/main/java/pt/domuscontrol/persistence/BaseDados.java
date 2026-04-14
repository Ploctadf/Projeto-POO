package pt.domuscontrol.persistence;

import pt.domuscontrol.model.common.Relogio;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.model.user.RegistoInteracao;
import pt.domuscontrol.model.user.Utilizador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseDados implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Utilizador> utilizadores;
    private final List<Casa> casas;
    private final List<RegistoInteracao> interacoes;
    private final Relogio relogio;

    // Contadores de IDs persistidos
    private int proximoIdUtilizador;
    private int proximoIdCasa;
    private int proximoIdDivisao;
    private int proximoIdDispositivo;

    public BaseDados() {
        this.utilizadores = new ArrayList<>();
        this.casas = new ArrayList<>();
        this.interacoes = new ArrayList<>();
        this.relogio = new Relogio();
        this.proximoIdUtilizador = 1;
        this.proximoIdCasa = 1;
        this.proximoIdDivisao = 1;
        this.proximoIdDispositivo = 1;
    }

    // --- Geração de IDs ---
    public String gerarIdUtilizador() { return String.format("U%03d", proximoIdUtilizador++); }
    public String gerarIdCasa()       { return String.format("C%03d", proximoIdCasa++); }
    public String gerarIdDivisao()    { return String.format("DIV%03d", proximoIdDivisao++); }
    public String gerarIdDispositivo(){ return String.format("D%03d", proximoIdDispositivo++); }

    // --- Utilizadores ---
    public void adicionarUtilizador(Utilizador u) { utilizadores.add(u); }
    public boolean removerUtilizador(Utilizador u) { return utilizadores.remove(u); }
    public List<Utilizador> getUtilizadores() { return Collections.unmodifiableList(utilizadores); }

    // --- Casas ---
    public void adicionarCasa(Casa c) { casas.add(c); }
    public boolean removerCasa(Casa c) { return casas.remove(c); }
    public List<Casa> getCasas() { return Collections.unmodifiableList(casas); }

    // --- Interações ---
    public void adicionarInteracao(RegistoInteracao r) { interacoes.add(r); }
    public List<RegistoInteracao> getInteracoes() { return Collections.unmodifiableList(interacoes); }

    // --- Relógio ---
    public Relogio getRelogio() { return relogio; }
}
