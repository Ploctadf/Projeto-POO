package pt.domuscontrol.model.device;

public class ColunaDesom extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private int volume; // 0-100

    public ColunaDesom(String id, String marca, String modelo, double consumoPorHora, int volume) {
        super(id, marca, modelo, consumoPorHora);
        setVolume(volume);
    }

    public int getVolume() { return volume; }

    public void setVolume(int volume) {
        if (volume < 0 || volume > 100)
            throw new IllegalArgumentException("Volume deve estar entre 0 e 100");
        this.volume = volume;
    }

    @Override
    public String descricao() {
        return String.format("Coluna de Som | Volume: %d%%", volume);
    }
}
