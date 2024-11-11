package persistence;

import java.util.List;
import java.util.LinkedList;
import models.Vendas;

public class VendaDAO {
    private List<Vendas> historicoVendas = new LinkedList<>();

    public void registrarVenda (Vendas venda){
        historicoVendas.add(venda);
    }

    public List<Vendas> listarHistoricoVendas(){
        return new LinkedList<>(historicoVendas);
    }
}
