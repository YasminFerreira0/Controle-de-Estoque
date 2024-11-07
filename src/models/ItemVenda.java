package models;

public class ItemVenda {
    
    private Produtos produto;
    
    private int quantidadeVendida;

    public ItemVenda(Produtos produto, int quantidadeVendida) {
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public Produtos getProduto() {
        return produto; 
    }
    
    public int getQuantidadeVendida() { 
        return quantidadeVendida; 
    }
}
