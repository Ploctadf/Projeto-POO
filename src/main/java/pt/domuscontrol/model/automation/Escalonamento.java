package pt.domuscontrol.model.automation;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import pt.domuscontrol.persistence.BaseDados;

/**
 * Um escalonamento dispara uma Acao todos os dias a uma hora específica.
 *
 * Exemplos:
 *   - Todos os dias às 07:30 → abrir cortinas
 *   - Todos os dias às 23:00 → desligar todas as luzes da sala
 *
 * O campo `ultimaExecucao` garante que o escalonamento só dispara uma vez por dia,
 * mesmo que o método verificarEExecutar() seja chamado várias vezes no mesmo minuto.
 */
public class Escalonamento implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String nome;
    private final String casaId;
    private final LocalTime horario;
    private final Acao acao;
    private boolean ativo;
    private LocalDate ultimaExecucao; // null = nunca executou

    public Escalonamento(String id, String nome, String casaId, int hora, int minuto, Acao acao) {
        if (id == null || id.isBlank())    throw new IllegalArgumentException("ID não pode ser vazio");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        if (casaId == null)                throw new IllegalArgumentException("CasaId não pode ser nulo");
        if (acao == null)                  throw new IllegalArgumentException("Ação não pode ser nula");
        this.id = id;
        this.nome = nome;
        this.casaId = casaId;
        this.horario = LocalTime.of(hora, minuto);
        this.acao = acao;
        this.ativo = true;
        this.ultimaExecucao = null;
    }

    /**
     * Verifica se é a hora de executar e, se for, executa a ação.
     * @return true se a ação foi executada agora
     */
    public boolean verificarEExecutar(BaseDados bd) {
        if (!ativo) return false;

        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        LocalTime horaAtual = agora.toLocalTime();
        LocalDate diaAtual  = agora.toLocalDate();

        boolean horaCorreta = horaAtual.getHour()   == horario.getHour()
                           && horaAtual.getMinute()  == horario.getMinute();
        boolean naoExecutouHoje = ultimaExecucao == null || !ultimaExecucao.equals(diaAtual);

        if (horaCorreta && naoExecutouHoje) {
            acao.executar(bd);
            ultimaExecucao = diaAtual;
            return true;
        }
        return false;
    }

    public String getId()     { return id; }
    public String getNome()   { return nome; }
    public String getCasaId() { return casaId; }
    public LocalTime getHorario() { return horario; }
    public boolean isAtivo()  { return ativo; }

    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public String descricao() {
        return String.format("[%s] %s | %02d:%02d → %s | %s",
                id, nome, horario.getHour(), horario.getMinute(),
                acao.descricao(), ativo ? "ATIVO" : "INATIVO");
    }

    @Override
    public String toString() { return descricao(); }
}