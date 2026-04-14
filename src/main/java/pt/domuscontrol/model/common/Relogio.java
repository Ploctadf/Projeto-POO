package pt.domuscontrol.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Relogio implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private LocalDateTime dataHoraAtual;

    public Relogio() {
        this.dataHoraAtual = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraAtual() {
        return dataHoraAtual;
    }

    public void avancarMinutos(long minutos) {
        dataHoraAtual = dataHoraAtual.plusMinutes(minutos);
    }

    public void avancarHoras(long horas) {
        dataHoraAtual = dataHoraAtual.plusHours(horas);
    }

    public void avancarDias(long dias) {
        dataHoraAtual = dataHoraAtual.plusDays(dias);
    }

    @Override
    public String toString() {
        return dataHoraAtual.format(FORMATO);
    }
}
