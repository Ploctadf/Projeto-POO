package pt.domuscontrol.model.device;

public class Cortina extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private int percentagemAbertura; // 0 = fechada, 100 = aberta

    public Cortina(String id, String marca, String modelo, double consumoPorHora) {
        super(id, marca, modelo, consumoPorHora);
        this.percentagemAbertura = 0;
    }

    public int getPercentagemAbertura() { return percentagemAbertura; }

    public void setPercentagemAbertura(int percentagem) {
        if (percentagem < 0 || percentagem > 100)
            throw new IllegalArgumentException("Percentagem deve estar entre 0 e 100");
        this.percentagemAbertura = percentagem;
    }

    public void abrir() { this.percentagemAbertura = 100; }
    public void fechar() { this.percentagemAbertura = 0; }

    @Override
    public String descricao() {
        return String.format("Cortina | Abertura: %d%%", percentagemAbertura);
    }
}
