package pt.domuscontrol.exception;

public class DispositivoNaoEncontradoException extends DomusException {
    public DispositivoNaoEncontradoException(String id) {
        super("Dispositivo não encontrado com id: " + id);
    }
}
