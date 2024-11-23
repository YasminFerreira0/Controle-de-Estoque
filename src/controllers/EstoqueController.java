package controllers;

import models.Estoque;
import models.Produtos;
import persistence.EstoqueDAO;

import javax.swing.*;

public class EstoqueController {



    //********************************************************************************

    //falata fazer
    //Visualização do estado do estoque, com filtros por categorias e quantidades.

    //********************************************************************************




    private static EstoqueDAO estoqueDAO = new EstoqueDAO();

    public static Produtos buscarProdutoPorCodigo(String codigo) {
        for (Estoque estoque : estoqueDAO.listarTodos()) {
            if (estoque.getProduto().getCodigo().equals(codigo)) {
                return estoque.getProduto();
            }
        }
        return null;
    }

    public static Estoque buscarEstoquePorCodigo(String codigo) {
        for (Estoque estoque : estoqueDAO.listarTodos()) {
            if (estoque.getProduto().getCodigo().equals(codigo)) {
                return estoque;
            }
        }
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
                estoque.atualizarEstoque(quantidade);
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

    public void listarEstoque() {
        StringBuilder listaEstoque = new StringBuilder("Estoque Atual:\n");
        for (Estoque estoque : estoqueDAO.listarTodos()) {
            listaEstoque.append("Produto: ").append(estoque.getProduto().getNome())
                    .append("\nCódigo: ").append(estoque.getProduto().getCodigo())
                    .append("\nQuantidade Atual: ").append(estoque.getQuant())
                    .append("\nQuantidade Mínima: ").append(estoque.getQuantMinima())
                    .append("\nNecessita Reposição: ").append(estoque.precisaRepor() ? "Sim" : "Não")
                    .append("\n--------------------------------\n");
        }
        JOptionPane.showMessageDialog(null, listaEstoque.toString(), "Estoque Atual", JOptionPane.INFORMATION_MESSAGE);
    }
}
