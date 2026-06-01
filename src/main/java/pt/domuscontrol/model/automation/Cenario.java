package pt.domuscontrol.model.automation;
import pt.domuscontrol.model.common.SistemaContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cenario implements Serializable {

    private final String nome;
    private final List<Acao> acoes;

    public Cenario(String nome) {
        this.nome = nome;
        this.acoes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarAcao(Acao acao) {
        acoes.add(acao);
    }

    public void executar(SistemaContext context) {
        for (Acao a : acoes) {
            a.executar(context);
        }
    }

    public List<Acao> getAcoes() {
        return acoes;
    }

    @Override
    public String toString() {
        return nome + " (" + acoes.size() + " ações)";
    }
}