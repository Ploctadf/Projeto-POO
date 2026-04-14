package pt.domuscontrol.model.device;

public enum ModoArCondicionado {
    ARREFECER("Arrefecer"),
    AQUECER("Aquecer"),
    VENTILADOR("Ventilador"),
    AUTOMATICO("Automático");

    private final String descricao;

    ModoArCondicionado(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
