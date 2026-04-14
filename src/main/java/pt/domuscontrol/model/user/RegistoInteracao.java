package pt.domuscontrol.model.user;

import pt.domuscontrol.model.common.TipoAcao;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistoInteracao implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final String utilizadorId;
    private final String casaId;
    private final String divisaoId;
    private final String dispositivoId;
    private final TipoAcao acao;
    private final LocalDateTime timestamp;

    public RegistoInteracao(String utilizadorId, String casaId, String divisaoId,
                            String dispositivoId, TipoAcao acao, LocalDateTime timestamp) {
        this.utilizadorId = utilizadorId;
        this.casaId = casaId;
        this.divisaoId = divisaoId;
        this.dispositivoId = dispositivoId;
        this.acao = acao;
        this.timestamp = timestamp;
    }

    public String getUtilizadorId() { return utilizadorId; }
    public String getCasaId() { return casaId; }
    public String getDivisaoId() { return divisaoId; }
    public String getDispositivoId() { return dispositivoId; }
    public TipoAcao getAcao() { return acao; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] Utilizador %s -> %s em dispositivo %s (casa %s)",
                timestamp.format(FORMATO), utilizadorId, acao, dispositivoId, casaId);
    }
}
