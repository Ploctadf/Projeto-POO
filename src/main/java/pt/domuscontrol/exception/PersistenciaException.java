package pt.domuscontrol.exception;

public class PersistenciaException extends DomusException {
    public PersistenciaException(String mensagem) {
        super("Erro de persistência: " + mensagem);
    }
}
