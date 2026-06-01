package pt.domuscontrol.ui;

import pt.domuscontrol.model.automation.*;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.persistence.BaseDados;
import pt.domuscontrol.service.AutomacaoService;
import pt.domuscontrol.service.CasaService;
import pt.domuscontrol.service.CenarioService;
import pt.domuscontrol.service.SugestaoService;

import java.util.List;

/**
 * Menu de texto para gerir e testar Automações e Escalonamentos.
 */
public class MenuAutomacoes {

    private final AutomacaoService automacaoService;
    private final CasaService casaService;
    private final BaseDados bd;
    private final CenarioService cenarioService;
    private final SugestaoService sugestaoService;

    public MenuAutomacoes(AutomacaoService automacaoService, CasaService casaService, BaseDados bd, CenarioService cenarioService, SugestaoService sugestaoService) {
        this.automacaoService = automacaoService;
        this.casaService = casaService;
        this.bd = bd;
        this.cenarioService = cenarioService;
        this.sugestaoService = sugestaoService;
    }

    public void mostrar() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("AUTOMAÇÕES E ESCALONAMENTOS");
            System.out.println("  --- Automações ---");
            System.out.println("  1. Listar automações");
            System.out.println("  2. Criar automação (sensor)");
            System.out.println("  3. Criar automação (horário)");
            System.out.println("  4. Avaliar todas as automações agora");
            System.out.println("  5. Ativar / desativar automação");
            System.out.println("  6. Remover automação");
            System.out.println("  --- Escalonamentos ---");
            System.out.println("  7. Listar escalonamentos");
            System.out.println("  8. Criar escalonamento");
            System.out.println("  9. Verificar escalonamentos agora");
            System.out.println("  10. Ativar / desativar escalonamento");
            System.out.println("  11. Remover escalonamento");
            System.out.println("  --- Cenários ---");
            System.out.println("  12. Executar cenário");
            System.out.println("  13. Ver sugestões automáticas");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1  -> listarAutomacoes();
                case 2  -> criarAutomacaoSensor();
                case 3  -> criarAutomacaoHorario();
                case 4  -> avaliarAutomacoes();
                case 5  -> toggleAutomacao();
                case 6  -> removerAutomacao();
                case 7  -> listarEscalonamentos();
                case 8  -> criarEscalonamento();
                case 9  -> verificarEscalonamentos();
                case 10 -> toggleEscalonamento();
                case 11 -> removerEscalonamento();
                case 12 -> executarCenario();
                case 13 -> mostrarSugestoes();
                case 0  -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // ==================== AUTOMAÇÕES ====================

    private void listarAutomacoes() {
        ConsoleUtils.imprimirTitulo("LISTA DE AUTOMAÇÕES");
        List<Automacao> lista = automacaoService.listarAutomacoes();
        if (lista.isEmpty()) {
            System.out.println("  Nenhuma automação registada.");
        } else {
            for (int i = 0; i < lista.size(); i++)
                System.out.printf("  %d. %s%n", i + 1, lista.get(i).descricao());
        }
        ConsoleUtils.pausar();
    }

