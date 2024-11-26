package app;

import controllers.EstoqueController;
import controllers.VendaController;
import controllers.ProdutoController;
import models.Categoria;
import models.Estoque;
import models.Produtos;
import models.Vendas;
import persistence.VendaDAO;
import persistence.EstoqueDAO;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        VendaController vendaController = new VendaController();
        ProdutoController produtoController = new ProdutoController();
        List<Vendas> vendas = VendaDAO.listarHistoricoVendas();
        EstoqueController estoqueController = new EstoqueController();

        ImageIcon loja = new ImageIcon("./.idea/images/loja.png");
        ImageIcon relatorio = new ImageIcon("./.idea/images/relatorio.png");
        ImageIcon produtoIcone = new ImageIcon("./.idea/images/produto.png");

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
                        while (true) {
                            // Menu com as opções
                            String[] opcProd = {"Cadastrar Produto", "Editar Produto", "Excluir Produto", "Sair"};
                            int escolha = JOptionPane.showOptionDialog(
                                    null,
                                    "Escolha a operação que deseja realizar:",
                                    "Produto",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE,
                                    produtoIcone,
                                    opcProd,
                                    opcProd[0]
                            );

                            // Lógica de escolha das opções
                            switch (escolha) {
                                case 0:  // Cadastrar Produto
                                    produtoController.adicionarProduto();
                                    break;

                                case 1:  // Editar Produto
                                    produtoController.editarProduto();
                                    break;

                                case 2:  // Excluir Produto
                                    produtoController.excluirProduto();
                                    break;

                                case 3:  // Sair
                                    break;

                                default:
                                    System.exit(0);
                            }
                            if (escolha == 3 || escolha == JOptionPane.CLOSED_OPTION) {
                                break; // Interrompe o loop do menu Produto
                            }
                        }
                        break;
                    case 1:

                        estoqueController.visualizarEstoque();

                        break;
                    case 2:
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

                                String[] filtrosEstoque = {
                                        "Por Produto",
                                        "Por Categoria",
                                        "Por Volume de Vendas",
                                        "Por Quantidade em Estoque"
                                };

                                int opcFiltroEstoque = JOptionPane.showOptionDialog(
                                        null,
                                        "Selecione o filtro do relatório de estoque", // Mensagem
                                        "Relatório de Estoque",                      // Título da janela
                                        JOptionPane.DEFAULT_OPTION,                  // Tipo de opção padrão
                                        JOptionPane.INFORMATION_MESSAGE,             // Tipo de mensagem (informação)
                                        null,                                        // Ícone
                                        filtrosEstoque,                              // Opções a serem exibidas
                                        filtrosEstoque[0]                            // Opção padrão selecionada
                                );

                                // Supondo que você tenha um método para listar os estoques.
                                List<Estoque> estoques = EstoqueDAO.listarTodos();
                                if (estoques.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Nenhum produto no estoque.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                    return;
                                }

                                switch (opcFiltroEstoque) {
                                    case 0: // Por Produto
                                        String produtoFiltro = JOptionPane.showInputDialog(
                                                null,
                                                "Informe o nome do produto para filtrar:",
                                                "Relatório de Estoque por Produto",
                                                JOptionPane.PLAIN_MESSAGE
                                        );

                                        if (produtoFiltro == null || produtoFiltro.trim().isEmpty()) {
                                            JOptionPane.showMessageDialog(null, "Produto não informado. Retornando ao menu principal.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                            return;
                                        }

                                        StringBuilder relatorioPorProduto = new StringBuilder("Estoque por Produto:\n");
                                        boolean produtoEncontrado = false;
                                        for (Estoque estoque : estoques) {
                                            if (estoque.getProduto().getNome().equalsIgnoreCase(produtoFiltro)) {
                                                relatorioPorProduto.append(estoque).append("\n");
                                                produtoEncontrado = true;
                                            }
                                        }

                                        if (!produtoEncontrado) {
                                            JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o nome informado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, relatorioPorProduto.toString(), "Relatório: Por Produto", JOptionPane.INFORMATION_MESSAGE);
                                        }
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
                                            StringBuilder relatorioPorCategoria = new StringBuilder("Estoque por Categoria:\n");
                                            boolean categoriaEncontrada = false;
                                            for (Estoque estoque : estoques) {
                                                if (estoque.getProduto().getCategoria() == categoriaFiltro) {
                                                    relatorioPorCategoria.append(estoque).append("\n");
                                                    categoriaEncontrada = true;
                                                }
                                            }

                                            if (!categoriaEncontrada) {
                                                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado na categoria informada.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                JOptionPane.showMessageDialog(null, relatorioPorCategoria.toString(), "Relatório: Por Categoria", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        } catch (IllegalArgumentException e) {
                                            JOptionPane.showMessageDialog(null, "Categoria inválida! Certifique-se de inserir uma das categorias disponíveis.", "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                        break;

                                    case 2: // Por Volume de Vendas
                                        Map<Produtos, Integer> volumePorProduto = new HashMap<>();
                                        vendas = VendaDAO.listarHistoricoVendas();

                                        for (Vendas venda : vendas) {
                                            for (Produtos produto : venda.getProdutosVendidos()) {
                                                volumePorProduto.put(produto, volumePorProduto.getOrDefault(produto, 0) + 1);
                                            }
                                        }

                                        List<Map.Entry<Produtos, Integer>> produtosOrdenados = new ArrayList<>(volumePorProduto.entrySet());
                                        produtosOrdenados.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                                        StringBuilder relatorioPorVolume = new StringBuilder("Produtos mais vendidos:\n");
                                        for (Map.Entry<Produtos, Integer> entry : produtosOrdenados) {
                                            relatorioPorVolume.append("Produto: ").append(entry.getKey().getNome())
                                                    .append(" | Volume de Vendas: ").append(entry.getValue()).append("\n");
                                        }

                                        if (relatorioPorVolume.length() == 0) {
                                            JOptionPane.showMessageDialog(null, "Nenhuma venda registrada até o momento.", "Aviso", JOptionPane.WARNING_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, relatorioPorVolume.toString(), "Relatório: Por Volume de Vendas", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        break;

                                    case 3: // Por Quantidade em Estoque
                                        StringBuilder relatorioPorQuantidade = new StringBuilder("Produtos abaixo da quantidade mínima em estoque:\n");
                                        boolean produtoAbaixoMinimo = false;
                                        for (Estoque estoque : estoques) {
                                            if (estoque.getQuant() < estoque.getQuantMinima()) {
                                                relatorioPorQuantidade.append(estoque).append("\n");
                                                produtoAbaixoMinimo = true;
                                            }
                                        }

                                        if (!produtoAbaixoMinimo) {
                                            JOptionPane.showMessageDialog(null, "Todos os produtos estão acima da quantidade mínima em estoque.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(null, relatorioPorQuantidade.toString(), "Relatório: Por Quantidade em Estoque", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                        break;

                                    default:
                                        JOptionPane.showMessageDialog(null, "Nenhuma opção selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
                                        break;
                                }


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
                                "Encerrando o Programa!!",     // Mensagem
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

                if (opc == 4) {
                    break; // Sai do laço 'do-while' quando a opção "Sair" for selecionada
                }

            }while(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

