package controllers;

import models.Vendedor;
import persistence.VendedorDAO;

import javax.swing.*;

public class VendedorController {
    private VendedorDAO vendedorDAO = new VendedorDAO();

    public Vendedor cadastroVendedor() {
        ImageIcon icone = new ImageIcon("./.idea/images/vendedor.png");
        String nome;

        try{
            while(true){
                nome = String.valueOf(JOptionPane.showInputDialog(
                        null,
                        "Informe o nome do Vendedor: ", // Mensagem
                        "Vendedor",                        // Título da janela
                        JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                        icone,                            // Ícone personalizado
                        null,                             // Lista de opções (não usamos aqui)
                        ""                                // Valor inicial (campo de texto vazio)
                ));

                if (!nome.trim().isEmpty()) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "O nome do vendedor não pode ser vazio. Tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }

            }

            Vendedor vendedor = new Vendedor(nome);
            vendedorDAO.cadastraVendedor(vendedor);
            return vendedor;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}

