package app;
import controllers.VendaController;
import controllers.ProdutoController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        VendaController vendaController = new VendaController();

        try{
            do{
                String[] opcoes = {"Produto", "Estoque", "Realizar Venda", "Relatorios","Sair"};
                ImageIcon icone = new ImageIcon("./.idea/images/loja.png");

                int opc = JOptionPane.showOptionDialog(
                        null,
                        "Escolha a operação que deseja realizar", // Mensagem
                        "Loja",                          // Título da janela
                        JOptionPane.DEFAULT_OPTION,      // Tipo de opção padrão
                        JOptionPane.INFORMATION_MESSAGE, // Tipo de mensagem (informação)
                        icone,                            // Ícone
                        opcoes,                          // Opções a serem exibidas
                        opcoes[0]                        // Opção padrão selecionada
                );


                // Caso queira tratar a escolha
                switch (opc) {
                    case 0:
                        System.out.println("Você escolheu Produto");
                        break;
                    case 1:
                        System.out.println("Você escolheu Estoque");
                        break;
                    case 2:
                        System.out.println("Você escolheu Realizar Venda");

                        vendaController.registrarVenda();

                        break;
                    case 3:
                        System.out.println("Você escolheu Relatórios");
                        break;
                    case 4:
                        System.out.println("Você escolheu Sair");
                        break;
                    default:
                        System.out.println("Nenhuma opção foi escolhida");
                        break;
                }


            }while(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

