package pt.domuscontrol.service;

import pt.domuscontrol.exception.CasaNaoEncontradaException;
import pt.domuscontrol.exception.DivisaoNaoEncontradaException;
import pt.domuscontrol.exception.DispositivoNaoEncontradoException;
import pt.domuscontrol.exception.UtilizadorNaoEncontradoException;
import pt.domuscontrol.model.device.Dispositivo;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.model.house.Divisao;
import pt.domuscontrol.model.house.TipoDivisao;
import pt.domuscontrol.model.user.Utilizador;
import pt.domuscontrol.persistence.BaseDados;

import java.util.List;

public class CasaService {

    private final BaseDados bd;

    public CasaService(BaseDados bd) {
        this.bd = bd;
    }

    // ==================== CASAS ====================

    public Casa criarCasa(String nome, String morada, String idProprietario)
            throws UtilizadorNaoEncontradoException {
        Utilizador prop = encontrarUtilizador(idProprietario);
        Casa casa = new Casa(bd.gerarIdCasa(), nome, morada);
        casa.adicionarProprietario(prop);
        bd.adicionarCasa(casa);
        return casa;
    }

    public void removerCasa(String idCasa) throws CasaNaoEncontradaException {
        Casa casa = encontrarCasa(idCasa);
        bd.removerCasa(casa);
    }

    public Casa getCasaPorId(String idCasa) throws CasaNaoEncontradaException {
        return encontrarCasa(idCasa);
    }

    public List<Casa> listarCasas() {
        return bd.getCasas();
    }

    public List<Casa> listarCasasDeUtilizador(String idUtilizador) {
        return bd.getCasas().stream()
                .filter(c -> c.getProprietarios().stream().anyMatch(u -> u.getId().equals(idUtilizador))
                          || c.getUsufrutuarios().stream().anyMatch(u -> u.getId().equals(idUtilizador)))
                .toList();
    }

    public void associarUtilizador(String idCasa, String idUtilizador, boolean comoProprietario)
            throws CasaNaoEncontradaException, UtilizadorNaoEncontradoException {
        Casa casa = encontrarCasa(idCasa);
        Utilizador u = encontrarUtilizador(idUtilizador);
        if (comoProprietario) casa.adicionarProprietario(u);
        else casa.adicionarUsufrutuario(u);
    }

    public void removerUtilizadorDeCasa(String idCasa, String idUtilizador)
            throws CasaNaoEncontradaException {
        Casa casa = encontrarCasa(idCasa);
        if (!casa.removerUtilizador(idUtilizador))
            throw new IllegalArgumentException("Utilizador " + idUtilizador + " não pertence à casa");
    }

    // ==================== DIVISÕES ====================

    public Divisao criarDivisao(String idCasa, String nome, TipoDivisao tipo)
            throws CasaNaoEncontradaException {
        Casa casa = encontrarCasa(idCasa);
        Divisao divisao = new Divisao(bd.gerarIdDivisao(), nome, tipo);
        casa.adicionarDivisao(divisao);
        return divisao;
    }

    public void removerDivisao(String idCasa, String idDivisao)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        Casa casa = encontrarCasa(idCasa);
        if (!casa.removerDivisao(idDivisao))
            throw new DivisaoNaoEncontradaException(idDivisao);
    }

    public Divisao getDivisaoPorId(String idCasa, String idDivisao)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        return encontrarDivisao(idCasa, idDivisao);
    }

    // ==================== DISPOSITIVOS ====================

    public void adicionarDispositivo(String idCasa, String idDivisao, Dispositivo d)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        Divisao div = encontrarDivisao(idCasa, idDivisao);
        div.adicionarDispositivo(d);
    }

    public Dispositivo criarEAdicionarDispositivo(String idCasa, String idDivisao, Dispositivo d)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        adicionarDispositivo(idCasa, idDivisao, d);
        return d;
    }

    public void removerDispositivo(String idCasa, String idDivisao, String idDispositivo)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Divisao div = encontrarDivisao(idCasa, idDivisao);
        if (!div.removerDispositivo(idDispositivo))
            throw new DispositivoNaoEncontradoException(idDispositivo);
    }

    // ==================== UTILIZADORES ====================

    public Utilizador criarUtilizador(String nome, String email, String password) {
        Utilizador u = new Utilizador(bd.gerarIdUtilizador(), nome, email, password);
        bd.adicionarUtilizador(u);
        return u;
    }

    public void removerUtilizador(String idUtilizador) throws UtilizadorNaoEncontradoException {
        Utilizador u = encontrarUtilizador(idUtilizador);
        bd.removerUtilizador(u);
    }

    public List<Utilizador> listarUtilizadores() {
        return bd.getUtilizadores();
    }

    public void editarUtilizador(String idUtilizador, String novoNome, String novoEmail)
            throws UtilizadorNaoEncontradoException {
        Utilizador u = encontrarUtilizador(idUtilizador);
        if (novoNome != null && !novoNome.isBlank()) u.setNome(novoNome);
        if (novoEmail != null && !novoEmail.isBlank()) u.setEmail(novoEmail);
    }

    public void editarCasa(String idCasa, String novoNome, String novaMorada)
            throws CasaNaoEncontradaException {
        Casa casa = encontrarCasa(idCasa);
        if (novoNome != null && !novoNome.isBlank()) casa.setNome(novoNome);
        if (novaMorada != null && !novaMorada.isBlank()) casa.setMorada(novaMorada);
    }

    public void editarDivisao(String idCasa, String idDivisao, String novoNome, TipoDivisao novoTipo)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        Divisao div = encontrarDivisao(idCasa, idDivisao);
        if (novoNome != null && !novoNome.isBlank()) div.setNome(novoNome);
        if (novoTipo != null) div.setTipo(novoTipo);
    }

    public void editarDispositivo(String idCasa, String idDivisao, String idDispositivo,
                                  String novaMarca, String novoModelo, double novoConsumo)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Divisao div = encontrarDivisao(idCasa, idDivisao);
        Dispositivo d = div.getDispositivos().stream()
                .filter(x -> x.getId().equals(idDispositivo))
                .findFirst()
                .orElseThrow(() -> new DispositivoNaoEncontradoException(idDispositivo));
        if (novaMarca != null && !novaMarca.isBlank()) d.setMarca(novaMarca);
        if (novoModelo != null && !novoModelo.isBlank()) d.setModelo(novoModelo);
        if (novoConsumo >= 0) d.setConsumoPorHora(novoConsumo);
    }

    public String gerarIdDispositivo() {
        return bd.gerarIdDispositivo();
    }

    // ==================== Métodos auxiliares (package-private para services) ====================

    Casa encontrarCasa(String idCasa) throws CasaNaoEncontradaException {
        return bd.getCasas().stream()
                .filter(c -> c.getId().equals(idCasa))
                .findFirst()
                .orElseThrow(() -> new CasaNaoEncontradaException(idCasa));
    }

    Divisao encontrarDivisao(String idCasa, String idDivisao)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException {
        Casa casa = encontrarCasa(idCasa);
        return casa.getDivisoes().stream()
                .filter(d -> d.getId().equals(idDivisao))
                .findFirst()
                .orElseThrow(() -> new DivisaoNaoEncontradaException(idDivisao));
    }

    Utilizador encontrarUtilizador(String idUtilizador) throws UtilizadorNaoEncontradoException {
        return bd.getUtilizadores().stream()
                .filter(u -> u.getId().equals(idUtilizador))
                .findFirst()
                .orElseThrow(() -> new UtilizadorNaoEncontradoException(idUtilizador));
    }
}
