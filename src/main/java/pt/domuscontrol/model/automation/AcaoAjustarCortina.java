package pt.domuscontrol.model.automation;

import pt.domuscontrol.model.common.SistemaContext;
import pt.domuscontrol.model.device.Cortina;
import pt.domuscontrol.model.device.Dispositivo;

import java.util.Optional;

/**
 * Ação que ajusta a abertura de uma cortina para uma percentagem específica.
 */
public class AcaoAjustarCortina implements Acao {
    private static final long serialVersionUID = 1L;

    private final String casaId;
    private final String divisaoId;
    private final String dispositivoId;
    private final int percentagem; // 0 = fechada, 100 = aberta

    public AcaoAjustarCortina(String casaId, String divisaoId, String dispositivoId, int percentagem) {
        if (percentagem < 0 || percentagem > 100)
            throw new IllegalArgumentException("Percentagem deve estar entre 0 e 100");
        this.casaId = casaId;
        this.divisaoId = divisaoId;
        this.dispositivoId = dispositivoId;
        this.percentagem = percentagem;
    }

    @Override
    public void executar(SistemaContext ctx) {
        encontrarDispositivo(ctx).ifPresent(d -> {
            if (d instanceof Cortina c) c.setPercentagemAbertura(percentagem);
        });
    }

    @Override
    public String descricao() {
        return "AJUSTAR cortina " + dispositivoId + " para " + percentagem + "%";
    }

    private Optional<Dispositivo> encontrarDispositivo(SistemaContext ctx) {
        return ctx.getCasas().stream()
                .filter(c -> c.getId().equals(casaId))
                .flatMap(c -> c.getDivisoes().stream())
                .filter(div -> div.getId().equals(divisaoId))
                .flatMap(div -> div.getDispositivos().stream())
                .filter(d -> d.getId().equals(dispositivoId))
                .findFirst();
    }
}