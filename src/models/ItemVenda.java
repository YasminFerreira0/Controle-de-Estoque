package models;

public class ItemVenda {
    
    //private Produto produto;
    
    private int quantidadeVendida;

    public ItemVenda(/*Produto produto,*/ int quantidadeVendida) {
        //this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    /*public Produto getProduto() { 
        return produto; 
    }*/
    
    public int getQuantidadeVendida() { 
        return quantidadeVendida; 
    }
}
