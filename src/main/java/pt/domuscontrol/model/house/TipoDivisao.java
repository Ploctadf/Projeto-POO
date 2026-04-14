package pt.domuscontrol.model.house;

public enum TipoDivisao {
    SALA_ESTAR("Sala de Estar"),
    SALA_JANTAR("Sala de Jantar"),
    COZINHA("Cozinha"),
    QUARTO("Quarto"),
    CASA_BANHO("Casa de Banho"),
    GARAGEM("Garagem"),
    ESCRITORIO("Escritório"),
    JARDIM("Jardim"),
    VARANDA("Varanda"),
    CORREDOR("Corredor");

    private final String descricao;

    TipoDivisao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
