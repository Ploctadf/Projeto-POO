package pt.domuscontrol.exception;

public class UtilizadorNaoEncontradoException extends DomusException {
    public UtilizadorNaoEncontradoException(String id) {
        super("Utilizador não encontrado com id: " + id);
    }
}
