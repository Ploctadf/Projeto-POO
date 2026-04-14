package pt.domuscontrol.model.device;

public class Sensor extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private final TipoSensor tipo;
    private double valorAtual;

    public Sensor(String id, String marca, String modelo, double consumoPorHora, TipoSensor tipo) {
        super(id, marca, modelo, consumoPorHora);
        if (tipo == null) throw new IllegalArgumentException("Tipo de sensor não pode ser nulo");
        this.tipo = tipo;
        this.valorAtual = 0.0;
    }

    public TipoSensor getTipo() { return tipo; }
    public double getValorAtual() { return valorAtual; }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    @Override
    public String descricao() {
        String valor = tipo == TipoSensor.MOVIMENTO
                ? (valorAtual > 0 ? "Detectado" : "Sem movimento")
                : String.format("%.1f %s", valorAtual, tipo.getUnidade());
        return String.format("Sensor de %s | Valor: %s", tipo.getDescricao(), valor);
    }
}
