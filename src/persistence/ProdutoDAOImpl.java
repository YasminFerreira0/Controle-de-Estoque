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
        // Verifica se o produto já existe (baseado no código)
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
        // Encontra o produto e atualiza seus dados
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
        // Remove o produto com base no código
        return produtos.removeIf(produto -> produto.getCodigo().equals(codigo));
    }

    @Override
    public Produtos buscarProdutoPorCodigo(Integer codigo) {
        // Busca o produto com base no código
        for (Produtos produto : produtos) {
            if (produto.getCodigo().equals(codigo)) {
                return produto;
            }
        }
        return null; // Produto não encontrado
    }

    @Override
    public List<Produtos> listarTodos() {
        // Retorna todos os produtos cadastrados
        return new ArrayList<>(produtos); // Retorna uma cópia para evitar modificações externas
    }
}