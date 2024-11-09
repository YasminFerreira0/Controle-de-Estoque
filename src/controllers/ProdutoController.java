package controllers;
import models.Categoria;
import models.Fornecedor;
import models.Produtos;
import persistence.ProdutoDAO;

import javax.swing.*;
import java.util.List;

//import persistence de produto

public class ProdutoController {

    //Instancia de persistence de produto

    public boolean adicionarProduto(Integer codigo, String nome, Categoria categoria, Fornecedor fornecedor, Double preco, Integer quantEstoque) {
        Produtos novoProduto = new Produtos(codigo, nome, categoria, fornecedor, preco, quantEstoque);
        return ProdutoDAO.adicionarProduto(novoProduto);
    }

    public boolean editarProduto(Integer codigo, String nome, Categoria categoria, Fornecedor fornecedor, Double preco, Integer quantEstoque) {
        Produtos produtoEditado = new Produtos(codigo, nome, categoria, fornecedor, preco, quantEstoque);
        return ProdutoDAO.atualizarProduto(produtoEditado);
    }
    public boolean excluirProduto(Integer codigo) {
        return ProdutoDAO.removerProduto(codigo);
    }

    public Produtos buscarProdutoPorCodigo(Integer codigo) {
        return ProdutoDAO.buscarProdutoPorCodigo(codigo);
    }

    public List<Produtos> listarTodosProdutos() {
        return ProdutoDAO.listarTodos();
    }
}
