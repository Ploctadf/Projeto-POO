package pt.domuscontrol.model.automation;

import java.util.Optional;

import pt.domuscontrol.model.common.SistemaContext;
import pt.domuscontrol.model.device.Dispositivo;
import pt.domuscontrol.model.device.Sensor;

/**
 * Gatilho que dispara quando o valor de um sensor ultrapassa (ou fica abaixo de) um limiar.
 *
 * Exemplos:
 *   pluviosidade > 5  → fechar cortinas
 *   luminosidade < 100 → ligar luzes
 *   temperatura > 28  → ligar AC
 */
public class GatilhoSensor implements Gatilho {
    private static final long serialVersionUID = 1L;

    public enum Operador { MAIOR_QUE, MENOR_QUE }

    private final String casaId;
    private final String divisaoId;
    private final String sensorId;
    private final Operador operador;
    private final double limiar;

    public GatilhoSensor(String casaId, String divisaoId, String sensorId,
                         Operador operador, double limiar) {
        this.casaId = casaId;
        this.divisaoId = divisaoId;
        this.sensorId = sensorId;
        this.operador = operador;
        this.limiar = limiar;
    }

    @Override
    public boolean avaliar(SistemaContext ctx) {
        Optional<Dispositivo> d = ctx.getCasas().stream()
                .filter(c -> c.getId().equals(casaId))
                .flatMap(c -> c.getDivisoes().stream())
                .filter(div -> div.getId().equals(divisaoId))
                .flatMap(div -> div.getDispositivos().stream())
                .filter(disp -> disp.getId().equals(sensorId))
                .findFirst();

        if (d.isEmpty() || !(d.get() instanceof Sensor sensor)) return false;

        double valor = sensor.getValorAtual();
        return switch (operador) {
            case MAIOR_QUE -> valor > limiar;
            case MENOR_QUE -> valor < limiar;
        };
    }

    @Override
    public String descricao() {
        String op = operador == Operador.MAIOR_QUE ? ">" : "<";
        return "sensor " + sensorId + " " + op + " " + limiar;
    }
}