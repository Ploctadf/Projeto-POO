package pt.domuscontrol.model.automation;

import java.time.LocalDateTime;
import java.util.Optional;

import pt.domuscontrol.model.common.SistemaContext;
import pt.domuscontrol.model.device.Dispositivo;

/**
 * Ação que liga um dispositivo específico.
 */
public class AcaoLigar implements Acao {
    private static final long serialVersionUID = 1L;

    private final String casaId;
    private final String divisaoId;
    private final String dispositivoId;

    public AcaoLigar(String casaId, String divisaoId, String dispositivoId) {
        this.casaId = casaId;
        this.divisaoId = divisaoId;
        this.dispositivoId = dispositivoId;
    }

    @Override
    public void executar(SistemaContext ctx) {
        LocalDateTime agora = ctx.getRelogio().getDataHoraAtual();
        encontrarDispositivo(ctx).ifPresent(d -> d.ligar(agora));
    }

    @Override
    public String descricao() {
        return "LIGAR dispositivo " + dispositivoId;
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