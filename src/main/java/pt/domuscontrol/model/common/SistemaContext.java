package pt.domuscontrol.model.common;

import pt.domuscontrol.model.house.Casa;

import java.util.List;

/**
 * Abstração que fornece ao model o acesso ao estado do sistema (casas e relógio)
 * sem depender da camada de persistência.
 */
public interface SistemaContext {
    List<Casa> getCasas();
    Relogio getRelogio();
}
