package pt.domuscontrol.model.automation;
 
import pt.domuscontrol.model.common.SistemaContext;
 
import java.io.Serializable;
 
/**
 * Uma automação é composta por:
 *   - um Gatilho (condição)
 *   - uma Acao (o que fazer quando a condição é verdadeira)
 *
 * Quando avaliarEExecutar() é chamado, verifica o gatilho e, se disparar,
 * executa a ação e devolve true.
 */
public class Automacao implements Serializable {
    private static final long serialVersionUID = 1L;
 
    private final String id;
    private final String nome;
    private final String casaId;
    private final Gatilho gatilho;
    private final Acao acao;
    private boolean ativa;
 
    public Automacao(String id, String nome, String casaId, Gatilho gatilho, Acao acao) {
        if (id == null || id.isBlank())    throw new IllegalArgumentException("ID não pode ser vazio");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio");
        if (casaId == null)                throw new IllegalArgumentException("CasaId não pode ser nulo");
        if (gatilho == null)               throw new IllegalArgumentException("Gatilho não pode ser nulo");
        if (acao == null)                  throw new IllegalArgumentException("Ação não pode ser nula");
        this.id = id;
        this.nome = nome;
        this.casaId = casaId;
        this.gatilho = gatilho;
        this.acao = acao;
        this.ativa = true;
    }
 
    /**
     * Avalia o gatilho e, se disparar, executa a ação.
     * @return true se a ação foi executada
     */
    public boolean avaliarEExecutar(SistemaContext ctx) {
        if (!ativa) return false;
        if (gatilho.avaliar(ctx)) {
            acao.executar(ctx);
            return true;
        }
        return false;
    }
 
    public String getId()     { return id; }
    public String getNome()   { return nome; }
    public String getCasaId() { return casaId; }
    public boolean isAtiva()  { return ativa; }
 
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
 
    public String descricao() {
        return String.format("[%s] %s | SE %s → %s | %s",
                id, nome, gatilho.descricao(), acao.descricao(),
                ativa ? "ATIVA" : "INATIVA");
    }
 
    @Override
    public String toString() { return descricao(); }
}
