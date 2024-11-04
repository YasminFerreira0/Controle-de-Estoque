package models;

public class Estoque {
    private Integer quant;
    private Integer quantMinima;
    //private Produto produto;

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
   
}
