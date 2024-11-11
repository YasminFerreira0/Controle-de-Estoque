package models;

import java.util.Objects;

public class Produtos {
    private Integer codigo;
    private String nome;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private Double preco;
    private Integer quantEstoque;
    private Integer quantMinima;

    public Produtos(Integer codigo, String nome, Categoria categoria, Fornecedor fornecedor, Double preco, Integer quantEstoque, Integer quantMinima) {
        this.codigo = codigo;
        this.nome = nome;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.preco = preco;
        this.quantEstoque = quantEstoque;
        this.quantMinima = quantMinima;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Integer getQuantMinima() {
        return quantMinima;
    }

    public void setQuantMinima(Integer quantMinima) {
        this.quantMinima = quantMinima;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produtos produto = (Produtos) o;
        return Objects.equals(codigo, produto.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + codigo +
                ", nome='" + nome + '\'' +
                ", preco=" + preco +
                '}';

    }
}
