package pt.domuscontrol.model.device;

public class ArCondicionado extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private double temperaturaDefinida; // °C
    private ModoArCondicionado modo;

    public ArCondicionado(String id, String marca, String modelo, double consumoPorHora,
                          double temperaturaDefinida, ModoArCondicionado modo) {
        super(id, marca, modelo, consumoPorHora);
        setTemperaturaDefinida(temperaturaDefinida);
        this.modo = modo != null ? modo : ModoArCondicionado.AUTOMATICO;
    }

    public double getTemperaturaDefinida() { return temperaturaDefinida; }
    public ModoArCondicionado getModo() { return modo; }

    public void setTemperaturaDefinida(double temperatura) {
        if (temperatura < 16 || temperatura > 30)
            throw new IllegalArgumentException("Temperatura deve estar entre 16°C e 30°C");
        this.temperaturaDefinida = temperatura;
    }

    public void setModo(ModoArCondicionado modo) {
        if (modo == null) throw new IllegalArgumentException("Modo não pode ser nulo");
        this.modo = modo;
    }

    @Override
    public String descricao() {
        return String.format("Ar Condicionado | Temp: %.1f°C | Modo: %s", temperaturaDefinida, modo);
    }
}
