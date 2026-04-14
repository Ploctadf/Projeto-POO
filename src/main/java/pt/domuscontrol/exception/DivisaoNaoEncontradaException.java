package pt.domuscontrol.exception;

public class DivisaoNaoEncontradaException extends DomusException {
    public DivisaoNaoEncontradaException(String id) {
        super("Divisão não encontrada com id: " + id);
    }
}
