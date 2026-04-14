package pt.domuscontrol.ui;

import pt.domuscontrol.model.device.Dispositivo;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.model.house.Divisao;
import pt.domuscontrol.service.EstatisticaService;

import java.util.List;

public class MenuEstatisticas {

    private final EstatisticaService estatisticaService;

    public MenuEstatisticas(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    public void mostrar() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("ESTATÍSTICAS");
            System.out.println("  1. Casa que mais consome");
            System.out.println("  2. Top 3 dispositivos por tempo ligado");
            System.out.println("  3. Top 3 dispositivos por número de ativações");
            System.out.println("  4. Top 3 divisões com mais dispositivos");
            System.out.println("  5. Consumo instantâneo total (dispositivos ligados agora)");
            System.out.println("  6. Todas as casas ordenadas por consumo");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1 -> mostrarCasaQueMaisConsome();
                case 2 -> mostrarTopDispositivosTempo();
                case 3 -> mostrarTopDispositivosActivacoes();
                case 4 -> mostrarTopDivisoes();
                case 5 -> mostrarConsumoInstantaneo();
                case 6 -> mostrarCasasPorConsumo();
                case 0 -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void mostrarCasaQueMaisConsome() {
        ConsoleUtils.imprimirTitulo("CASA QUE MAIS CONSOME");
        Casa c = estatisticaService.casaQueMaisConsome();
        if (c == null) System.out.println("  Nenhuma casa registada.");
        else System.out.printf("  %s%n  Consumo total: %.2f Wh%n", c, c.getConsumoTotal());
        ConsoleUtils.pausar();
    }

    private void mostrarTopDispositivosTempo() {
        ConsoleUtils.imprimirTitulo("TOP 3 DISPOSITIVOS POR TEMPO LIGADO");
        String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
        List<Dispositivo> lista = estatisticaService.topDispositivosPorTempo(idCasa, 3);
        if (lista.isEmpty()) { System.out.println("  Sem dados."); }
        else {
            int pos = 1;
            for (Dispositivo d : lista) {
                long horas = d.getTempoLigadoSegundos() / 3600;
                long mins  = (d.getTempoLigadoSegundos() % 3600) / 60;
                System.out.printf("  %d. %s | Tempo: %dh %dmin%n", pos++, d, horas, mins);
            }
        }
        ConsoleUtils.pausar();
    }

    private void mostrarTopDispositivosActivacoes() {
        ConsoleUtils.imprimirTitulo("TOP 3 DISPOSITIVOS POR ATIVAÇÕES");
        String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
        List<Dispositivo> lista = estatisticaService.topDispositivosPorActivacoes(idCasa, 3);
        if (lista.isEmpty()) { System.out.println("  Sem dados."); }
        else {
            int pos = 1;
            for (Dispositivo d : lista)
                System.out.printf("  %d. %s | Ativações: %d%n", pos++, d, d.getNumActivacoes());
        }
        ConsoleUtils.pausar();
    }

    private void mostrarTopDivisoes() {
        ConsoleUtils.imprimirTitulo("TOP 3 DIVISÕES COM MAIS DISPOSITIVOS");
        List<Divisao> lista = estatisticaService.topDivisoesPorNumDispositivos(3);
        if (lista.isEmpty()) { System.out.println("  Sem dados."); }
        else {
            int pos = 1;
            for (Divisao d : lista)
                System.out.printf("  %d. %s | Dispositivos: %d%n", pos++, d, d.getNumDispositivos());
        }
        ConsoleUtils.pausar();
    }

    private void mostrarConsumoInstantaneo() {
        ConsoleUtils.imprimirTitulo("CONSUMO INSTANTÂNEO");
        System.out.printf("  Dispositivos ligados: %d%n", estatisticaService.totalDispositivosLigados());
        System.out.printf("  Consumo actual: %.2f Wh%n", estatisticaService.consumoInstantaneoTotal());
        ConsoleUtils.pausar();
    }

    private void mostrarCasasPorConsumo() {
        ConsoleUtils.imprimirTitulo("CASAS POR CONSUMO");
        List<Casa> lista = estatisticaService.casasOrdenadasPorConsumo();
        if (lista.isEmpty()) { System.out.println("  Nenhuma casa registada."); }
        else {
            int pos = 1;
            for (Casa c : lista)
                System.out.printf("  %d. %s | Consumo: %.2f Wh%n", pos++, c, c.getConsumoTotal());
        }
        ConsoleUtils.pausar();
    }
}
