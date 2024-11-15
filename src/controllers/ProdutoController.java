package controllers;
import models.Categoria;
import models.Fornecedor;
import models.Produtos;
import persistence.ProdutoDAOImpl;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private final List<Produtos> estoque;

    public ProdutoController() {
        this.estoque = new ArrayList<>();
    }

    public void adicionarProduto(Produtos produto) {
        try {
            for (Produtos p : estoque) {
                if (p.getCodigo().equals(produto.getCodigo())) {
                    JOptionPane.showMessageDialog(null, "Produto com o código " + produto.getCodigo() + " já existe.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            estoque.add(produto);
            JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar o produto: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editarProduto(String codigo, Produtos novoProduto) {
        try {
            boolean produtoEncontrado = false;
            for (int i = 0; i < estoque.size(); i++) {
                Produtos p = estoque.get(i);
                if (Integer.toString(p.getCodigo()).equals(codigo)) {
                    estoque.set(i, novoProduto);
                    produtoEncontrado = true;
                    break;
                }
            }

            if (!produtoEncontrado) {
                JOptionPane.showMessageDialog(null, "Produto com o código " + codigo + " não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Produto editado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o produto: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluirProduto(String codigo) {
        try {
            boolean produtoEncontrado = false;
            for (int i = 0; i < estoque.size(); i++) {
                Produtos p = estoque.get(i);
                if (Integer.toString(p.getCodigo()).equals(codigo)) {
                    estoque.remove(i);
                    produtoEncontrado = true;
                    break;
                }
            }

            if (!produtoEncontrado) {
                JOptionPane.showMessageDialog(null, "Produto com o código " + codigo + " não encontrado.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o produto: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void visualizarEstoque() {
        try {
            StringBuilder estoqueInfo = new StringBuilder();
            for (Produtos produto : estoque) {
                estoqueInfo.append(produto).append("\n");
            }

            if (estoqueInfo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto no estoque.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, estoqueInfo.toString(),
                        "Estoque", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao visualizar o estoque: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verificarEstoqueMinimo() {
        try {
            StringBuilder alertas = new StringBuilder();
            for (Produtos produto : estoque) {
                if (produto.getQuantEstoque() <= produto.getQuantMinima()) {
                    alertas.append("Alerta: ").append(produto.getNome()).append(" está com estoque abaixo do mínimo.\n");
                }
            }

            if (alertas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum produto com estoque abaixo do mínimo.",
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, alertas.toString(),
                        "Alertas de Estoque", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar estoque mínimo: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Produtos buscarProduto(String codigo) {
        for (Produtos produto : estoque) {
            if (Integer.toString(produto.getCodigo()).equals(codigo)) {
                return produto;
            }
        }
        return null;
    }
}
