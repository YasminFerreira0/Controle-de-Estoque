package controllers;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Vendedor;
import models.Cliente;
import persistence.VendaDAO;
import models.Vendas;
import models.Estoque;
import models.Produtos;

import persistence.VendaDAO;

public class VendaController {
    private VendaDAO vendaDAO = new VendaDAO();
    private ClienteController clienteController = new ClienteController();
    private VendedorController vendedorController = new VendedorController();

    public void registrarVenda () {
        ImageIcon data = new ImageIcon("./.idea/images/data.png");

        try{
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            formatoData.setLenient(false);

            String dataVendaStr;

            while(true){
                dataVendaStr = String.valueOf(JOptionPane.showInputDialog(
                        null,
                        "Informe a data da venda (dd/MM/yyyy):",  // Mensagem
                        "Data da Venda",                          // Título da janela
                        JOptionPane.PLAIN_MESSAGE,                // Tipo da mensagem
                        data,                                    // Ícone personalizado
                        null,                                     // Opções (não usadas no input dialog)
                        ""                                        // Valor inicial (campo de texto vazio)
                ));

                if (dataVendaStr == null) {
                    JOptionPane.showMessageDialog(null, "Operação de venda cancelada. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (dataVendaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Data não informada. Tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    continue;
                }

                try {
                    Date dataVenda = formatoData.parse(dataVendaStr);
                    break;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Data inválida. Por favor, informe uma data no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            Cliente cliente = clienteController.cadastraCliente();
            if (cliente == null) {
                // Cancelado no cadastro do cliente
                JOptionPane.showMessageDialog(null, "Operação de venda cancelada durante o cadastro do cliente. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Vendedor vendedor = vendedorController.cadastroVendedor();
            if (vendedor == null) {
                // Cancelado no cadastro do vendedor
                JOptionPane.showMessageDialog(null, "Operação de venda cancelada durante o cadastro do vendedor. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
