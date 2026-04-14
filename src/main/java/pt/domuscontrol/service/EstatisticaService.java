package pt.domuscontrol.service;

import pt.domuscontrol.model.device.Dispositivo;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.model.house.Divisao;
import pt.domuscontrol.persistence.BaseDados;

import java.util.Comparator;
import java.util.List;

public class EstatisticaService {

    private final BaseDados bd;

    public EstatisticaService(BaseDados bd) {
        this.bd = bd;
    }

    // Casa que mais consome (total acumulado em Wh)
    public Casa casaQueMaisConsome() {
        return bd.getCasas().stream()
                .max(Comparator.comparingDouble(Casa::getConsumoTotal))
                .orElse(null);
    }

    // Top 3 dispositivos de uma casa ordenados por tempo ligado (segundos)
    public List<Dispositivo> topDispositivosPorTempo(String casaId, int n) {
        return bd.getCasas().stream()
                .filter(c -> c.getId().equals(casaId))
                .findFirst()
                .map(c -> c.getTodosDispositivos().stream()
                        .sorted(Comparator.comparingLong(Dispositivo::getTempoLigadoSegundos).reversed())
                        .limit(n)
                        .toList())
                .orElse(List.of());
    }

    // Top 3 dispositivos de uma casa ordenados por número de ativações
    public List<Dispositivo> topDispositivosPorActivacoes(String casaId, int n) {
        return bd.getCasas().stream()
                .filter(c -> c.getId().equals(casaId))
                .findFirst()
                .map(c -> c.getTodosDispositivos().stream()
                        .sorted(Comparator.comparingInt(Dispositivo::getNumActivacoes).reversed())
                        .limit(n)
                        .toList())
                .orElse(List.of());
    }

    // Top 3 divisões (de todas as casas) com mais dispositivos
    public List<Divisao> topDivisoesPorNumDispositivos(int n) {
        return bd.getCasas().stream()
                .flatMap(c -> c.getDivisoes().stream())
                .sorted(Comparator.comparingInt(Divisao::getNumDispositivos).reversed())
                .limit(n)
                .toList();
    }

    // Consumo total de uma casa específica
    public double consumoTotalCasa(String casaId) {
        return bd.getCasas().stream()
                .filter(c -> c.getId().equals(casaId))
                .mapToDouble(Casa::getConsumoTotal)
                .findFirst()
                .orElse(0.0);
    }

    // Número total de dispositivos ligados em toda a aplicação
    public long totalDispositivosLigados() {
        return bd.getCasas().stream()
                .flatMap(c -> c.getTodosDispositivos().stream())
                .filter(Dispositivo::isLigado)
                .count();
    }

    // Consumo total de todos os dispositivos ligados agora (em W/h)
    public double consumoInstantaneoTotal() {
        return bd.getCasas().stream()
                .flatMap(c -> c.getTodosDispositivos().stream())
                .filter(Dispositivo::isLigado)
                .mapToDouble(Dispositivo::getConsumoPorHora)
                .sum();
    }

    // Lista todas as casas ordenadas por consumo (maior primeiro)
    public List<Casa> casasOrdenadasPorConsumo() {
        return bd.getCasas().stream()
                .sorted(Comparator.comparingDouble(Casa::getConsumoTotal).reversed())
                .toList();
    }
}
