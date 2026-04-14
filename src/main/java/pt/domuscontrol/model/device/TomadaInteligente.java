package pt.domuscontrol.model.device;

public class TomadaInteligente extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private double consumoAtual; // W (potência instantânea)

    public TomadaInteligente(String id, String marca, String modelo, double consumoPorHora) {
        super(id, marca, modelo, consumoPorHora);
        this.consumoAtual = 0.0;
    }

    public double getConsumoAtual() { return consumoAtual; }

    public void setConsumoAtual(double consumoAtual) {
        if (consumoAtual < 0) throw new IllegalArgumentException("Consumo não pode ser negativo");
        this.consumoAtual = consumoAtual;
    }

    @Override
    public String descricao() {
        return String.format("Tomada Inteligente | Consumo actual: %.1f W", consumoAtual);
    }
}
