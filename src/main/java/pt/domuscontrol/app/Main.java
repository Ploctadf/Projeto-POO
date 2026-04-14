package pt.domuscontrol.app;

import pt.domuscontrol.exception.PersistenciaException;
import pt.domuscontrol.persistence.BaseDados;
import pt.domuscontrol.persistence.DadosDemo;
import pt.domuscontrol.persistence.RepositorioBinario;
import pt.domuscontrol.service.CasaService;
import pt.domuscontrol.service.DispositivoService;
import pt.domuscontrol.service.EstatisticaService;
import pt.domuscontrol.ui.ConsoleUtils;
import pt.domuscontrol.ui.MenuEstatisticas;
import pt.domuscontrol.ui.MenuGestao;

/**
 * Ponto de entrada da aplicação DomusControl.
 */
public class Main {

    public static void main(String[] args) {
        ConsoleUtils.imprimirTitulo("BEM-VINDO AO DOMUSCONTROL");

        BaseDados bd = carregarOuInicializar();

        CasaService casaService = new CasaService(bd);
        DispositivoService dispositivoService = new DispositivoService(bd, casaService);
        EstatisticaService estatisticaService = new EstatisticaService(bd);

        // TODO: autenticação por menu
        String utilizadorAtualId = bd.getUtilizadores().isEmpty()
                ? "U001"
                : bd.getUtilizadores().get(0).getId();

        System.out.println("  Relógio: " + bd.getRelogio());
        System.out.println("  Utilizador actual: " + utilizadorAtualId);

        MenuGestao menuGestao = new MenuGestao(casaService, dispositivoService, utilizadorAtualId);
        MenuEstatisticas menuEstatisticas = new MenuEstatisticas(estatisticaService);

        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("MENU PRINCIPAL | " + bd.getRelogio());
            System.out.println("  1. Gestão (casas, divisões, dispositivos, utilizadores)");
            System.out.println("  2. Estatísticas");
            System.out.println("  3. Automações e Escalonamentos");
            System.out.println("  4. Cenários");
            System.out.println("  5. Sugestões automáticas");
            System.out.println("  6. Avançar tempo simulado");
            System.out.println("  7. Guardar estado");
            System.out.println("  8. Carregar estado");
            System.out.println("  0. Sair");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");

            switch (opcao) {
                case 1 -> menuGestao.mostrar();
                case 2 -> menuEstatisticas.mostrar();
                case 3 -> System.out.println("  [Em desenvolvimento]");
                case 4 -> System.out.println("  [Em desenvolvimento]");
                case 5 -> System.out.println("  [Em desenvolvimento]");
                case 6 -> menuAvancarTempo(bd);
                case 7 -> guardar(bd);
                case 8 -> { bd = carregar(bd); }
                case 0 -> {
                    if (ConsoleUtils.lerSimNao("  Guardar antes de sair?")) guardar(bd);
                    System.out.println("  Até logo!");
                }
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static BaseDados carregarOuInicializar() {
        if (RepositorioBinario.existeFicheiro()) {
            if (ConsoleUtils.lerSimNao("  Ficheiro guardado encontrado. Carregar?")) {
                try {
                    BaseDados bd = RepositorioBinario.carregar();
                    ConsoleUtils.imprimirSucesso("Estado carregado com sucesso.");
                    return bd;
                } catch (PersistenciaException e) {
                    ConsoleUtils.imprimirErro(e.getMessage() + " — a usar dados de demonstração.");
                }
            }
        }
        ConsoleUtils.imprimirSucesso("A carregar dados de demonstração...");
        return DadosDemo.criar();
    }

    private static void menuAvancarTempo(BaseDados bd) {
        ConsoleUtils.imprimirTitulo("AVANÇAR TEMPO | Actual: " + bd.getRelogio());
        System.out.println("  1. Avançar minutos");
        System.out.println("  2. Avançar horas");
        System.out.println("  3. Avançar dias");
        int op = ConsoleUtils.lerOpcao("  Opção: ");
        switch (op) {
            case 1 -> { long m = ConsoleUtils.lerInteiro("  Minutos: ", 1, 9999); bd.getRelogio().avancarMinutos(m); }
            case 2 -> { long h = ConsoleUtils.lerInteiro("  Horas: ", 1, 9999);   bd.getRelogio().avancarHoras(h); }
            case 3 -> { long d = ConsoleUtils.lerInteiro("  Dias: ", 1, 365);     bd.getRelogio().avancarDias(d); }
            default -> ConsoleUtils.imprimirErro("Opção inválida.");
        }
        ConsoleUtils.imprimirSucesso("Tempo actual: " + bd.getRelogio());
        ConsoleUtils.pausar();
    }

    private static void guardar(BaseDados bd) {
        try {
            RepositorioBinario.guardar(bd);
            ConsoleUtils.imprimirSucesso("Estado guardado em ficheiro.");
        } catch (PersistenciaException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private static BaseDados carregar(BaseDados bdAtual) {
        try {
            BaseDados bd = RepositorioBinario.carregar();
            ConsoleUtils.imprimirSucesso("Estado carregado com sucesso.");
            ConsoleUtils.pausar();
            return bd;
        } catch (PersistenciaException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
            ConsoleUtils.pausar();
            return bdAtual;
        }
    }
}
