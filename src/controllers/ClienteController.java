package controllers;

import models.Cliente;
import persistence.ClienteDAO;

import javax.swing.*;

public class ClienteController {
    private ClienteDAO clienteDAO = new ClienteDAO();

    public Cliente cadastraCliente() {
        ImageIcon icone = new ImageIcon("./.idea/images/cliente.png");

        try{

            String CPF = String.valueOf(JOptionPane.showInputDialog(
                    null,
                    "Digite o CPF do cliente (Informe apenas números):", // Mensagem
                    "Cliente",                        // Título da janela
                    JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                    icone,                            // Ícone personalizado
                    null,                             // Lista de opções (não usamos aqui)
                    ""                                // Valor inicial (campo de texto vazio)
            ));

            while (!validaCPF(CPF)) {
                JOptionPane.showMessageDialog(
                        null,
                        "O CPF informado é inválido.\nPor favor, insira um CPF válido com 11 dígitos numéricos.",
                        "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE
                );

                CPF = String.valueOf(JOptionPane.showInputDialog(
                        null,
                        "Digite o CPF do cliente (Informe apenas números):", // Mensagem
                        "Cliente",                        // Título da janela
                        JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                        icone,                            // Ícone personalizado
                        null,                             // Lista de opções (não usamos aqui)
                        ""                                // Valor inicial (campo de texto vazio)
                ));
            }

            String nome;

            while(true){
                nome = String.valueOf(JOptionPane.showInputDialog(
                        null,
                        "Informe o nome do Cliente: ", // Mensagem
                        "Cliente",                        // Título da janela
                        JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                        icone,                            // Ícone personalizado
                        null,                             // Lista de opções (não usamos aqui)
                        ""                                // Valor inicial (campo de texto vazio)
                ));

                if (!nome.trim().isEmpty()) {
                    break; // Nome válido foi informado, sai do loop
                } else {
                    JOptionPane.showMessageDialog(null, "O nome do cliente não pode ser vazio. Tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }


            Cliente cliente = new Cliente(CPF, nome);
            clienteDAO.cadastraCliente(cliente);
            return cliente;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private boolean validaCPF(String CPF){
        return CPF.matches("\\d{11}");
    }
}
