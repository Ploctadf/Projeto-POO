package pt.domuscontrol.service;

import pt.domuscontrol.exception.CasaNaoEncontradaException;
import pt.domuscontrol.exception.DispositivoNaoEncontradoException;
import pt.domuscontrol.exception.DivisaoNaoEncontradaException;
import pt.domuscontrol.model.common.TipoAcao;
import pt.domuscontrol.model.device.*;
import pt.domuscontrol.model.house.Divisao;
import pt.domuscontrol.model.user.RegistoInteracao;
import pt.domuscontrol.persistence.BaseDados;

import java.time.LocalDateTime;

public class DispositivoService {

    private final BaseDados bd;
    private final CasaService casaService;

    public DispositivoService(BaseDados bd, CasaService casaService) {
        this.bd = bd;
        this.casaService = casaService;
    }

    // ==================== OPERAÇÕES BÁSICAS ====================

    public void ligar(String utilizadorId, String casaId, String divisaoId, String dispositivoId)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        d.ligar(agora);
        registar(utilizadorId, casaId, divisaoId, dispositivoId, TipoAcao.LIGAR, agora);
    }

    public void desligar(String utilizadorId, String casaId, String divisaoId, String dispositivoId)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        d.desligar(agora);
        registar(utilizadorId, casaId, divisaoId, dispositivoId, TipoAcao.DESLIGAR, agora);
    }

    public void toggle(String utilizadorId, String casaId, String divisaoId, String dispositivoId)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        TipoAcao acao = d.isLigado() ? TipoAcao.DESLIGAR : TipoAcao.LIGAR;
        d.toggle(agora);
        registar(utilizadorId, casaId, divisaoId, dispositivoId, acao, agora);
    }

    // ==================== AJUSTES ESPECÍFICOS ====================

    public void ajustarIntensidadeLampada(String utilizadorId, String casaId, String divisaoId,
                                          String dispositivoId, int intensidade)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        if (!(d instanceof Lampada))
            throw new IllegalArgumentException("Dispositivo " + dispositivoId + " não é uma lâmpada");
        ((Lampada) d).setIntensidade(intensidade);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        registar(utilizadorId, casaId, divisaoId, dispositivoId, TipoAcao.AJUSTAR_INTENSIDADE, agora);
    }

    public void ajustarTemperaturaAC(String utilizadorId, String casaId, String divisaoId,
                                     String dispositivoId, double temperatura)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        if (!(d instanceof ArCondicionado))
            throw new IllegalArgumentException("Dispositivo " + dispositivoId + " não é um ar condicionado");
        ((ArCondicionado) d).setTemperaturaDefinida(temperatura);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        registar(utilizadorId, casaId, divisaoId, dispositivoId, TipoAcao.AJUSTAR_TEMPERATURA, agora);
    }

    public void ajustarVolumeColuna(String utilizadorId, String casaId, String divisaoId,
                                    String dispositivoId, int volume)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        if (!(d instanceof ColunaDesom))
            throw new IllegalArgumentException("Dispositivo " + dispositivoId + " não é uma coluna de som");
        ((ColunaDesom) d).setVolume(volume);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        registar(utilizadorId, casaId, divisaoId, dispositivoId, TipoAcao.AJUSTAR_VOLUME, agora);
    }

    public void ajustarAberturaCortina(String utilizadorId, String casaId, String divisaoId,
                                       String dispositivoId, int percentagem)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        if (!(d instanceof Cortina))
            throw new IllegalArgumentException("Dispositivo " + dispositivoId + " não é uma cortina");
        ((Cortina) d).setPercentagemAbertura(percentagem);
        LocalDateTime agora = bd.getRelogio().getDataHoraAtual();
        registar(utilizadorId, casaId, divisaoId, dispositivoId, TipoAcao.AJUSTAR_ABERTURA, agora);
    }

    public void atualizarValorSensor(String casaId, String divisaoId,
                                     String dispositivoId, double valor)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Dispositivo d = encontrarDispositivo(casaId, divisaoId, dispositivoId);
        if (!(d instanceof Sensor))
            throw new IllegalArgumentException("Dispositivo " + dispositivoId + " não é um sensor");
        ((Sensor) d).setValorAtual(valor);
    }

    // ==================== Auxiliares ====================

    public Dispositivo encontrarDispositivo(String casaId, String divisaoId, String dispositivoId)
            throws CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException {
        Divisao div = casaService.encontrarDivisao(casaId, divisaoId);
        return div.getDispositivos().stream()
                .filter(d -> d.getId().equals(dispositivoId))
                .findFirst()
                .orElseThrow(() -> new DispositivoNaoEncontradoException(dispositivoId));
    }

    private void registar(String utilizadorId, String casaId, String divisaoId,
                           String dispositivoId, TipoAcao acao, LocalDateTime agora) {
        bd.adicionarInteracao(new RegistoInteracao(utilizadorId, casaId, divisaoId, dispositivoId, acao, agora));
    }
}
