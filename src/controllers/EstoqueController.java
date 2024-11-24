package controllers;

import models.Estoque;
import models.Produtos;
import models.Categoria;
import persistence.EstoqueDAO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
    //******************************************************************************************
    public void visualizarEstoque() {
        // Pergunta ao usuário qual filtro ele quer usar
        String[] opcoesFiltros = {"Código", "Categoria", "Quantidade Mínima", "Nenhum Filtro"};
        String filtroEscolhido = (String) JOptionPane.showInputDialog(
                null,
                "Escolha o filtro para visualizar o estoque:",
                "Seleção de Filtro",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcoesFiltros,
                opcoesFiltros[0]  // Filtro padrão é 'Código'
        );

        if (filtroEscolhido == null || filtroEscolhido.equals("Nenhum Filtro")) {
            // Se o usuário escolher 'Nenhum Filtro', exibe todos os produtos
            listarEstoqueSemFiltro();
        } else {
            // Caso contrário, pergunta o valor para o filtro escolhido
            String valorFiltro = JOptionPane.showInputDialog("Digite o valor para filtrar por " + filtroEscolhido + ":");

            if (valorFiltro == null || valorFiltro.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Filtro inválido ou cancelado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Chama o metodo para listar o estoque filtrado com base na escolha
            switch (filtroEscolhido) {
                case "Código":
                    listarEstoquePorCodigo(Integer.parseInt(valorFiltro));
                    break;
                case "Categoria":
                    listarEstoquePorCategoria(valorFiltro);
                    break;
                case "Quantidade Mínima":
                    listarEstoquePorQuantidadeMinima(Integer.parseInt(valorFiltro));
                    break;
            }
        }
    }

    // Metodo para listar o estoque sem filtros
    private void listarEstoqueSemFiltro() {
        StringBuilder listaEstoque = new StringBuilder("Estoque Atual:\n");

        for (Estoque estoque : estoqueDAO.listarTodos()) {
            Produtos produto = estoque.getProduto();
            listaEstoque.append("Produto: ").append(produto.getNome())
                    .append("\nCódigo: ").append(produto.getCodigo())
                    .append("\nCategoria: ").append(produto.getCategoria())
                    .append("\nQuantidade Atual: ").append(estoque.getQuant())
                    .append("\nQuantidade Mínima: ").append(estoque.getQuantMinima())
                    .append("\nNecessita Reposição: ").append(estoque.precisaRepor() ? "Sim" : "Não")
                    .append("\n--------------------------------\n");
        }

        if (listaEstoque.length() > 20) {
            JOptionPane.showMessageDialog(null, listaEstoque.toString(), "Estoque Atual", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Estoque vazio ou sem produtos.", "Estoque Atual", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Metodo para listar o estoque filtrado por código
    private void listarEstoquePorCodigo(int codigo) {
        StringBuilder listaEstoque = new StringBuilder("Estoque Filtrado por Código " + codigo + ":\n");

        for (Estoque estoque : estoqueDAO.listarTodos()) {
            Produtos produto = estoque.getProduto();
            if (produto.getCodigo().equals(codigo)) {
                listaEstoque.append("Produto: ").append(produto.getNome())
                        .append("\nCódigo: ").append(produto.getCodigo())
                        .append("\nCategoria: ").append(produto.getCategoria())
                        .append("\nQuantidade Atual: ").append(estoque.getQuant())
                        .append("\nQuantidade Mínima: ").append(estoque.getQuantMinima())
                        .append("\nNecessita Reposição: ").append(estoque.precisaRepor() ? "Sim" : "Não")
                        .append("\n--------------------------------\n");
            }
        }

        if (listaEstoque.length() > 20) {
            JOptionPane.showMessageDialog(null, listaEstoque.toString(), "Estoque Filtrado por Código", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o código " + codigo, "Resultado do Filtro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Metodo para listar o estoque filtrado por categoria
    private void listarEstoquePorCategoria(String categoria) {
        StringBuilder listaEstoque = new StringBuilder("Estoque Filtrado por Categoria " + categoria + ":\n");

        for (Estoque estoque : estoqueDAO.listarTodos()) {
            Produtos produto = estoque.getProduto();
            if (produto.getCategoria().name().equalsIgnoreCase(categoria)) {
                listaEstoque.append("Produto: ").append(produto.getNome())
                        .append("\nCódigo: ").append(produto.getCodigo())
                        .append("\nCategoria: ").append(produto.getCategoria())
                        .append("\nQuantidade Atual: ").append(estoque.getQuant())
                        .append("\nQuantidade Mínima: ").append(estoque.getQuantMinima())
                        .append("\nNecessita Reposição: ").append(estoque.precisaRepor() ? "Sim" : "Não")
                        .append("\n--------------------------------\n");
            }
        }

        if (listaEstoque.length() > 20) {
            JOptionPane.showMessageDialog(null, listaEstoque.toString(), "Estoque Filtrado por Categoria", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado para a categoria " + categoria, "Resultado do Filtro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Metodo para listar o estoque filtrado por quantidade mínima
    private void listarEstoquePorQuantidadeMinima(int quantidadeMinima) {
        StringBuilder listaEstoque = new StringBuilder("Estoque Filtrado por Quantidade Mínima " + quantidadeMinima + ":\n");

        for (Estoque estoque : estoqueDAO.listarTodos()) {
            Produtos produto = estoque.getProduto();
            if (estoque.getQuant() < quantidadeMinima) {
                listaEstoque.append("Produto: ").append(produto.getNome())
                        .append("\nCódigo: ").append(produto.getCodigo())
                        .append("\nCategoria: ").append(produto.getCategoria())
                        .append("\nQuantidade Atual: ").append(estoque.getQuant())
                        .append("\nQuantidade Mínima: ").append(estoque.getQuantMinima())
                        .append("\nNecessita Reposição: ").append(estoque.precisaRepor() ? "Sim" : "Não")
                        .append("\n--------------------------------\n");
            }
        }

        if (listaEstoque.length() > 20) {
            JOptionPane.showMessageDialog(null, listaEstoque.toString(), "Estoque Filtrado por Quantidade Mínima", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto com quantidade menor que " + quantidadeMinima, "Resultado do Filtro", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

