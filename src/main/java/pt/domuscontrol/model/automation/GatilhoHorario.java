package pt.domuscontrol.model.automation;

import java.time.LocalTime;

import pt.domuscontrol.persistence.BaseDados;

/**
 * Gatilho que dispara quando a hora atual do relógio simulado corresponde
 * à hora definida (comparação por hora e minuto).
 *
 * Usado para automações do tipo "quando forem 22:00, ligar AC do quarto".
 */
public class GatilhoHorario implements Gatilho {
    private static final long serialVersionUID = 1L;

    private final LocalTime horario;

    public GatilhoHorario(int hora, int minuto) {
        this.horario = LocalTime.of(hora, minuto);
    }

    @Override
    public boolean avaliar(BaseDados bd) {
        LocalTime agora = bd.getRelogio().getDataHoraAtual().toLocalTime();
        return agora.getHour() == horario.getHour()
                && agora.getMinute() == horario.getMinute();
    }

    @Override
    public String descricao() {
        return String.format("hora == %02d:%02d", horario.getHour(), horario.getMinute());
    }
}