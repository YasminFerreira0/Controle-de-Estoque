package persistence;

import models.Cliente;
import java.util.List;
import java.util.LinkedList;

public class ClienteDAO {
    private List<Cliente> clientes = new LinkedList<>();

    public boolean cadastraCliente(Cliente cliente) {
        for (Cliente c : clientes) {
            if (c.getCPF().equals(cliente.getCPF())) {
                return false;
            }
        }
        this.clientes.add(cliente);
        return true;
    }
}
