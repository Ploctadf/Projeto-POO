package pt.domuscontrol.persistence;

import pt.domuscontrol.model.automation.*;
import pt.domuscontrol.model.common.TipoAcao;
import pt.domuscontrol.model.device.*;
import pt.domuscontrol.model.house.Casa;
import pt.domuscontrol.model.house.Divisao;
import pt.domuscontrol.model.house.TipoDivisao;
import pt.domuscontrol.model.user.RegistoInteracao;
import pt.domuscontrol.model.user.Utilizador;

import java.time.LocalDateTime;

/**
 * Estado de demonstração com utilizadores, casas, dispositivos,
 * automações e escalonamentos pré-configurados.
 */
public class DadosDemo {

    private DadosDemo() {}

    public static BaseDados criar() {
        BaseDados bd = new BaseDados();
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();

        // ===================== UTILIZADORES =====================
        Utilizador admin = new Utilizador(bd.gerarIdUtilizador(), "Ana Silva",  "ana@email.com",  "1234");
        Utilizador joao  = new Utilizador(bd.gerarIdUtilizador(), "João Costa", "joao@email.com", "5678");
        bd.adicionarUtilizador(admin);
        bd.adicionarUtilizador(joao);

        // ===================== CASA 1 =====================
        Casa casa1 = new Casa(bd.gerarIdCasa(), "Casa da Ana", "Rua das Flores, 10, Braga");
        casa1.adicionarProprietario(admin);
        casa1.adicionarUsufrutuario(joao);

        // Sala de estar
        Divisao sala = new Divisao(bd.gerarIdDivisao(), "Sala de Estar", TipoDivisao.SALA_ESTAR);
        Lampada lampSala1    = new Lampada(bd.gerarIdDispositivo(), "Philips", "Hue E27",  9.0, 80, 3000);
        Lampada lampSala2    = new Lampada(bd.gerarIdDispositivo(), "Philips", "Hue Spot", 7.0, 60, 2700);
        ColunaDesom coluna   = new ColunaDesom(bd.gerarIdDispositivo(), "Sonos", "One SL",  6.0, 40);
        Cortina cortinaSala  = new Cortina(bd.gerarIdDispositivo(), "IKEA", "Fytur", 2.0);
        lampSala1.ligar(agora.minusHours(3)); lampSala1.desligar(agora.minusHours(1));
        lampSala2.ligar(agora.minusHours(2)); lampSala2.desligar(agora.minusMinutes(30));
        coluna.ligar(agora.minusHours(2));
        sala.adicionarDispositivo(lampSala1);
        sala.adicionarDispositivo(lampSala2);
        sala.adicionarDispositivo(coluna);
        sala.adicionarDispositivo(cortinaSala);

        // Quarto principal
        Divisao quarto = new Divisao(bd.gerarIdDivisao(), "Quarto Principal", TipoDivisao.QUARTO);
        Lampada lampQuarto   = new Lampada(bd.gerarIdDispositivo(), "IKEA", "Tradfri E14", 5.0, 30, 2700);
        ArCondicionado ac    = new ArCondicionado(bd.gerarIdDispositivo(), "Daikin", "Stylish 9000", 900.0, 21.0, ModoArCondicionado.ARREFECER);
        Cortina cortinaQuarto = new Cortina(bd.gerarIdDispositivo(), "IKEA", "Fytur", 2.0);
        lampQuarto.ligar(agora.minusHours(1)); lampQuarto.desligar(agora.minusMinutes(15));
        ac.ligar(agora.minusHours(5));         ac.desligar(agora.minusHours(1));
        quarto.adicionarDispositivo(lampQuarto);
        quarto.adicionarDispositivo(ac);
        quarto.adicionarDispositivo(cortinaQuarto);

        // Cozinha
        Divisao cozinha = new Divisao(bd.gerarIdDivisao(), "Cozinha", TipoDivisao.COZINHA);
        Lampada lampCoz       = new Lampada(bd.gerarIdDispositivo(), "Osram", "Smart+ E27", 10.0, 100, 4000);
        TomadaInteligente tom = new TomadaInteligente(bd.gerarIdDispositivo(), "TP-Link", "Tapo P115", 2300.0);
        Sensor sensorTemp     = new Sensor(bd.gerarIdDispositivo(), "Xiaomi", "Temp Sensor", 0.5, TipoSensor.TEMPERATURA);
        sensorTemp.setValorAtual(22.5);
        lampCoz.ligar(agora.minusMinutes(45)); lampCoz.desligar(agora.minusMinutes(10));
        cozinha.adicionarDispositivo(lampCoz);
        cozinha.adicionarDispositivo(tom);
        cozinha.adicionarDispositivo(sensorTemp);

        // Garagem
        Divisao garagem = new Divisao(bd.gerarIdDivisao(), "Garagem", TipoDivisao.GARAGEM);
        PortaoGaragem portao = new PortaoGaragem(bd.gerarIdDispositivo(), "Somfy", "RTS 200", 150.0);
        Sensor sensorChuva   = new Sensor(bd.gerarIdDispositivo(), "Aqara", "Rain Sensor", 1.0, TipoSensor.PLUVIOSIDADE);
        sensorChuva.setValorAtual(0.0); // sem chuva inicialmente
        Sensor sensorLuzExt  = new Sensor(bd.gerarIdDispositivo(), "Xiaomi", "Light Ext",  0.3, TipoSensor.LUMINOSIDADE);
        sensorLuzExt.setValorAtual(800.0);
        garagem.adicionarDispositivo(portao);
        garagem.adicionarDispositivo(sensorChuva);
        garagem.adicionarDispositivo(sensorLuzExt);

        casa1.adicionarDivisao(sala);
        casa1.adicionarDivisao(quarto);
        casa1.adicionarDivisao(cozinha);
        casa1.adicionarDivisao(garagem);
        bd.adicionarCasa(casa1);

        // ===================== CASA 2 =====================
        Casa casa2 = new Casa(bd.gerarIdCasa(), "Apartamento do João", "Av. da República, 55, Porto");
        casa2.adicionarProprietario(joao);

        Divisao salaAp = new Divisao(bd.gerarIdDivisao(), "Sala", TipoDivisao.SALA_ESTAR);
        Lampada lampAp1    = new Lampada(bd.gerarIdDispositivo(), "Yeelight", "LED E27",  8.5, 70, 3000);
        ColunaDesom colAp  = new ColunaDesom(bd.gerarIdDispositivo(), "JBL", "Pulse 5",   12.0, 60);
        Sensor sensorLuz   = new Sensor(bd.gerarIdDispositivo(), "Xiaomi", "Light Sensor", 0.3, TipoSensor.LUMINOSIDADE);
        sensorLuz.setValorAtual(450.0);
        lampAp1.ligar(agora.minusHours(4)); lampAp1.desligar(agora.minusHours(2));
        salaAp.adicionarDispositivo(lampAp1);
        salaAp.adicionarDispositivo(colAp);
        salaAp.adicionarDispositivo(sensorLuz);

        Divisao quartoAp = new Divisao(bd.gerarIdDivisao(), "Quarto", TipoDivisao.QUARTO);
        ArCondicionado acAp  = new ArCondicionado(bd.gerarIdDispositivo(), "LG", "DualCool 12000", 1200.0, 23.0, ModoArCondicionado.AQUECER);
        Cortina cortinaAp    = new Cortina(bd.gerarIdDispositivo(), "Somfy", "RTS Ext", 3.0);
        acAp.ligar(agora.minusHours(3)); acAp.desligar(agora.minusHours(1));
        quartoAp.adicionarDispositivo(acAp);
        quartoAp.adicionarDispositivo(cortinaAp);

        casa2.adicionarDivisao(salaAp);
        casa2.adicionarDivisao(quartoAp);
        bd.adicionarCasa(casa2);

        // ===================== HISTÓRICO DE INTERAÇÕES =====================
        LocalDateTime base = agora.minusDays(7);
        for (int dia = 0; dia < 7; dia++) {
            bd.adicionarInteracao(new RegistoInteracao(admin.getId(), casa1.getId(),
                    sala.getId(), lampSala1.getId(), TipoAcao.LIGAR,    base.plusDays(dia).withHour(20)));
            bd.adicionarInteracao(new RegistoInteracao(admin.getId(), casa1.getId(),
                    sala.getId(), lampSala1.getId(), TipoAcao.DESLIGAR, base.plusDays(dia).withHour(23)));
            bd.adicionarInteracao(new RegistoInteracao(admin.getId(), casa1.getId(),
                    quarto.getId(), ac.getId(), TipoAcao.LIGAR,         base.plusDays(dia).withHour(22)));
        }

        // ===================== AUTOMAÇÕES (mínimo 2) =====================

        // Automação 1: SE pluviosidade > 5 → fechar cortina da sala
        // (Para testar: atualizar o sensor de chuva para > 5 e depois avaliar automações)
        Automacao aut1 = new Automacao(
                bd.gerarIdAutomacao(),
                "Fechar cortina se chover",
                casa1.getId(),
                new GatilhoSensor(casa1.getId(), garagem.getId(), sensorChuva.getId(),
                        GatilhoSensor.Operador.MAIOR_QUE, 5.0),
                new AcaoAjustarCortina(casa1.getId(), sala.getId(), cortinaSala.getId(), 0)
        );
        bd.adicionarAutomacao(aut1);

        // Automação 2: SE luminosidade exterior < 200 → ligar lâmpada da sala
        // (Para testar: atualizar o sensor de luz para < 200 e avaliar automações)
        Automacao aut2 = new Automacao(
                bd.gerarIdAutomacao(),
                "Ligar luz sala quando escurece",
                casa1.getId(),
                new GatilhoSensor(casa1.getId(), garagem.getId(), sensorLuzExt.getId(),
                        GatilhoSensor.Operador.MENOR_QUE, 200.0),
                new AcaoLigar(casa1.getId(), sala.getId(), lampSala1.getId())
        );
        bd.adicionarAutomacao(aut2);

        // ===================== ESCALONAMENTOS (mínimo 4) =====================

        // Escalonamento 1: 07:30 → abrir cortinas do quarto
        Escalonamento esc1 = new Escalonamento(
                bd.gerarIdEscalonamento(),
                "Acordar - abrir cortinas quarto",
                casa1.getId(), 7, 30,
                new AcaoAjustarCortina(casa1.getId(), quarto.getId(), cortinaQuarto.getId(), 100)
        );
        bd.adicionarEscalonamento(esc1);

        // Escalonamento 2: 20:00 → ligar lâmpada da sala
        Escalonamento esc2 = new Escalonamento(
                bd.gerarIdEscalonamento(),
                "Noite - ligar luz sala",
                casa1.getId(), 20, 0,
                new AcaoLigar(casa1.getId(), sala.getId(), lampSala1.getId())
        );
        bd.adicionarEscalonamento(esc2);

        // Escalonamento 3: 23:00 → desligar lâmpada da sala
        Escalonamento esc3 = new Escalonamento(
                bd.gerarIdEscalonamento(),
                "Dormir - desligar luz sala",
                casa1.getId(), 23, 0,
                new AcaoDesligar(casa1.getId(), sala.getId(), lampSala1.getId())
        );
        bd.adicionarEscalonamento(esc3);

        // Escalonamento 4: 22:00 → ligar AC do quarto
        Escalonamento esc4 = new Escalonamento(
                bd.gerarIdEscalonamento(),
                "Dormir - ligar AC quarto",
                casa1.getId(), 22, 0,
                new AcaoLigar(casa1.getId(), quarto.getId(), ac.getId())
        );
        bd.adicionarEscalonamento(esc4);

        return bd;
    }
}