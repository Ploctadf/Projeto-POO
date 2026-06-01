package pt.domuscontrol.model.automation;

import pt.domuscontrol.model.common.SistemaContext;

import java.io.Serializable;

/**
 * Interface que representa uma condição de activação de uma automação.
 * Implementada por: GatilhoSensor, GatilhoHorario, GatilhoEstado, etc.
 */
public interface Gatilho extends Serializable {
    boolean avaliar(SistemaContext ctx);
    String descricao();
}
