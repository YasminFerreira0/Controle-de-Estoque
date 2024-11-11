package models;

public class Estoque {
    private Integer quant;
    private Integer quantMinima;
    private Produtos produto;

    public Estoque(Integer quant, Integer quantMinima, Produtos produto) {
        this.quant = quant;
        this.quantMinima = quantMinima;
        this.produto = produto;
    }

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

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public boolean precisaRepor() {
        return quant < quantMinima;
    }

    public void atualizarEstoque(Integer quantidadeVendida) {
        if (quant >= quantidadeVendida) {
            this.quant -= quantidadeVendida;
        } else {
            System.out.println("Estoque insuficiente para o produto: " + produto.getNome());
        }
    }

}
