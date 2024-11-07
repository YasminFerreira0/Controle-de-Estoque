package models;

public class Estoque {
    private Integer quant;
    private Integer quantMinima;
    private Produtos produto;

    public Integer getQuant() {
        return quant;
    }
    public void setQuant(Integer quant) {
        this.quant = quant;
    }

    public Integer getQuantMinima() {
        return quantMinima;
    }
    public void setQuantMinima(Integer quantMinima) {
        this.quantMinima = quantMinima;
    }

    public boolean precisaRepor(){
        return quant < quantMinima;
    }
   
}
