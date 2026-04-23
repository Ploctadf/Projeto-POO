package pt.domuscontrol.ui;

import pt.domuscontrol.exception.*;
import pt.domuscontrol.model.device.*;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.model.house.Divisao;
import pt.domuscontrol.model.house.TipoDivisao;
import pt.domuscontrol.model.user.Utilizador;
import pt.domuscontrol.service.CasaService;
import pt.domuscontrol.service.DispositivoService;

public class MenuGestao {

    private final CasaService casaService;
    private final DispositivoService dispositivoService;
    private String utilizadorAtualId;

    public MenuGestao(CasaService casaService, DispositivoService dispositivoService, String utilizadorAtualId) {
        this.casaService = casaService;
        this.dispositivoService = dispositivoService;
        this.utilizadorAtualId = utilizadorAtualId;
    }

    public void mostrar() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("GESTÃO DO SISTEMA");
            System.out.println("  1. Gerir utilizadores");
            System.out.println("  2. Gerir casas");
            System.out.println("  3. Gerir divisões");
            System.out.println("  4. Gerir dispositivos");
            System.out.println("  5. Operar dispositivo");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1 -> menuUtilizadores();
                case 2 -> menuCasas();
                case 3 -> menuDivisoes();
                case 4 -> menuDispositivos();
                case 5 -> menuOperarDispositivo();
                case 0 -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    // ==================== UTILIZADORES ====================

    private void menuUtilizadores() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("UTILIZADORES");
            System.out.println("  1. Listar utilizadores");
            System.out.println("  2. Criar utilizador");
            System.out.println("  3. Editar utilizador");
            System.out.println("  4. Remover utilizador");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1 -> listarUtilizadores();
                case 2 -> criarUtilizador();
                case 3 -> editarUtilizador();
                case 4 -> removerUtilizador();
                case 0 -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarUtilizadores() {
        ConsoleUtils.imprimirTitulo("LISTA DE UTILIZADORES");
        var lista = casaService.listarUtilizadores();
        if (lista.isEmpty()) { System.out.println("  Nenhum utilizador registado."); }
        else lista.forEach(u -> System.out.println("  " + u));
        ConsoleUtils.pausar();
    }

