package pt.domuscontrol.service;

import pt.domuscontrol.model.automation.*;
import pt.domuscontrol.model.device.*;
import pt.domuscontrol.model.house.*;
import pt.domuscontrol.persistence.BaseDados;

import java.util.*;

public class CenarioService {

    private final Map<String, Cenario> cenarios;
    private final BaseDados bd;

    public CenarioService(BaseDados bd) {
        this.bd = bd;
        this.cenarios = new HashMap<>();

        criarCenariosBase();
    }

    public void criarCenariosBase() {
        cenarios.clear();

        Cenario sair = new Cenario("Sair de Casa");
        Cenario jantar = new Cenario("Jantar com Amigos");
        Cenario deitar = new Cenario("Deitar");
        Cenario acordar = new Cenario("Acordar");

        for (Casa casa : bd.getCasas()) {

            for (Divisao div : casa.getDivisoes()) {

                for (Dispositivo d : div.getDispositivos()) {

                    sair.adicionarAcao(
                            new AcaoDesligar(casa.getId(), div.getId(), d.getId())
                    );

                    if (d instanceof Lampada) {
                        jantar.adicionarAcao(
                                new AcaoLigar(casa.getId(), div.getId(), d.getId())
                        );
                    }

                    if (!(d instanceof Sensor)) {
                        deitar.adicionarAcao(
                                new AcaoDesligar(casa.getId(), div.getId(), d.getId())
                        );
                    }

                    if (d instanceof Cortina || d instanceof Lampada) {
                        acordar.adicionarAcao(
                                new AcaoLigar(casa.getId(), div.getId(), d.getId())
                        );
                    }
                }
            }
        }

        cenarios.put(sair.getNome(), sair);
        cenarios.put(jantar.getNome(), jantar);
        cenarios.put(deitar.getNome(), deitar);
        cenarios.put(acordar.getNome(), acordar);
    }

    public Collection<Cenario> listarCenarios() {
        return cenarios.values();
    }

    public boolean executarCenario(String nome) {

        Cenario c = cenarios.get(nome);

        if (c != null) {
            c.executar(bd);
            return true;
        }

        return false;
    }
}