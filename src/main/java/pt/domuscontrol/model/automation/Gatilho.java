package pt.domuscontrol.model.automation;

import pt.domuscontrol.persistence.BaseDados;

import java.io.Serializable;

/**
 * Interface que representa uma condição de activação de uma automação.
 * Implementada por: GatilhoSensor, GatilhoHorario, GatilhoEstado, etc.
 */
public interface Gatilho extends Serializable {
    boolean avaliar(BaseDados bd);
    String descricao();
}
