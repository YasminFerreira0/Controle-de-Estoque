package persistence;

import models.Fornecedor;
import models.Estoque;
import java.util.List;
import java.util.LinkedList;

public class FornecedorDAO {
    private List<Fornecedor> fornecedores = new LinkedList<>();

    public boolean cadastrarFornecedor(Fornecedor fornecedor) {
        for (Fornecedor f : fornecedores) {
            if (f.getNome().equalsIgnoreCase(fornecedor.getNome())) {
                return false;
            }
        }
        this.fornecedores.add(fornecedor);
        return true;
    }
    public List<Estoque> listarEstoques() {
        return estoqueList;
    }
}

