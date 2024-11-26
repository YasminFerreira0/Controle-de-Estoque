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

    public Vendas(Date dataVenda, Produtos[] produtosVendidos, Integer[] quantVendida, Cliente cliente, Vendedor vendedor) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (vendedor == null) {
            throw new IllegalArgumentException("Vendedor não pode ser nulo.");
        }

        this.dataVenda = dataVenda;
        this.produtosVendidos = produtosVendidos;
        this.quantVendida = quantVendida != null ? quantVendida : new Integer[produtosVendidos.length];
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.valorTotal = calcularValor();
    }

    public Vendas(Date dataVenda, Produtos[] produtosVendidos, Cliente cliente, Vendedor vendedor) {
        this(dataVenda, produtosVendidos, new Integer[produtosVendidos.length], cliente, vendedor);
        // Inicializar as quantidades como 1 para cada produto
        for (int i = 0; i < quantVendida.length; i++) {
            quantVendida[i] = 1;
        }
    }

    private Double calcularValor() {
        valorTotal = 0d;
        if (produtosVendidos == null || quantVendida == null) {
            return 0d;
        }

        for (int i = 0; i < produtosVendidos.length; i++) {
            int quantidadeDisponivel = Math.min(quantVendida[i], produtosVendidos[i].getQuantEstoque());
            valorTotal += produtosVendidos[i].getPreco() * quantidadeDisponivel;
        }

        return valorTotal;
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
        return calcularValor();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Data da Venda: ").append(dataVenda).append("\n");
        sb.append("Cliente: ").append(cliente != null ? cliente.getNome() : "Não informado").append("\n");
        sb.append("Vendedor: ").append(vendedor != null ? vendedor.getNome() : "Não informado").append("\n");
        sb.append("Produtos Vendidos:\n");

        if (produtosVendidos != null && quantVendida != null) {
            for (int i = 0; i < produtosVendidos.length; i++) {
                sb.append("- ").append(produtosVendidos[i].getNome())
                        .append(" | Quantidade: ").append(quantVendida[i])
                        .append(" | Preço Unitário: ").append(produtosVendidos[i].getPreco())
                        .append("\n");
            }
        } else {
            sb.append("Nenhum produto vendido.\n");
        }

        sb.append("Valor Total: ").append(valorTotal != null ? String.format("%.2f", valorTotal) : "Não calculado").append("\n");
        return sb.toString();
    }
}
