package pt.domuscontrol.service;

import pt.domuscontrol.model.user.RegistoInteracao;
import pt.domuscontrol.persistence.BaseDados;

import java.time.LocalTime;
import java.util.*;

public class SugestaoService {

    private final BaseDados bd;

    public SugestaoService(BaseDados bd) {
        this.bd = bd;
    }

    public List<String> gerarSugestoes() {

        List<String> sugestoes = new ArrayList<>();

        Map<String, Integer> repeticoes = new HashMap<>();

        for (RegistoInteracao r : bd.getInteracoes()) {

            LocalTime hora =
                    r.getTimestamp()
                            .toLocalTime()
                            .withSecond(0)
                            .withNano(0);

            String chave =
                    r.getDispositivoId() +
                            " | " +
                            r.getAcao() +
                            " | " +
                            hora;

            repeticoes.put(
                    chave,
                    repeticoes.getOrDefault(chave, 0) + 1
            );
        }

        for (Map.Entry<String, Integer> e : repeticoes.entrySet()) {

            if (e.getValue() == 3) {

                sugestoes.add(
                        "Sugestão automática: criar escalonamento para -> "
                                + e.getKey()
                );
            }
        }

        return sugestoes;
    }
}