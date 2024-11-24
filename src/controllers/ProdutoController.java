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
            Categoria categoria = escolherCategoria(); // Obtendo a categoria como enum
            if (categoria == null) return; // Cancelamento de operação

            String fornecedorNome = JOptionPane.showInputDialog("Digite o fornecedor do produto:");
            String precoStr = JOptionPane.showInputDialog("Digite o preço do produto:");
            String quantidadeStr = JOptionPane.showInputDialog("Digite a quantidade em estoque:");
            String quantMinimaStr = JOptionPane.showInputDialog("Digite a quantidade mínima do produto:");

            // Cancelamento de operação
            if (nome == null || codigoStr == null || fornecedorNome == null || precoStr == null ||
                    quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de cadastro de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || codigoStr.isEmpty() || fornecedorNome.isEmpty() ||
                    precoStr.isEmpty() || quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convertendo os dados inseridos
            int codigo = Integer.parseInt(codigoStr);
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            int quantMinima = Integer.parseInt(quantMinimaStr);

            // Criando o objeto Produto
            Fornecedor fornecedor = new Fornecedor(fornecedorNome);
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
                null,
                nomesCategorias,
                nomesCategorias[0]
        );

        if (categoriaSelecionada == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return Categoria.valueOf(categoriaSelecionada);
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

}