    private void criarUtilizador() {
        ConsoleUtils.imprimirTitulo("CRIAR UTILIZADOR");
        try {
            String nome  = ConsoleUtils.lerTexto("  Nome: ");
            String email = ConsoleUtils.lerTexto("  Email: ");
            String pass  = ConsoleUtils.lerTexto("  Password: ");
            Utilizador u = casaService.criarUtilizador(nome, email, pass);
            ConsoleUtils.imprimirSucesso("Utilizador criado: " + u);
        } catch (IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void editarUtilizador() {
        ConsoleUtils.imprimirTitulo("EDITAR UTILIZADOR");
        listarUtilizadores();
        try {
            String id = ConsoleUtils.lerTexto("  ID do utilizador a editar: ");
            String novoNome  = ConsoleUtils.lerTexto("  Novo nome (Enter para manter): ");
            String novoEmail = ConsoleUtils.lerTexto("  Novo email (Enter para manter): ");
            casaService.editarUtilizador(id,
                    novoNome.isBlank()  ? null : novoNome,
                    novoEmail.isBlank() ? null : novoEmail);
            ConsoleUtils.imprimirSucesso("Utilizador actualizado.");
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void removerUtilizador() {
        ConsoleUtils.imprimirTitulo("REMOVER UTILIZADOR");
        listarUtilizadores();
        try {
            String id = ConsoleUtils.lerTexto("  ID do utilizador a remover: ");
            casaService.removerUtilizador(id);
            ConsoleUtils.imprimirSucesso("Utilizador removido.");
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    // ==================== CASAS ====================

    private void menuCasas() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("CASAS");
            System.out.println("  1. Listar casas");
            System.out.println("  2. Criar casa");
            System.out.println("  3. Ver detalhes de uma casa");
            System.out.println("  4. Editar casa");
            System.out.println("  5. Associar utilizador a casa");
            System.out.println("  6. Remover casa");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1 -> listarCasas();
                case 2 -> criarCasa();
                case 3 -> verDetalhesCasa();
                case 4 -> editarCasa();
                case 5 -> associarUtilizadorCasa();
                case 6 -> removerCasa();
                case 0 -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarCasas() {
        ConsoleUtils.imprimirTitulo("LISTA DE CASAS");
        var lista = casaService.listarCasas();
        if (lista.isEmpty()) { System.out.println("  Nenhuma casa registada."); }
        else lista.forEach(c -> System.out.println("  " + c));
        ConsoleUtils.pausar();
    }

    private void criarCasa() {
        ConsoleUtils.imprimirTitulo("CRIAR CASA");
        try {
            listarUtilizadores();
            String idProp = ConsoleUtils.lerTexto("  ID do proprietário: ");
            String nome   = ConsoleUtils.lerTexto("  Nome da casa: ");
            String morada = ConsoleUtils.lerTexto("  Morada: ");
            Casa c = casaService.criarCasa(nome, morada, idProp);
            ConsoleUtils.imprimirSucesso("Casa criada: " + c);
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void verDetalhesCasa() {
        ConsoleUtils.imprimirTitulo("DETALHES DA CASA");
        try {
            listarCasas();
            String id = ConsoleUtils.lerTexto("  ID da casa: ");
            Casa c = casaService.getCasaPorId(id);
            System.out.println("\n  " + c);
            System.out.printf("  Consumo total: %.2f Wh%n", c.getConsumoTotal());
            System.out.println("\n  Proprietários:");
            c.getProprietarios().forEach(u -> System.out.println("    " + u));
            System.out.println("\n  Usufrutuários:");
            if (c.getUsufrutuarios().isEmpty()) System.out.println("    (nenhum)");
            else c.getUsufrutuarios().forEach(u -> System.out.println("    " + u));
            System.out.println("\n  Divisões:");
            c.getDivisoes().forEach(d -> {
                System.out.println("    " + d);
                d.getDispositivos().forEach(disp -> System.out.println("      -> " + disp));
            });
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void associarUtilizadorCasa() {
        ConsoleUtils.imprimirTitulo("ASSOCIAR UTILIZADOR A CASA");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            listarUtilizadores();
            String idUtil = ConsoleUtils.lerTexto("  ID do utilizador: ");
            boolean prop = ConsoleUtils.lerSimNao("  Como proprietário?");
            casaService.associarUtilizador(idCasa, idUtil, prop);
            ConsoleUtils.imprimirSucesso("Utilizador associado.");
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void editarCasa() {
        ConsoleUtils.imprimirTitulo("EDITAR CASA");
        try {
            listarCasas();
            String id = ConsoleUtils.lerTexto("  ID da casa a editar: ");
            String novoNome   = ConsoleUtils.lerTexto("  Novo nome (Enter para manter): ");
            String novaMorada = ConsoleUtils.lerTexto("  Nova morada (Enter para manter): ");
            casaService.editarCasa(id,
                    novoNome.isBlank()   ? null : novoNome,
                    novaMorada.isBlank() ? null : novaMorada);
            ConsoleUtils.imprimirSucesso("Casa actualizada.");
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void removerCasa() {
        ConsoleUtils.imprimirTitulo("REMOVER CASA");
        try {
            listarCasas();
            String id = ConsoleUtils.lerTexto("  ID da casa a remover: ");
            if (ConsoleUtils.lerSimNao("  Confirma remoção?")) {
                casaService.removerCasa(id);
                ConsoleUtils.imprimirSucesso("Casa removida.");
            }
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    // ==================== DIVISÕES ====================

    private void menuDivisoes() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("DIVISÕES");
            System.out.println("  1. Listar divisões de uma casa");
            System.out.println("  2. Criar divisão");
            System.out.println("  3. Editar divisão");
            System.out.println("  4. Remover divisão");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1 -> listarDivisoes();
                case 2 -> criarDivisao();
                case 3 -> editarDivisao();
                case 4 -> removerDivisao();
                case 0 -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarDivisoes() {
        ConsoleUtils.imprimirTitulo("DIVISÕES DA CASA");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            casaService.getCasaPorId(idCasa).getDivisoes()
                    .forEach(d -> System.out.println("  " + d));
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void criarDivisao() {
        ConsoleUtils.imprimirTitulo("CRIAR DIVISÃO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            String nome = ConsoleUtils.lerTexto("  Nome da divisão: ");
            TipoDivisao tipo = escolherTipoDivisao();
            Divisao d = casaService.criarDivisao(idCasa, nome, tipo);
            ConsoleUtils.imprimirSucesso("Divisão criada: " + d);
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void editarDivisao() {
        ConsoleUtils.imprimirTitulo("EDITAR DIVISÃO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            casaService.getCasaPorId(idCasa).getDivisoes()
                    .forEach(d -> System.out.println("  " + d));
            String idDiv  = ConsoleUtils.lerTexto("  ID da divisão a editar: ");
            String novoNome = ConsoleUtils.lerTexto("  Novo nome (Enter para manter): ");
            System.out.println("  Novo tipo (Enter para manter):");
            TipoDivisao[] tipos = TipoDivisao.values();
            for (int i = 0; i < tipos.length; i++)
                System.out.printf("  %d. %s%n", i + 1, tipos[i]);
            System.out.println("  0. Manter actual");
            int idx = ConsoleUtils.lerInteiro("  Tipo: ", 0, tipos.length);
            TipoDivisao novoTipo = idx == 0 ? null : tipos[idx - 1];
            casaService.editarDivisao(idCasa, idDiv,
                    novoNome.isBlank() ? null : novoNome, novoTipo);
            ConsoleUtils.imprimirSucesso("Divisão actualizada.");
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void removerDivisao() {
        ConsoleUtils.imprimirTitulo("REMOVER DIVISÃO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            Casa c = casaService.getCasaPorId(idCasa);
            c.getDivisoes().forEach(d -> System.out.println("  " + d));
            String idDiv = ConsoleUtils.lerTexto("  ID da divisão a remover: ");
            casaService.removerDivisao(idCasa, idDiv);
            ConsoleUtils.imprimirSucesso("Divisão removida.");
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    // ==================== DISPOSITIVOS ====================

    private void menuDispositivos() {
        int opcao;
        do {
            ConsoleUtils.imprimirTitulo("DISPOSITIVOS");
            System.out.println("  1. Listar dispositivos de uma divisão");
            System.out.println("  2. Adicionar dispositivo");
            System.out.println("  3. Editar dispositivo");
            System.out.println("  4. Remover dispositivo");
            System.out.println("  0. Voltar");
            opcao = ConsoleUtils.lerOpcao("\n  Opção: ");
            switch (opcao) {
                case 1 -> listarDispositivos();
                case 2 -> adicionarDispositivo();
                case 3 -> editarDispositivo();
                case 4 -> removerDispositivo();
                case 0 -> {}
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarDispositivos() {
        ConsoleUtils.imprimirTitulo("DISPOSITIVOS DA DIVISÃO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            casaService.getCasaPorId(idCasa).getDivisoes()
                    .forEach(d -> System.out.println("  " + d));
            String idDiv = ConsoleUtils.lerTexto("  ID da divisão: ");
            casaService.getDivisaoPorId(idCasa, idDiv).getDispositivos()
                    .forEach(d -> System.out.println("    -> " + d + " | " + d.descricao()));
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void editarDispositivo() {
        ConsoleUtils.imprimirTitulo("EDITAR DISPOSITIVO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            casaService.getCasaPorId(idCasa).getDivisoes().forEach(d -> {
                System.out.println("  " + d);
                d.getDispositivos().forEach(disp -> System.out.println("    -> " + disp));
            });
            String idDiv  = ConsoleUtils.lerTexto("  ID da divisão: ");
            String idDisp = ConsoleUtils.lerTexto("  ID do dispositivo a editar: ");
            String novaMarca  = ConsoleUtils.lerTexto("  Nova marca (Enter para manter): ");
            String novoModelo = ConsoleUtils.lerTexto("  Novo modelo (Enter para manter): ");
            String consStr    = ConsoleUtils.lerTexto("  Novo consumo Wh (Enter para manter): ");
            double novoConsumo = consStr.isBlank() ? -1 : Double.parseDouble(consStr);
            casaService.editarDispositivo(idCasa, idDiv, idDisp,
                    novaMarca.isBlank()  ? null : novaMarca,
                    novoModelo.isBlank() ? null : novoModelo,
                    novoConsumo);
            ConsoleUtils.imprimirSucesso("Dispositivo actualizado.");
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void adicionarDispositivo() {
        ConsoleUtils.imprimirTitulo("ADICIONAR DISPOSITIVO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            Casa c = casaService.getCasaPorId(idCasa);
            c.getDivisoes().forEach(d -> System.out.println("  " + d));
            String idDiv = ConsoleUtils.lerTexto("  ID da divisão: ");

            System.out.println("\n  Tipo de dispositivo:");
            System.out.println("  1. Lâmpada");
            System.out.println("  2. Ar Condicionado");
            System.out.println("  3. Sensor");
            System.out.println("  4. Tomada Inteligente");
            System.out.println("  5. Cortina");
            System.out.println("  6. Coluna de Som");
            System.out.println("  7. Portão de Garagem");
            int tipo = ConsoleUtils.lerOpcao("  Tipo: ");

            String id    = casaService.gerarIdDispositivo();
            String marca = ConsoleUtils.lerTexto("  Marca: ");
            String model = ConsoleUtils.lerTexto("  Modelo: ");
            double cons  = ConsoleUtils.lerDouble("  Consumo por hora (Wh): ", 0, 5000);

            Dispositivo d = switch (tipo) {
                case 1 -> {
                    int intensidade = ConsoleUtils.lerInteiro("  Intensidade (0-100): ", 0, 100);
                    int cor = ConsoleUtils.lerInteiro("  Temperatura de cor (2700-4000K): ", 2700, 4000);
                    yield new Lampada(id, marca, model, cons, intensidade, cor);
                }
                case 2 -> {
                    double temp = ConsoleUtils.lerDouble("  Temperatura (16-30°C): ", 16, 30);
                    ModoArCondicionado modo = escolherModoAC();
                    yield new ArCondicionado(id, marca, model, cons, temp, modo);
                }
                case 3 -> {
                    TipoSensor tipoSensor = escolherTipoSensor();
                    yield new Sensor(id, marca, model, cons, tipoSensor);
                }
                case 4 -> new TomadaInteligente(id, marca, model, cons);
                case 5 -> new Cortina(id, marca, model, cons);
                case 6 -> {
                    int vol = ConsoleUtils.lerInteiro("  Volume inicial (0-100): ", 0, 100);
                    yield new ColunaDesom(id, marca, model, cons, vol);
                }
                case 7 -> new PortaoGaragem(id, marca, model, cons);
                default -> throw new IllegalArgumentException("Tipo inválido");
            };

            casaService.adicionarDispositivo(idCasa, idDiv, d);
            ConsoleUtils.imprimirSucesso("Dispositivo adicionado: " + d);
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    private void removerDispositivo() {
        ConsoleUtils.imprimirTitulo("REMOVER DISPOSITIVO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            Casa c = casaService.getCasaPorId(idCasa);
            c.getDivisoes().forEach(d -> {
                System.out.println("  " + d);
                d.getDispositivos().forEach(disp -> System.out.println("    -> " + disp));
            });
            String idDiv  = ConsoleUtils.lerTexto("  ID da divisão: ");
            String idDisp = ConsoleUtils.lerTexto("  ID do dispositivo: ");
            casaService.removerDispositivo(idCasa, idDiv, idDisp);
            ConsoleUtils.imprimirSucesso("Dispositivo removido.");
        } catch (DomusException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    // ==================== OPERAR DISPOSITIVO ====================

    private void menuOperarDispositivo() {
        ConsoleUtils.imprimirTitulo("OPERAR DISPOSITIVO");
        try {
            listarCasas();
            String idCasa = ConsoleUtils.lerTexto("  ID da casa: ");
            Casa c = casaService.getCasaPorId(idCasa);
            c.getDivisoes().forEach(d -> {
                System.out.println("  " + d);
                d.getDispositivos().forEach(disp -> System.out.println("    -> " + disp + " | " + disp.descricao()));
            });
            String idDiv  = ConsoleUtils.lerTexto("  ID da divisão: ");
            String idDisp = ConsoleUtils.lerTexto("  ID do dispositivo: ");

            Dispositivo d = dispositivoService.encontrarDispositivo(idCasa, idDiv, idDisp);
            System.out.println("\n  Dispositivo: " + d);
            System.out.println("  " + d.descricao());
            System.out.println("\n  1. Ligar");
            System.out.println("  2. Desligar");
            System.out.println("  3. Toggle (inverter estado)");
            if (d instanceof Lampada)       System.out.println("  4. Ajustar intensidade");
            if (d instanceof ArCondicionado) System.out.println("  5. Ajustar temperatura");
            if (d instanceof ColunaDesom)   System.out.println("  6. Ajustar volume");
            if (d instanceof Cortina)       System.out.println("  7. Ajustar abertura");
            if (d instanceof Sensor)        System.out.println("  8. Actualizar valor do sensor");

            int op = ConsoleUtils.lerOpcao("  Operação: ");
            switch (op) {
                case 1 -> dispositivoService.ligar(utilizadorAtualId, idCasa, idDiv, idDisp);
                case 2 -> dispositivoService.desligar(utilizadorAtualId, idCasa, idDiv, idDisp);
                case 3 -> dispositivoService.toggle(utilizadorAtualId, idCasa, idDiv, idDisp);
                case 4 -> {
                    int v = ConsoleUtils.lerInteiro("  Intensidade (0-100): ", 0, 100);
                    dispositivoService.ajustarIntensidadeLampada(utilizadorAtualId, idCasa, idDiv, idDisp, v);
                }
                case 5 -> {
                    double t = ConsoleUtils.lerDouble("  Temperatura (16-30°C): ", 16, 30);
                    dispositivoService.ajustarTemperaturaAC(utilizadorAtualId, idCasa, idDiv, idDisp, t);
                }
                case 6 -> {
                    int v = ConsoleUtils.lerInteiro("  Volume (0-100): ", 0, 100);
                    dispositivoService.ajustarVolumeColuna(utilizadorAtualId, idCasa, idDiv, idDisp, v);
                }
                case 7 -> {
                    int v = ConsoleUtils.lerInteiro("  Abertura (0-100): ", 0, 100);
                    dispositivoService.ajustarAberturaCortina(utilizadorAtualId, idCasa, idDiv, idDisp, v);
                }
                case 8 -> {
                    double v = ConsoleUtils.lerDouble("  Novo valor: ", -999, 99999);
                    dispositivoService.atualizarValorSensor(idCasa, idDiv, idDisp, v);
                }
                default -> ConsoleUtils.imprimirErro("Opção inválida.");
            }
            ConsoleUtils.imprimirSucesso("Operação executada. Estado: " + d);
        } catch (DomusException | IllegalArgumentException e) {
            ConsoleUtils.imprimirErro(e.getMessage());
        }
        ConsoleUtils.pausar();
    }

    // ==================== Auxiliares de escolha ====================

    private TipoDivisao escolherTipoDivisao() {
        TipoDivisao[] valores = TipoDivisao.values();
        System.out.println("\n  Tipos de divisão:");
        for (int i = 0; i < valores.length; i++)
            System.out.printf("  %d. %s%n", i + 1, valores[i]);
        int idx = ConsoleUtils.lerInteiro("  Tipo: ", 1, valores.length);
        return valores[idx - 1];
    }

    private ModoArCondicionado escolherModoAC() {
        ModoArCondicionado[] modos = ModoArCondicionado.values();
        System.out.println("\n  Modos:");
        for (int i = 0; i < modos.length; i++)
            System.out.printf("  %d. %s%n", i + 1, modos[i]);
        int idx = ConsoleUtils.lerInteiro("  Modo: ", 1, modos.length);
        return modos[idx - 1];
    }

    private TipoSensor escolherTipoSensor() {
        TipoSensor[] tipos = TipoSensor.values();
        System.out.println("\n  Tipos de sensor:");
        for (int i = 0; i < tipos.length; i++)
            System.out.printf("  %d. %s%n", i + 1, tipos[i]);
        int idx = ConsoleUtils.lerInteiro("  Tipo: ", 1, tipos.length);
        return tipos[idx - 1];
    }
}
