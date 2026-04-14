package pt.domuscontrol.model.device;

public class Lampada extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private int intensidade;       // 0-100 %
    private int temperaturaCor;    // 2700-4000 K

    public Lampada(String id, String marca, String modelo, double consumoPorHora,
                   int intensidade, int temperaturaCor) {
        super(id, marca, modelo, consumoPorHora);
        setIntensidade(intensidade);
        setTemperaturaCor(temperaturaCor);
    }

    public int getIntensidade() { return intensidade; }
    public int getTemperaturaCor() { return temperaturaCor; }

    public void setIntensidade(int intensidade) {
        if (intensidade < 0 || intensidade > 100)
            throw new IllegalArgumentException("Intensidade deve estar entre 0 e 100");
        this.intensidade = intensidade;
    }

    public void setTemperaturaCor(int temperaturaCor) {
        if (temperaturaCor < 2700 || temperaturaCor > 4000)
            throw new IllegalArgumentException("Temperatura de cor deve estar entre 2700K e 4000K");
        this.temperaturaCor = temperaturaCor;
    }

    @Override
    public String descricao() {
        return String.format("Lâmpada | Intensidade: %d%% | Cor: %dK", intensidade, temperaturaCor);
    }
}
