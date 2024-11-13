package controllers;

import models.Vendedor;
import persistence.VendedorDAO;

import javax.swing.*;

public class VendedorController {
    private VendedorDAO vendedorDAO = new VendedorDAO();

    public void cadastroVendedor() {
        try{

            ImageIcon icone = new ImageIcon("./.idea/images/vendedor.png");


            String nome = String.valueOf(JOptionPane.showInputDialog(
                    null,
                    "Informe o nome do Vendedor: ", // Mensagem
                    "Vendedor",                        // Título da janela
                    JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                    icone,                            // Ícone personalizado
                    null,                             // Lista de opções (não usamos aqui)
                    ""                                // Valor inicial (campo de texto vazio)
            ));


            Vendedor vendedor = new Vendedor(nome);
            vendedorDAO.cadastraVendedor(vendedor);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

