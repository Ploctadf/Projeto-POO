package pt.domuscontrol.service;

import pt.domuscontrol.model.automation.Automacao;
import pt.domuscontrol.model.automation.Escalonamento;
import pt.domuscontrol.persistence.BaseDados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service que gere automações e escalonamentos.
 * As listas são mantidas em memória (e persistidas via BaseDados).
 *
 * Nota: as listas de automações e escalonamentos estão dentro do BaseDados
 * para que sejam serializadas quando o estado é guardado em ficheiro.
 */
public class AutomacaoService {

    private final BaseDados bd;

    public AutomacaoService(BaseDados bd) {
        this.bd = bd;
    }

    // ==================== AUTOMAÇÕES ====================

    public void adicionarAutomacao(Automacao a) {
        bd.adicionarAutomacao(a);
    }

    public boolean removerAutomacao(String id) {
        return bd.removerAutomacao(id);
    }

    public List<Automacao> listarAutomacoes() {
        return bd.getAutomacoes();
    }

    public List<Automacao> listarAutomacoesPorCasa(String casaId) {
        return bd.getAutomacoes().stream()
                .filter(a -> a.getCasaId().equals(casaId))
                .toList();
    }

    /**
     * Avalia todas as automações ativas e executa as que disparam.
     * @return lista com os nomes das automações que foram executadas
     */
    public List<String> avaliarTodasAutomacoes() {
        List<String> executadas = new ArrayList<>();
        for (Automacao a : bd.getAutomacoes()) {
            if (a.avaliarEExecutar(bd)) {
                executadas.add(a.getNome());
            }
        }
        return Collections.unmodifiableList(executadas);
    }

    // ==================== ESCALONAMENTOS ====================

    public void adicionarEscalonamento(Escalonamento e) {
        bd.adicionarEscalonamento(e);
    }

    public boolean removerEscalonamento(String id) {
        return bd.removerEscalonamento(id);
    }

    public List<Escalonamento> listarEscalonamentos() {
        return bd.getEscalonamentos();
    }

    public List<Escalonamento> listarEscalonamentosPorCasa(String casaId) {
        return bd.getEscalonamentos().stream()
                .filter(e -> e.getCasaId().equals(casaId))
                .toList();
    }

    /**
     * Verifica todos os escalonamentos e executa os que correspondem à hora atual.
     * Deve ser chamado sempre que o tempo simulado avança.
     * @return lista com os nomes dos escalonamentos que dispararam
     */
    public List<String> verificarEscalonamentos() {
        List<String> executados = new ArrayList<>();
        for (Escalonamento e : bd.getEscalonamentos()) {
            if (e.verificarEExecutar(bd)) {
                executados.add(e.getNome());
            }
        }
        return Collections.unmodifiableList(executados);
    }

    // ==================== Geração de IDs ====================

    public String gerarIdAutomacao() {
        return bd.gerarIdAutomacao();
    }

    public String gerarIdEscalonamento() {
        return bd.gerarIdEscalonamento();
    }
}