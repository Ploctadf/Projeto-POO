package pt.domuscontrol.model.automation;

import pt.domuscontrol.persistence.BaseDados;

import java.io.Serializable;

/**
 * Interface que representa uma acção a executar num dispositivo.
 * Implementada por: AcaoLigar, AcaoDesligar, AcaoAjustarIntensidade, etc.
 */
public interface Acao extends Serializable {
    void executar(BaseDados bd);
    String descricao();
}
