package persistence;

import models.Estoque;
import models.Produtos;

import java.util.LinkedList;
import java.util.List;

public class EstoqueDAO {
    private static List<Estoque> estoques = new LinkedList<>();

    public boolean adicionarEstoque(Estoque estoque) {
        for (Estoque e : estoques) {
            if (e.getProduto().getCodigo().equalsIgnoreCase(estoque.getProduto().getCodigo())) {
                return false;
            }
        }
        return estoques.add(estoque);
    }

    public boolean atualizarEstoque(Estoque estoque) {
        for (int i = 0; i < estoques.size(); i++) {
            if (estoques.get(i).getProduto().getCodigo().equalsIgnoreCase(estoque.getProduto().getCodigo())) {
                estoques.set(i, estoque);
                return true;
            }
        }
        return false;
    }

    public boolean removerEstoque(Produtos produto) {
        boolean removido =
                estoques.removeIf(e -> e.getProduto().getCodigo().equalsIgnoreCase(produto.getCodigo()));
        if (removido) {
            System.out.println("Produto removido do estoque: " + produto.getCodigo());
        } else {
            System.out.println("Erro: Produto não encontrado para remoção. Código: " + produto.getCodigo());
        }
        return removido;
    }

    public Estoque buscarEstoquePorProduto(Produtos produto) {
        for (Estoque e : estoques) {
            if (e.getProduto().getCodigo().equalsIgnoreCase(produto.getCodigo())) {
                return e;
            }
        }
        return null;
    }

    public static List<Estoque> listarTodos() {
        return new LinkedList<>(estoques);
    }

    public Produtos buscarProdutoPorCodigo(String codigo) {
        List<Estoque> estoques = listarTodos();
        for (Estoque estoque : estoques) {
            if (estoque.getProduto().getCodigo().equalsIgnoreCase(codigo)) {
                return estoque.getProduto(); // Retorna o produto encontrado
            }
        }
        return null; // Retorna null se não encontrar o produto
    }
}