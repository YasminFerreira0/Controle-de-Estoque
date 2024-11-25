package persistence;
import models.Produtos;

import java.util.List;

public interface ProdutoDAO {

        boolean adicionarProduto(Produtos produto);

        boolean atualizarProduto(Produtos produto);

        boolean removerProduto(String codigo);

        Produtos buscarProdutoPorCodigo(String codigo);

        List<Produtos> listarTodos();
    }

