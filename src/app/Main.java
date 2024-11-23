package app;
import controllers.VendaController;
import controllers.ProdutoController;
import models.Vendas;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        VendaController vendaController = new VendaController();
        ProdutoController produtoController = new ProdutoController();

        ImageIcon loja = new ImageIcon("./.idea/images/loja.png");

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
                        System.out.println("Você escolheu Produto");

                        //*******************************************************************************************
                        // Switch case referente as rotinas com produto: cadastrar, editar, excluir
                        //*******************************************************************************************
                        //produtoController.adicionarProduto();


                        break;
                    case 1:
                        System.out.println("Você escolheu Estoque");


                        //*******************************************************************************************
                        //implementar visualização do estoque com filtro por categoria e quantidade, EstoqueController
                        //*******************************************************************************************


                        break;
                    case 2:
                        //Implementação do VendaController
                        vendaController.registrarVenda();

                        break;
                    case 3:
                        System.out.println("Você escolheu Relatórios");


                        //*******************************************************************************************
                        //Relatório de Estoque: Listagem da quantidade atual de produtos em estoque, indicando produtos em quantidade mínima.
                        //Filtros para relatórios:
                        //
                        //   Por Produto: Listagem de vendas ou estoque de um produto específico.
                        //
                        //   Por Categoria: Listagem de vendas ou estoque por categorias de produtos.
                        //
                        //   Por Data: Permitir selecionar períodos específicos para análise.
                        //
                        //   Por Volume de Vendas: Filtrar produtos mais vendidos.
                        //*******************************************************************************************



                        //*******************************************************************************************
                        //  Relatório de Vendas: Listagem das vendas realizadas, com data, produtos, quantidade e valor total.
                        //*******************************************************************************************
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

