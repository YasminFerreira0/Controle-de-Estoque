package controllers;

import models.Produtos;
import models.Categoria;
import models.Fornecedor;
import models.Estoque;
import persistence.ProdutoDAOImpl;
import persistence.EstoqueDAO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private FornecedorController fornecedorController = new FornecedorController();
    private final ProdutoDAOImpl produtoDAO = new ProdutoDAOImpl();
    private EstoqueDAO estoqueDAO = new EstoqueDAO();


    ImageIcon produtoIcone = new ImageIcon("./.idea/images/produto.png");
    ImageIcon sucessoProduto = new ImageIcon("./.idea/images/sucessoProduto.png");
    // Adicionar produto
    public void adicionarProduto() {

        try {
            String nome = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe o nome do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String codigo = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe o codigo do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            Categoria categoria = escolherCategoria();
            if (categoria == null) return; // Cancelamento de operação

            Fornecedor fornecedor = fornecedorController.cadastraFornecedor();
            if (fornecedor == null) {
                // Cancelado no cadastro do fornecedor
                JOptionPane.showMessageDialog(null,
                        "Operação de cadastro cancelada. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String precoStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe o preco do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String quantidadeStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe a quantidade em estoque do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String quantMinimaStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe a quantidade minima do estoque do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            // Cancelamento de operação
            if (nome == null || codigo == null || precoStr == null ||
                    quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de cadastro de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || codigo.isEmpty() ||
                    precoStr.isEmpty() || quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convertendo os dados inseridos
            Double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            int quantMinima = Integer.parseInt(quantMinimaStr);

            // Criando o objeto Produto
            Produtos produto = new Produtos(codigo, nome, categoria, fornecedor, preco, quantidade, quantMinima);

            boolean produtoAdicionado = produtoDAO.adicionarProduto(produto);
            if (!produtoAdicionado) {
                JOptionPane.showMessageDialog(null, "Produto com código já existente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Estoque estoque = new Estoque(quantidade, quantMinima, produto);
            estoqueDAO.adicionarEstoque(estoque);


            JOptionPane.showOptionDialog(
                    null,                                                   // Componente pai
                    "Produto adicionado com sucesso!",  // Mensagem
                    "Sucesso",                                              // Título da janela
                    JOptionPane.DEFAULT_OPTION,                              // Tipo de opção padrão
                    JOptionPane.INFORMATION_MESSAGE,                         // Tipo de mensagem (informação)
                    sucessoProduto,
                    // Ícone (null para usar o padrão)
                    null,                                                   // Opções a serem exibidas (null para padrão)
                    null                                                    // Opção padrão selecionada
            );

            System.out.printf( produto.getNome() + produto.getCodigo());


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de conversão de número: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo auxiliar para selecionar a categoria
    private Categoria escolherCategoria() {
        Categoria[] categorias = Categoria.values();
        String[] nomesCategorias = new String[categorias.length];
        for (int i = 0; i < categorias.length; i++) {
            nomesCategorias[i] = categorias[i].name();
        }

        String categoriaSelecionada = (String) JOptionPane.showInputDialog(
                null,
                "Escolha a categoria do produto:",
                "Seleção de Categoria",
                JOptionPane.QUESTION_MESSAGE,
                produtoIcone,
                nomesCategorias,
                nomesCategorias[0]
        );

        if (categoriaSelecionada == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return Categoria.valueOf(categoriaSelecionada);
    }



    /*


    // Editar produto (com entrada via JOptionPane)
    public void editarProduto() {
        try {
            String codigoStr = JOptionPane.showInputDialog("Digite o código do produto a ser editado:");

            if (codigoStr == null || codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação de edição de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(codigoStr);

            // Verificando se o produto existe
            Produtos produtoExistente = null;
            for (Produtos produto : estoque) {
                if (produto.getCodigo().equals(codigo)) {
                    produtoExistente = produto;
                    break;
                }
            }

            if (produtoExistente == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Entrada dos novos dados para o produto
            String nome = JOptionPane.showInputDialog("Digite o novo nome do produto:", produtoExistente.getNome());
            Categoria novaCategoria = escolherCategoria(); // Obtendo nova categoria
            if (novaCategoria == null) return; // Cancelamento de operação

            String fornecedorNome = JOptionPane.showInputDialog("Digite o novo fornecedor do produto:", produtoExistente.getFornecedor().getNome());
            String precoStr = JOptionPane.showInputDialog("Digite o novo preço do produto:", produtoExistente.getPreco());
            String quantidadeStr = JOptionPane.showInputDialog("Digite a nova quantidade em estoque:", produtoExistente.getQuantEstoque());
            String quantMinimaStr = JOptionPane.showInputDialog("Digite a nova quantidade mínima do produto:", produtoExistente.getQuantMinima());

            // Cancelamento de operação
            if (nome == null || fornecedorNome == null || precoStr == null || quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de edição de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || fornecedorNome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convertendo os dados inseridos
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            int quantMinima = Integer.parseInt(quantMinimaStr);

            // Atualizando os dados do produto
            produtoExistente.setNome(nome);
            produtoExistente.setCategoria(novaCategoria); // Atualizando categoria
            produtoExistente.setFornecedor(new Fornecedor(fornecedorNome));
            produtoExistente.setPreco(preco);
            produtoExistente.setQuantEstoque(quantidade);
            produtoExistente.setQuantMinima(quantMinima);

            // Editando o produto no banco de dados e na lista de estoque
            produtoDAO.atualizarProduto(produtoExistente);
            JOptionPane.showMessageDialog(null, "Produto editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluirProduto() {
        try {
            String codigoStr = JOptionPane.showInputDialog("Digite o código do produto a ser excluído:");

            if (codigoStr == null || codigoStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação de exclusão de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(codigoStr);

            // Verificando se o produto existe
            Produtos produtoExistente = null;
            for (Produtos produto : estoque) {
                if (produto.getCodigo().equals(codigo)) {
                    produtoExistente = produto;
                    break;
                }
            }

            if (produtoExistente == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Excluindo o produto
            produtoDAO.removerProduto(codigo);
            estoque.remove(produtoExistente);
            JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    */

}
