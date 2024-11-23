package app;

import controllers.VendaController;
import controllers.ProdutoController;
import models.Categoria;
import models.Produtos;
import models.Vendas;
import persistence.VendaDAO;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        VendaController vendaController = new VendaController();
        ProdutoController produtoController = new ProdutoController();
        VendaDAO vendaDAO = new VendaDAO();

        ImageIcon loja = new ImageIcon("./.idea/images/loja.png");
        ImageIcon relatorio = new ImageIcon("./.idea/images/relatorio.png");

        try{
            do{
                String[] opcoes = {"Produto", "Estoque", "Venda", "Relatorios","Sair"};

                int opc = JOptionPane.showOptionDialog(
                        null,
                        "Escolha a operação que deseja realizar", // Mensagem
                        "Loja",                          // Título da janela
                        JOptionPane.DEFAULT_OPTION,      // Tipo de opção padrão
                        JOptionPane.INFORMATION_MESSAGE, // Tipo de mensagem (informação)
                        loja,                            // Ícone
                        opcoes,                          // Opções a serem exibidas
                        opcoes[0]                        // Opção padrão selecionada
                );


                // Caso queira tratar a escolha
                switch (opc) {
                    case 0:
                        //*******************************************************************************************
                        // Switch case referente as rotinas com produto: cadastrar, editar, excluir
                        //*******************************************************************************************

                    break;
                    case 1:
                        //*******************************************************************************************
                        //implementar visualização do estoque com filtro por categoria e quantidade, EstoqueController
                        //*******************************************************************************************

                    break;
                    case 2:
                        //Implementação do VendaController
                        vendaController.registrarVenda();
                    break;
                    case 3:
                        String[] opcoesRelatorio = {"Relatorio de Estoque", "Relatorio de Venda", "Voltar"};

                        int opcRelatorio = JOptionPane.showOptionDialog(
                                null,
                                "Escolha a operação que deseja realizar", // Mensagem
                                "Relatorio",                          // Título da janela
                                JOptionPane.DEFAULT_OPTION,      // Tipo de opção padrão
                                JOptionPane.INFORMATION_MESSAGE, // Tipo de mensagem (informação)
                                relatorio,                            // Ícone
                                opcoesRelatorio,                          // Opções a serem exibidas
                                opcoes[0]                        // Opção padrão selecionada
                        );

                        switch(opcRelatorio){
                            case 0:
                                /*
                                Relatório de Estoque: Listagem da quantidade atual de produtos em estoque, indicando produtos em quantidade mínima.
                                Filtros para relatórios:
                                    Por Produto: Listagem de vendas ou estoque de um produto específico.
                                    Por Categoria: Listagem de vendas ou estoque por categorias de produtos.
                                    Por Data: Permitir selecionar períodos específicos para análise.
                                    Por Volume de Vendas: Filtrar produtos mais vendidos.
                                */
                            break;
                            case 1:
                                String[] filtrosVenda = {"Por Produto", "Por Categoria", "Por Data", "Por Volume de Venda"};

                                int opcFiltrosVenda = JOptionPane.showOptionDialog(
                                        null,
                                        "Selecione o filtro do relatorio", // Mensagem
                                        "Relatorio de Venda",                          // Título da janela
                                        JOptionPane.DEFAULT_OPTION,      // Tipo de opção padrão
                                        JOptionPane.INFORMATION_MESSAGE, // Tipo de mensagem (informação)
                                        relatorio,                            // Ícone
                                        filtrosVenda,                          // Opções a serem exibidas
                                        opcoes[0]                        // Opção padrão selecionada
                                );

                                List<Vendas> vendas = vendaDAO.listarHistoricoVendas();
                                if (vendas.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Nenhuma venda registrada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                    return;
                                }

                                switch (opcFiltrosVenda) {
                                    case 0: // Por Produto
                                        String produtoFiltro = String.valueOf(JOptionPane.showInputDialog(
                                                null,                                // Componente pai
                                                "Informe o nome do produto para filtrar:",     // Mensagem
                                                "Relatorio de Venda por Produto",                 // Título da janela
                                                JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                                                relatorio,                                // Ícone (null para usar o padrão)
                                                null,                                // Opções a serem exibidas (null para padrão)
                                                null                                 // Opção padrão selecionada
                                        ));

                                        if (produtoFiltro == null || produtoFiltro.trim().isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "Produto não informado. Retornando ao menu principal.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                            break;
                                        }
                                        StringBuilder relatorioPorProduto = new StringBuilder("Vendas por Produto:\n");
                                        for (Vendas venda : vendas) {
                                            for (Produtos produto : venda.getProdutosVendidos()) {
                                                if (produto.getNome().equalsIgnoreCase(produtoFiltro)) {
                                                    relatorioPorProduto.append(venda).append("\n");
                                                }
                                            }
                                        }
                                        JOptionPane.showMessageDialog(null, relatorioPorProduto.toString(), "Relatório: Por Produto", JOptionPane.INFORMATION_MESSAGE);
                                        break;

                                    case 1: // Por Categoria
                                        StringBuilder categoriasDisponiveis = new StringBuilder("Categorias disponíveis:\n");
                                        for (Categoria categoria : Categoria.values()) {
                                            categoriasDisponiveis.append(categoria.name()).append("\n");
                                        }
                                        JOptionPane.showMessageDialog(null, categoriasDisponiveis.toString(), "Categorias", JOptionPane.INFORMATION_MESSAGE);

                                        String categoriaFiltroStr = JOptionPane.showInputDialog("Informe a categoria para filtrar:");
                                        if (categoriaFiltroStr == null || categoriaFiltroStr.trim().isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "Categoria não informada. Retornando ao menu principal.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                            break;
                                        }

                                        try {
                                            Categoria categoriaFiltro = Categoria.valueOf(categoriaFiltroStr.toUpperCase());
                                            StringBuilder relatorioPorCategoria = new StringBuilder("Vendas por Categoria:\n");
                                            for (Vendas venda : vendas) {
                                                for (Produtos produto : venda.getProdutosVendidos()) {
                                                    if (produto.getCategoria() == categoriaFiltro) {
                                                        relatorioPorCategoria.append(venda).append("\n");
                                                    }
                                                }
                                            }
                                            JOptionPane.showMessageDialog(null, relatorioPorCategoria.toString(), "Relatório: Por Categoria", JOptionPane.INFORMATION_MESSAGE);
                                        } catch (IllegalArgumentException e) {
                                            JOptionPane.showMessageDialog(null, "Categoria inválida! Certifique-se de inserir uma das categorias disponíveis.", "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;

                                    case 2: // Por Data
                                        try {
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                            Date inicio = sdf.parse(JOptionPane.showInputDialog("Informe a data inicial (dd/MM/yyyy):"));
                                            Date fim = sdf.parse(JOptionPane.showInputDialog("Informe a data final (dd/MM/yyyy):"));

                                            if (inicio.after(fim)) {
                                                JOptionPane.showMessageDialog(null, "A data inicial não pode ser maior que a data final.", "Erro", JOptionPane.ERROR_MESSAGE);
                                                break;
                                            }

                                            StringBuilder relatorioPorData = new StringBuilder("Vendas por Data:\n");
                                            for (Vendas venda : vendas) {
                                                if (!venda.getDataVenda().before(inicio) && !venda.getDataVenda().after(fim)) {
                                                    relatorioPorData.append(venda).append("\n");
                                                }
                                            }
                                            JOptionPane.showMessageDialog(null, relatorioPorData.toString(), "Relatório: Por Data", JOptionPane.INFORMATION_MESSAGE);
                                        } catch (ParseException e) {
                                            JOptionPane.showMessageDialog(null, "Data inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;

                                    case 3: // Por Volume de Venda
                                        Map<Produtos, Integer> volumePorProduto = new HashMap<>();
                                        for (Vendas venda : vendas) {
                                            List<Produtos> produtos = List.of(venda.getProdutosVendidos());
                                            List<Integer> quantidades = List.of(venda.getQuantVendida());
                                            for (int i = 0; i < produtos.size(); i++) {
                                                Produtos produto = produtos.get(i);
                                                int quantidade = quantidades.get(i);
                                                volumePorProduto.put(produto, volumePorProduto.getOrDefault(produto, 0) + quantidade);
                                            }
                                        }
                                        List<Map.Entry<Produtos, Integer>> produtosOrdenados = new ArrayList<>(volumePorProduto.entrySet());
                                        produtosOrdenados.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                                        StringBuilder relatorioPorVolume = new StringBuilder("Produtos mais vendidos:\n");
                                        for (Map.Entry<Produtos, Integer> entry : produtosOrdenados) {
                                            relatorioPorVolume.append("Produto: ").append(entry.getKey().getNome())
                                                    .append(" | Volume: ").append(entry.getValue()).append("\n");
                                        }
                                        JOptionPane.showMessageDialog(null, relatorioPorVolume.toString(), "Relatório: Por Volume", JOptionPane.INFORMATION_MESSAGE);
                                        break;

                                    default:
                                        JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                        }
                        break;
                    case 4:
                        // Finaliza o programa

                        JOptionPane.showOptionDialog(
                                null,                                // Componente pai
                                "Encerranddo Programa!!",     // Mensagem
                                "Saída",                             // Título da janela
                                JOptionPane.DEFAULT_OPTION,          // Tipo de opção padrão
                                JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensagem (informação)
                                loja,                                // Ícone (null para usar o padrão)
                                null,                                // Opções a serem exibidas (null para padrão)
                                null                                 // Opção padrão selecionada
                        );

                        break; // Encerra o laço do-while
                    default:
                        System.out.println("Nenhuma opção foi escolhida");
                        break;
                }

                // Condição para sair do loop (caso a opção "Sair" seja escolhida)
                if (opc == 4) {
                    break; // Sai do laço 'do-while' quando a opção "Sair" for selecionada
                }

            }while(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