    private void criarAutomacaoSensor() {
        ConsoleUtils.imprimirTitulo("CRIAR AUTOMAÇÃO (GATILHO: SENSOR)");
        try {
            String casaId = pedirCasa();
            System.out.println("\n  -- Gatilho: qual sensor dispara? --");
            String divSensorId = ConsoleUtils.lerTexto("  ID da divisão do sensor: ");
            String sensorId    = ConsoleUtils.lerTexto("  ID do sensor: ");
            System.out.println("  Operador:  1. Maior que   2. Menor que");
            int opOp = ConsoleUtils.lerInteiro("  Escolha: ", 1, 2);
            GatilhoSensor.Operador operador = opOp == 1
                    ? GatilhoSensor.Operador.MAIOR_QUE
                    : GatilhoSensor.Operador.MENOR_QUE;
            double limiar = ConsoleUtils.lerDouble("  Limiar: ", -999, 99999);

            System.out.println("\n  -- Ação: o que acontece? --");
            Acao acao = pedirAcao(casaId);

            String nome = ConsoleUtils.lerTexto("\n  Nome da automação: ");
            String id   = automacaoService.gerarIdAutomacao();
            Gatilho gatilho = new GatilhoSensor(casaId, divSensorId, sensorId, operador, limiar);
            Automacao a = new Automacao(id, nome, casaId, gatilho, acao);
            automacaoService.adicionarAutomacao(a);
            ConsoleUtils.imprimirSucesso("Automação criada: " + a.descricao());
        } catch (Exception e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void criarAutomacaoHorario() {
        ConsoleUtils.imprimirTitulo("CRIAR AUTOMAÇÃO (GATILHO: HORÁRIO)");
        try {
            String casaId = pedirCasa();
            System.out.println("\n  -- Gatilho: a que horas dispara? --");
            int hora   = ConsoleUtils.lerInteiro("  Hora (0-23): ", 0, 23);
            int minuto = ConsoleUtils.lerInteiro("  Minuto (0-59): ", 0, 59);

            System.out.println("\n  -- Ação: o que acontece? --");
            Acao acao = pedirAcao(casaId);

            String nome = ConsoleUtils.lerTexto("\n  Nome da automação: ");
            String id   = automacaoService.gerarIdAutomacao();
            Gatilho gatilho = new GatilhoHorario(hora, minuto);
            Automacao a = new Automacao(id, nome, casaId, gatilho, acao);
            automacaoService.adicionarAutomacao(a);
            ConsoleUtils.imprimirSucesso("Automação criada: " + a.descricao());
        } catch (Exception e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void avaliarAutomacoes() {
        ConsoleUtils.imprimirTitulo("AVALIAR AUTOMAÇÕES | Relógio: " + bd.getRelogio());
        List<String> executadas = automacaoService.avaliarTodasAutomacoes();
        if (executadas.isEmpty()) {
            System.out.println("  Nenhuma automação disparou.");
        } else {
            System.out.println("  Automações executadas:");
            executadas.forEach(n -> System.out.println("    ✓ " + n));
        }
        ConsoleUtils.pausar();
    }

    private void toggleAutomacao() {
        ConsoleUtils.imprimirTitulo("ATIVAR / DESATIVAR AUTOMAÇÃO");
        listarAutomacoes();
        String id = ConsoleUtils.lerTexto("  ID da automação: ");
        automacaoService.listarAutomacoes().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(a -> {
                    a.setAtiva(!a.isAtiva());
                    ConsoleUtils.imprimirSucesso("Automação " + id + " agora está " + (a.isAtiva() ? "ATIVA" : "INATIVA"));
                }, () -> ConsoleUtils.imprimirErro("Automação não encontrada: " + id));
        ConsoleUtils.pausar();
    }

    private void removerAutomacao() {
        ConsoleUtils.imprimirTitulo("REMOVER AUTOMAÇÃO");
        listarAutomacoes();
        String id = ConsoleUtils.lerTexto("  ID da automação a remover: ");
        if (automacaoService.removerAutomacao(id))
            ConsoleUtils.imprimirSucesso("Automação removida.");
        else
            ConsoleUtils.imprimirErro("Automação não encontrada: " + id);
        ConsoleUtils.pausar();
    }

    // ==================== ESCALONAMENTOS ====================

    private void listarEscalonamentos() {
        ConsoleUtils.imprimirTitulo("LISTA DE ESCALONAMENTOS");
        List<Escalonamento> lista = automacaoService.listarEscalonamentos();
        if (lista.isEmpty()) {
            System.out.println("  Nenhum escalonamento registado.");
        } else {
            for (int i = 0; i < lista.size(); i++)
                System.out.printf("  %d. %s%n", i + 1, lista.get(i).descricao());
        }
        ConsoleUtils.pausar();
    }

    private void criarEscalonamento() {
        ConsoleUtils.imprimirTitulo("CRIAR ESCALONAMENTO");
        try {
            String casaId = pedirCasa();
            int hora   = ConsoleUtils.lerInteiro("  Hora de disparo (0-23): ", 0, 23);
            int minuto = ConsoleUtils.lerInteiro("  Minuto (0-59): ", 0, 59);

            System.out.println("\n  -- Ação: o que acontece? --");
            Acao acao = pedirAcao(casaId);

            String nome = ConsoleUtils.lerTexto("\n  Nome do escalonamento: ");
            String id   = automacaoService.gerarIdEscalonamento();
            Escalonamento e = new Escalonamento(id, nome, casaId, hora, minuto, acao);
            automacaoService.adicionarEscalonamento(e);
            ConsoleUtils.imprimirSucesso("Escalonamento criado: " + e.descricao());
        } catch (Exception e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void verificarEscalonamentos() {
        ConsoleUtils.imprimirTitulo("VERIFICAR ESCALONAMENTOS | Relógio: " + bd.getRelogio());
        List<String> executados = automacaoService.verificarEscalonamentos();
        if (executados.isEmpty()) {
            System.out.println("  Nenhum escalonamento disparou agora.");
        } else {
            System.out.println("  Escalonamentos executados:");
            executados.forEach(n -> System.out.println("    ✓ " + n));
        }
        ConsoleUtils.pausar();
    }

    private void toggleEscalonamento() {
        ConsoleUtils.imprimirTitulo("ATIVAR / DESATIVAR ESCALONAMENTO");
        listarEscalonamentos();
        String id = ConsoleUtils.lerTexto("  ID do escalonamento: ");
        automacaoService.listarEscalonamentos().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(e -> {
                    e.setAtivo(!e.isAtivo());
                    ConsoleUtils.imprimirSucesso("Escalonamento " + id + " agora está " + (e.isAtivo() ? "ATIVO" : "INATIVO"));
                }, () -> ConsoleUtils.imprimirErro("Escalonamento não encontrado: " + id));
        ConsoleUtils.pausar();
    }

    private void removerEscalonamento() {
        ConsoleUtils.imprimirTitulo("REMOVER ESCALONAMENTO");
        listarEscalonamentos();
        String id = ConsoleUtils.lerTexto("  ID do escalonamento a remover: ");
        if (automacaoService.removerEscalonamento(id))
            ConsoleUtils.imprimirSucesso("Escalonamento removido.");
        else
            ConsoleUtils.imprimirErro("Escalonamento não encontrado: " + id);
        ConsoleUtils.pausar();
    }

    // ==================== Auxiliares ====================

    private String pedirCasa() {
        System.out.println("\n  Casas disponíveis:");
        casaService.listarCasas().forEach(c -> System.out.println("    " + c));
        return ConsoleUtils.lerTexto("  ID da casa: ");
    }

    /**
     * Pede ao utilizador que escolha a ação a executar.
     * Por simplicidade suporta ligar, desligar, e ajustar cortina.
     */
    private Acao pedirAcao(String casaId) {
        System.out.println("  Tipo de ação:");
        System.out.println("    1. Ligar dispositivo");
        System.out.println("    2. Desligar dispositivo");
        System.out.println("    3. Ajustar cortina (%)");
        int tipoAcao = ConsoleUtils.lerInteiro("  Escolha: ", 1, 3);

        // Mostrar divisões e dispositivos para ajudar o utilizador
        Casa casa = casaService.listarCasas().stream()
                .filter(c -> c.getId().equals(casaId)).findFirst().orElse(null);
        if (casa != null) {
            casa.getDivisoes().forEach(d -> {
                System.out.println("    Divisão " + d.getId() + " - " + d.getNome());
                d.getDispositivos().forEach(disp ->
                        System.out.println("      -> " + disp.getId() + " | " + disp.getMarca() + " " + disp.getModelo()));
            });
        }

        String divId  = ConsoleUtils.lerTexto("  ID da divisão do dispositivo: ");
        String dispId = ConsoleUtils.lerTexto("  ID do dispositivo: ");

        return switch (tipoAcao) {
            case 1 -> new AcaoLigar(casaId, divId, dispId);
            case 2 -> new AcaoDesligar(casaId, divId, dispId);
            case 3 -> {
                int pct = ConsoleUtils.lerInteiro("  Percentagem de abertura (0-100): ", 0, 100);
                yield new AcaoAjustarCortina(casaId, divId, dispId, pct);
            }
            default -> throw new IllegalArgumentException("Tipo de ação inválido");
        };
    }

    private void executarCenario() {

        ConsoleUtils.imprimirTitulo("EXECUTAR CENÁRIO");

        cenarioService.listarCenarios()
                .forEach(c -> System.out.println("  - " + c.getNome()));

        String nome = ConsoleUtils.lerTexto("  Nome do cenário: ");

        if (cenarioService.executarCenario(nome)) {
            ConsoleUtils.imprimirSucesso("Cenário executado.");
        } else {
            ConsoleUtils.imprimirErro("Cenário não encontrado.");
        }

        ConsoleUtils.pausar();
    }

    private void mostrarSugestoes() {

        ConsoleUtils.imprimirTitulo("SUGESTÕES AUTOMÁTICAS");

        List<String> sugestoes = sugestaoService.gerarSugestoes();

        if (sugestoes.isEmpty()) {

            System.out.println("  Sem sugestões disponíveis.");
        }
        else {

            sugestoes.forEach(s ->
                    System.out.println("  ✓ " + s));
        }

        ConsoleUtils.pausar();
    }
}