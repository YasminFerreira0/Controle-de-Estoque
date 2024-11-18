package controllers;

import models.Produtos;
import models.Categoria;
import models.Fornecedor;
import persistence.ProdutoDAOImpl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private final ProdutoDAOImpl produtoDAO;
    private final List<Produtos> estoque;

    public ProdutoController() {
        this.produtoDAO = new ProdutoDAOImpl();
        this.estoque = new ArrayList<>();
        this.estoque.addAll(produtoDAO.listarTodos());
    }

    // Adicionar produto
    public void adicionarProduto() {
        try {
            String nome = JOptionPane.showInputDialog("Digite o nome do produto:");
            String codigoStr = JOptionPane.showInputDialog("Digite o código do produto:");
            String categoriaNome = JOptionPane.showInputDialog("Digite a categoria do produto:");
            String fornecedorNome = JOptionPane.showInputDialog("Digite o fornecedor do produto:");
            String precoStr = JOptionPane.showInputDialog("Digite o preço do produto:");
            String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade em estoque:");
            String quantMinimaStr = JOptionPane.showInputDialog("Digite a quantidade mínima do produto:");

            // Cancelamento de operação
            if (nome == null || codigoStr == null || categoriaNome == null || fornecedorNome == null ||
                    precoStr == null || quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de cadastro de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || codigoStr.isEmpty() || categoriaNome.isEmpty() || fornecedorNome.isEmpty() ||
                    precoStr.isEmpty() || quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convertendo os dados inseridos
            int codigo = Integer.parseInt(codigoStr);
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            int quantMinima = Integer.parseInt(quantMinimaStr);

            // Criando os objetos Categoria e Fornecedor com os dados inseridos
            Categoria categoria = new Categoria(categoriaNome); // ALTERAR PORQUE CATEGORIA É UMA LISTA
            Fornecedor fornecedor = new Fornecedor(fornecedorNome); //*********-----

            // Criando o objeto Produto
            Produtos produto = new Produtos(codigo, nome, categoria, fornecedor, preco, quantidade, quantMinima);

            // Verificando se o produto já existe
            for (Produtos p : estoque) {
                if (p.getCodigo().equals(produto.getCodigo())) {
                    JOptionPane.showMessageDialog(null, "Produto com o código " + produto.getCodigo() + " já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Adicionando o produto ao banco de dados e à lista de estoque
            produtoDAO.adicionarProduto(produto);
            estoque.add(produto);
            JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            String categoriaNome = JOptionPane.showInputDialog("Digite a nova categoria do produto:", produtoExistente.getCategoria().getNome());
            String fornecedorNome = JOptionPane.showInputDialog("Digite o novo fornecedor do produto:", produtoExistente.getFornecedor().getNome());
            String precoStr = JOptionPane.showInputDialog("Digite o novo preço do produto:", produtoExistente.getPreco());
            String quantidadeStr = JOptionPane.showInputDialog("Digite a nova quantidade em estoque:", produtoExistente.getQuantEstoque());
            String quantMinimaStr = JOptionPane.showInputDialog("Digite a nova quantidade mínima do produto:", produtoExistente.getQuantMinima());

            if (nome == null || categoriaNome == null || fornecedorNome == null || precoStr == null ||
                    quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de edição de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || categoriaNome.isEmpty() || fornecedorNome.isEmpty() || precoStr.isEmpty() ||
                    quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convertendo os dados inseridos
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            int quantMinima = Integer.parseInt(quantMinimaStr);

            // Atualizando os dados do produto
            produtoExistente.setNome(nome);
            produtoExistente.setCategoria(new Categoria(categoriaNome)); // Atualizando categoria
            produtoExistente.setFornecedor(new Fornecedor(fornecedorNome)); // Atualizando fornecedor
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

    public void visualizarEstoque() {
        try {
            StringBuilder estoqueInfo = new StringBuilder();
            for (Produtos produto : estoque) {
                estoqueInfo.append(produto).append("\n");
            }

            if (estoqueInfo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto no estoque.", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, estoqueInfo.toString(), "Estoque", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao visualizar o estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
