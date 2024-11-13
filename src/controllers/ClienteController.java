package controllers;

import models.Cliente;
import persistence.ClienteDAO;

import javax.swing.*;

public class ClienteController {
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void cadastraCliente() {
        try{

            ImageIcon icone = new ImageIcon("./.idea/images/cliente.png");

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
                        "O CPF informado é inválido.\nPor favor, insira um CPF válido com 11 dígitos numéricos.", // Mensagem clara e dividida em duas linhas
                        "Erro de Validação", // Título mais informativo
                        JOptionPane.ERROR_MESSAGE // Ícone de erro
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

            String nome = String.valueOf(JOptionPane.showInputDialog(
                    null,
                    "Informe o nome do Cliente: ", // Mensagem
                    "Cliente",                        // Título da janela
                    JOptionPane.PLAIN_MESSAGE,        // Tipo da mensagem (sem ícone de alerta)
                    icone,                            // Ícone personalizado
                    null,                             // Lista de opções (não usamos aqui)
                    ""                                // Valor inicial (campo de texto vazio)
            ));


            Cliente cliente = new Cliente(CPF, nome);
            clienteDAO.cadastraCliente(cliente);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean validaCPF(String CPF){
        return CPF.matches("\\d{11}");
    }
}
