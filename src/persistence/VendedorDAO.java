package persistence;

import models.Vendedor;
import java.util.List;
import java.util.LinkedList;

public class VendedorDAO {
    private List<Vendedor> vendedores = new LinkedList<>();

    public boolean cadastraVendedor(Vendedor vendedor) {
        for (Vendedor v : vendedores) {
            if (v.getNome().equalsIgnoreCase(vendedor.getNome())) {
                return false;
            }
        }
        this.vendedores.add(vendedor);
        return true;
    }
}
