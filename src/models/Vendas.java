package models;

import java.util.List;
import java.util.Date;

public class Vendas {
    private Date dataVenda;
    private List<ItemVenda> produtosVendidos;
    private Integer quantVendida;
    private Double valorTotal;
    private Cliente cliente;
    private Vendedor vendedor;

    public Vendas(Date dataVenda, List<ItemVenda> produtosVendidos, Integer quantVendida, Double valorTotal, Cliente cliente, Vendedor vendedor) {
        this.dataVenda = dataVenda;
        this.produtosVendidos = produtosVendidos;
        this.quantVendida = quantVendida;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.vendedor = vendedor;
        calcularValor();
    }
    public Vendas(Date dataVenda, List<ItemVenda> produtosVendidos, Cliente cliente, Vendedor vendedor){
        this.dataVenda = dataVenda;
        this.produtosVendidos = produtosVendidos;
        this.cliente = cliente;
        this.vendedor = vendedor;
        calcularValor();
    }

    private void calcularValor(){
        valorTotal = 0d;
        for (ItemVenda item : produtosVendidos)
            valorTotal += item.getProduto().getPreco() * item.getQuantidadeVendida();
    }
    
    public Date getDataVenda() {
        return dataVenda;
    }
    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<ItemVenda> getProdutosVendidos() {
        return produtosVendidos;
    }
    public void setProdutosVendidos(List<ItemVenda> produtosVendidos) {
        this.produtosVendidos = produtosVendidos;
    }

    public Integer getQuantVendida() {
        return quantVendida;
    }
    public void setQuantVendida(Integer quantVendida) {
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
