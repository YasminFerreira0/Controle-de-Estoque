package models;

public class Produtos {
    private Integer Codigo;
    private String Nome;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private Double preco;
    private Integer quantEstoque;

    public Produtos(Integer codigo, String nome, Categoria categoria, Fornecedor fornecedor, Double preco, Integer quantEstoque) {
        Codigo = codigo;
        Nome = nome;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.preco = preco;
        this.quantEstoque = quantEstoque;
    }

    public Integer getCodigo() {
        return Codigo;
    }

    public void setCodigo(Integer codigo) {
        Codigo = codigo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantEstoque() {
        return quantEstoque;
    }

    public void setQuantEstoque(Integer quantEstoque) {
        this.quantEstoque = quantEstoque;
    }
}
