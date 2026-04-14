package pt.domuscontrol.exception;

public class CasaNaoEncontradaException extends DomusException {
    public CasaNaoEncontradaException(String id) {
        super("Casa não encontrada com id: " + id);
    }
}
