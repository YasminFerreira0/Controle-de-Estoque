package persistence;
import models.Produtos;

import java.util.List;

public interface ProdutoDAO {

        boolean adicionarProduto(Produtos produto);

        boolean atualizarProduto(Produtos produto);

        boolean removerProduto(Integer codigo);

        Produtos buscarProdutoPorCodigo(Integer codigo);

        List<Produtos> listarTodos();
    }

