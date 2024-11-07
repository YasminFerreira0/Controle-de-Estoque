package controllers;

import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import models.Cliente;
import models.ItemVenda;
import models.Vendas;
import models.Vendedor;
import models.Produtos;

public class VendasController {
    private List<Vendas> historicoVendas = new LinkedList<>();
    public void registrarVenda(Date dataVenda, List<ItemVenda> itens, String nomeCliente, String nomeVendedor){
        for (ItemVenda item : itens) {
            Produtos produto = item.getProduto();
            int novaQuantidade = produto.getQuantidadeEstoque() - item.getQuantidadeVendida();
            produto.setQuantidadeEstoque(novaQuantidade);
        }

        Vendas venda = new Vendas(dataVenda, itens, new Cliente(nomeCliente), new Vendedor(nomeVendedor));
        historicoVendas.add(venda);
        System.out.println("Venda registrada. Valor total: " + venda.getValorTotal());
    }
}
