package pt.domuscontrol.model.automation;

import pt.domuscontrol.model.device.Dispositivo;
import pt.domuscontrol.persistence.BaseDados;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Ação que desliga um dispositivo específico.
 */
public class AcaoDesligar implements Acao {
    private static final long serialVersionUID = 1L;

    private final String casaId;
    private final String divisaoId;
    private final String dispositivoId;

    public AcaoDesligar(String casaId, String divisaoId, String dispositivoId) {
        this.casaId = casaId;
        this.divisaoId = divisaoId;
        this.dispositivoId = dispositivoId;
    }

    @Override
    public void executar(BaseDados bd) {
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        encontrarDispositivo(bd).ifPresent(d -> d.desligar(agora));
    }

    @Override
    public String descricao() {
        return "DESLIGAR dispositivo " + dispositivoId;
    }

    private Optional<Dispositivo> encontrarDispositivo(BaseDados bd) {
        return bd.getCasas().stream()
                .filter(c -> c.getId().equals(casaId))
                .flatMap(c -> c.getDivisoes().stream())
                .filter(div -> div.getId().equals(divisaoId))
                .flatMap(div -> div.getDispositivos().stream())
                .filter(d -> d.getId().equals(dispositivoId))
                .findFirst();
    }
}