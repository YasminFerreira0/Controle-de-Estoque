package persistence;

import models.Estoque;
import models.Produtos;
import  java.util.List;
import java.util.LinkedList;

public class EstoqueDAO {
    private static List<Estoque> estoques = new LinkedList<>();

    public boolean adicionarEstoque(Estoque estoque) {
        for (Estoque e : estoques) {
            if (e.getProduto().getCodigo().equals(estoque.getProduto().getCodigo())) {
                return false;
            }
        }
        estoques.add(estoque);
        return true;
    }

    public boolean atualizarEstoque(Estoque estoque) {
        for (int i = 0; i < estoques.size(); i++) {
            if (estoques.get(i).getProduto().getCodigo().equals(estoque.getProduto().getCodigo())) {
                estoques.set(i, estoque);
                return true;
            }
        }
        return false;
    }

    public boolean removerEstoque(Produtos produto) {
        return estoques.removeIf(e -> e.getProduto().getCodigo().equals(produto.getCodigo()));
    }

    public Estoque buscarEstoquePorProduto(Produtos produto) {
        for (Estoque e : estoques) {
            if (e.getProduto().getCodigo().equals(produto.getCodigo())) {
                return e;
            }
        }
        return null;
    }

    public static List<Estoque> listarTodos() {
        return new LinkedList<>(estoques);
    }
}
