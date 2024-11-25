package controllers;

import models.Fornecedor;
import persistence.FornecedorDAO;

import javax.swing.*;

public class FornecedorController {
    private FornecedorDAO fornecedorDAO = new FornecedorDAO();

    public Fornecedor cadastraFornecedor() {
        ImageIcon icone = new ImageIcon("./.idea/images/fornecedor.png");
        String nome;

        try{
            while(true){
                nome = String.valueOf(JOptionPane.showInputDialog(
                        null,
                        "Informe o Fornecedordo Produto: ",// Mensagem
                        "Fornecedor",                       // Título da janela
                        JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                        icone,                            // Ícone personalizado
                        null,                             // Lista de opções (não usamos aqui)
                        ""                                // Valor inicial (campo de texto vazio)
                ));

                if (!nome.trim().isEmpty()) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "O nome do fornecedor não pode ser vazio. Tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }

            }

            Fornecedor fornecedor = new Fornecedor(nome);
            fornecedorDAO.cadastrarFornecedor(fornecedor);
            return fornecedor;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
