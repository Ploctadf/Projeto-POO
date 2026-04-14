package pt.domuscontrol.model.device;

public enum TipoSensor {
    TEMPERATURA("Temperatura", "°C"),
    LUMINOSIDADE("Luminosidade", "lux"),
    HUMIDADE("Humidade", "%"),
    MOVIMENTO("Movimento", ""),
    PLUVIOSIDADE("Pluviosidade", "mm/h");

    private final String descricao;
    private final String unidade;

    TipoSensor(String descricao, String unidade) {
        this.descricao = descricao;
        this.unidade = unidade;
    }

    public String getDescricao() { return descricao; }
    public String getUnidade() { return unidade; }

    @Override
    public String toString() {
        return descricao;
    }
}
