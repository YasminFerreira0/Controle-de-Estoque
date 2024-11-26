package controllers;

import models.Categoria;
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
            if (estoque.getProduto().getCodigo().equalsIgnoreCase(codigo)) {
                System.out.println("Estoque encontrado para o produto: " + estoque.getProduto().getNome());
                return estoque;
            }
        }
        System.out.println("Estoque não encontrado para o produto com código: " + codigo);
        return null;
    }

    public boolean verificarEstoque(Produtos produto, int quantidade) {
        Estoque estoque = estoqueDAO.buscarEstoquePorProduto(produto);
        return estoque != null && estoque.getQuant() >= quantidade;
    }

    public void baixarEstoque(String codigoProduto, int quantidade) {
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
            //JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso no estoque!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Produto já existente no estoque. Use a funcionalidade de atualização.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*public void atualizarEstoque(String codigoProduto, int novaQuantidade) {
        Estoque estoque = buscarEstoquePorCodigo(codigoProduto);
        if (estoque != null) {
            estoque.setQuant(novaQuantidade);
            estoqueDAO.atualizarEstoque(estoque);
            JOptionPane.showMessageDialog(null, "Estoque atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado no estoque.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }*/


    public void visualizarEstoque() {
        // Pergunta ao usuário qual filtro ele quer usar
        String[] opcoesFiltros = {"Código", "Categoria", "Nenhum Filtro"};
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
        } else if (filtroEscolhido.equals("Categoria")) {
            // Exibe as opções de categoria usando o enum
            Categoria[] categorias = Categoria.values();
            Categoria categoriaEscolhida = (Categoria) JOptionPane.showInputDialog(
                    null,
                    "Escolha uma categoria para filtrar:",
                    "Seleção de Categoria",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    categorias, // Opções de categorias
                    categorias[0] // Categoria padrão selecionada
            );

            if (categoriaEscolhida == null) {
                JOptionPane.showMessageDialog(null, "Nenhuma categoria selecionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Filtra o estoque pela categoria selecionada
            listarEstoquePorCategoria(categoriaEscolhida.name());

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
                    listarEstoquePorCodigo(valorFiltro);
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
    private void listarEstoquePorCodigo(String codigo) {
        StringBuilder listaEstoque = new StringBuilder("Estoque Filtrado por Código " + codigo + ":\n");
        for (Estoque estoque : estoqueDAO.listarTodos()) {
            Produtos produto = estoque.getProduto();
            if (produto.getCodigo().equalsIgnoreCase(codigo)) {
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


    public void listarEstoquePorQuantidadeMinima() {
        StringBuilder listaEstoque = new StringBuilder("Produtos com Estoque Igual ou Abaixo da Quantidade Mínima:\n");

        for (Estoque estoque : estoqueDAO.listarTodos()) {
            Produtos produto = estoque.getProduto();
            if (estoque.getQuant() <= estoque.getQuantMinima()) { // Verifica se a quantidade atual é menor ou igual à mínima
                listaEstoque.append("Produto: ").append(produto.getNome())
                        .append("\nCódigo: ").append(produto.getCodigo())
                        .append("\nCategoria: ").append(produto.getCategoria())
                        .append("\nQuantidade Atual: ").append(estoque.getQuant())
                        .append("\nQuantidade Mínima: ").append(estoque.getQuantMinima())
                        .append("\nNecessita Reposição: ").append(estoque.precisaRepor() ? "Sim" : "Não")
                        .append("\n--------------------------------\n");
            }
        }

        // Verifica se a lista contém informações de produtos
        if (listaEstoque.length() > "Produtos com Estoque Igual ou Abaixo da Quantidade Mínima:\n".length()) {
            JOptionPane.showMessageDialog(null, listaEstoque.toString(), "Estoque com Quantidade Baixa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum produto com estoque igual ou abaixo da quantidade mínima.", "Resultado do Filtro", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
