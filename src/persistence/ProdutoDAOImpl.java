package persistence;

import models.Produtos;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAOImpl implements ProdutoDAO {
    private List<Produtos> produtos;

    public ProdutoDAOImpl() {
        this.produtos = new ArrayList<>();
    }

    @Override
    public boolean adicionarProduto(Produtos produto) {
        for (Produtos p : produtos) {
            if (p.getCodigo().equals(produto.getCodigo())) {
                return false; // Produto com esse código já existe
            }
        }
        produtos.add(produto);
        return true;
    }

    @Override
    public boolean atualizarProduto(Produtos produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equals(produto.getCodigo())) {
                produtos.set(i, produto);
                return true;
            }
        }
        return false; // Produto não encontrado
    }

    @Override
    public boolean removerProduto(Integer codigo) {
        return produtos.removeIf(produto -> produto.getCodigo().equals(codigo));
    }

    @Override
    public Produtos buscarProdutoPorCodigo(Integer codigo) {
        for (Produtos produto : produtos) {
            if (produto.getCodigo().equals(codigo)) {
                return produto;
            }
        }
        return null; // Produto não encontrado
    }

    @Override
    public List<Produtos> listarTodos() {
        return new ArrayList<>(produtos); // Retorna uma cópia para evitar modificações externas
    }
}