package models;

import java.util.Date;

public class Vendas {
    private Date dataVenda;
    private Produtos[] produtosVendidos;
    private Integer[] quantVendida;
    private Double valorTotal;
    private Cliente cliente;
    private Vendedor vendedor;

    public Vendas(Date dataVenda, Produtos[] produtosVendidos, Integer[] quantVendida, Double valorTotal, Cliente cliente, Vendedor vendedor) {
        this.dataVenda = dataVenda;
        this.produtosVendidos = produtosVendidos;
        this.quantVendida = quantVendida;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.vendedor = vendedor;
    }
    public Vendas(Date dataVenda, Produtos[] produtosVendidos, Cliente cliente, Vendedor vendedor){
        this.dataVenda = dataVenda;
        this.produtosVendidos = produtosVendidos;
        this.cliente = cliente;
        this.vendedor = vendedor;
        calcularValor();
    }

    private void calcularValor(){
        valorTotal = 0d;
        for (int i =0; i < produtosVendidos.length; i++){
            valorTotal += produtosVendidos[i].getPreco() * quantVendida[i];
        }
    }
    
    public Date getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Produtos[] getProdutosVendidos() {
        return produtosVendidos;
    }
    public void setProdutosVendidos(Produtos[] produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    public Integer[] getQuantVendida() {
        return quantVendida;
    }
    public void setQuantVendida(Integer[] quantVendida) {
        this.quantVendida = quantVendida;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

}
