package controllers;

import models.Estoque;
import models.Produtos;
import persistence.EstoqueDAO;

import javax.swing.*;
import java.util.List;

public class EstoqueController {

    private static EstoqueDAO estoqueDAO = new EstoqueDAO();

    public Produtos buscarProdutoPorCodigo(String codigo) {
        System.out.println("Iniciando busca de produto com código: " + codigo);
        List<Estoque> estoques = estoqueDAO.listarTodos();

        for (Estoque estoque : estoques) {
            if (estoque.getProduto().getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("Produto encontrado: " + estoque.getProduto().getNome());
                return estoque.getProduto();
            }
        }
        System.out.println("Produto não encontrado no estoque. Código: " + codigo);
        return null;
    }

    public static Estoque buscarEstoquePorCodigo(String codigo) {
        System.out.println("Iniciando busca de estoque com código do produto: " + codigo);
        for (Estoque estoque : estoqueDAO.listarTodos()) {
            if (estoque.getProduto().getCodigo().equals(codigo)) {
                System.out.println("Estoque encontrado para o produto: " + estoque.getProduto().getNome());
                return estoque;
            }
        }
        System.out.println("Estoque não encontrado para o produto com código: " + codigo);
        return null;
    }

    public static boolean verificarEstoque(Produtos produto, int quantidade) {
        Estoque estoque = estoqueDAO.buscarEstoquePorProduto(produto);
        return estoque != null && estoque.getQuant() >= quantidade;
    }

    public static void baixarEstoque(String codigoProduto, int quantidade) {
        Estoque estoque = buscarEstoquePorCodigo(codigoProduto);
        if (estoque != null) {
            if (estoque.getQuant() >= quantidade) {
                estoque.setQuant(estoque.getQuant() - quantidade);
                estoqueDAO.atualizarEstoque(estoque);

                if (estoque.precisaRepor()) {
                    JOptionPane.showMessageDialog(null,
                            "Atenção! Estoque do produto " + estoque.getProduto().getNome() + " está abaixo da quantidade mínima!",
                            "Aviso de Reposição", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Estoque insuficiente para o produto: " + estoque.getProduto().getNome(),
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Produto com código " + codigoProduto + " não encontrado no estoque.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cadastrarProdutoNoEstoque(Produtos produto, int quantidade, int quantidadeMinima) {
        Estoque estoque = new Estoque(quantidade, quantidadeMinima, produto);
        if (estoqueDAO.adicionarEstoque(estoque)) {
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso no estoque!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Produto já existente no estoque. Use a funcionalidade de atualização.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void atualizarEstoque(String codigoProduto, int novaQuantidade) {
        Estoque estoque = buscarEstoquePorCodigo(codigoProduto);
        if (estoque != null) {
            estoque.setQuant(novaQuantidade);
            estoqueDAO.atualizarEstoque(estoque);
            JOptionPane.showMessageDialog(null, "Estoque atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado no estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
