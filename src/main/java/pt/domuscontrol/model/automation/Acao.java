package pt.domuscontrol.model.automation;

import pt.domuscontrol.model.common.SistemaContext;

import java.io.Serializable;

/**
 * Interface que representa uma acção a executar num dispositivo.
 * Implementada por: AcaoLigar, AcaoDesligar, AcaoAjustarIntensidade, etc.
 */
public interface Acao extends Serializable {
    void executar(SistemaContext ctx);
    String descricao();
}
