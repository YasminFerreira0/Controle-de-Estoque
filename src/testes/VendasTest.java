package testes;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import models.Vendas;
import models.Cliente;
import models.Vendedor;
import models.Produtos;
import models.Estoque;
import persistence.VendaDAO;

import java.util.Date;
import java.util.List;

public class VendasTest {
    private VendaDAO vendaDAO;
    private Produtos produto1;
    private Estoque estoque;
    private Cliente cliente;
    private Vendedor vendedor;
    private Vendas venda;

    @Before
    public void setup() {
        vendaDAO = new VendaDAO();
        produto1 = new Produtos("1", "Produto 1", null, null, 20.0, 10, 5);
        estoque = new Estoque(10, 5, produto1);
        cliente = new Cliente("12345678901", "Cliente Teste");
        vendedor = new Vendedor("Vendedor Teste");
    }

    @Test
    public void testValidarCpf() {
        // CPF inválido
        Cliente clienteInvalido = new Cliente("12345", "Cliente Inválido");

        // CPF válido
        assertTrue(cliente.validaCPF());
        assertFalse(clienteInvalido.validaCPF());
    }

    @Test
    public void testCalcularValorVenda() {
        Produtos[] produtosVendidos = {produto1};
        Integer[] quantVendida = {2};
        // Criar a venda e calcular o valor total automaticamente
        venda = new Vendas(new Date(), produtosVendidos, quantVendida, cliente, vendedor);

        // Verificar o cálculo do valor total
        assertEquals(40.0, venda.getValorTotal(), 0.0001); // (2 * 20.0)
    }

    @Test
    public void testVendaComMultiplosProdutos() {
        Produtos produto2 = new Produtos("2", "Produto 2",null, null, 15.0, 20, 5);
        Estoque estoque2 = new Estoque(20, 5, produto2);

        Produtos[] produtosVendidos = {produto1, produto2};
        Integer[] quantidades = {2, 3};
        venda = new Vendas(new Date(), produtosVendidos, quantidades, cliente, vendedor);

        // Calcular o valor esperado
        Double valorEsperado = (produto1.getPreco() * 2) + (produto2.getPreco() * 3);
        assertEquals(valorEsperado, venda.getValorTotal(), 0.0001);
    }


    @Test
    public void testVendaComProdutoPrecoZero() {
        Produtos produtoComPrecoZero =
                new Produtos("3", "Produto Preço Zero", null, null, 0.0, 10, 5);
        Produtos[] produtosVendidos = {produtoComPrecoZero};
        Integer[] quantVendida = {5};
        venda = new Vendas(new Date(), produtosVendidos, quantVendida, cliente, vendedor);

        // O valor total deve ser 0.0, já que o preço do produto é zero
        assertEquals(0.0, venda.getValorTotal(), 0.0001);
    }


    @Test
    public void testRegistrarEListarVendas() {
        // Configuração inicial
        Produtos[] produtosVendidos = {produto1};
        Integer[] quantVendida = {2};
        Vendas venda1 = new Vendas(new Date(), produtosVendidos, quantVendida, cliente, vendedor);

        Produtos produto2 = new Produtos("2", "Produto 2",null, null, 15.0, 20, 5);
        Produtos[] produtosVendidos2 = {produto2};
        Integer[] quantVendida2 = {3};
        Vendas venda2 = new Vendas(new Date(), produtosVendidos2, quantVendida2, cliente, vendedor);

        // Registrar as vendas
        vendaDAO.registrarVenda(venda1);
        vendaDAO.registrarVenda(venda2);

        // Listar o histórico de vendas
        List<Vendas> historico = vendaDAO.listarHistoricoVendas();

        // Verificar se as vendas foram registradas corretamente
        assertEquals(2, historico.size());
        assertEquals(venda1, historico.get(0));
        assertEquals(venda2, historico.get(1));

        // Verificar que a lista retornada é uma nova instância
        historico.clear();
        assertEquals(2, vendaDAO.listarHistoricoVendas().size());
    }


    @Test
    public void testVendaComQuantidadeMaiorQueEstoque() {
        Produtos[] produtosVendidos = {produto1};
        Integer[] quantVendida = {20}; // Quantidade maior que o estoque disponível (10)

        venda = new Vendas(new Date(), produtosVendidos, quantVendida, cliente, vendedor);

        // O valor total deve ser calculado apenas para a quantidade disponível
        Double valorEsperado = produto1.getPreco() * produto1.getQuantEstoque();
        assertEquals(valorEsperado, venda.getValorTotal(), 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVendaSemCliente() {
        Produtos[] produtosVendidos = {produto1};
        Integer[] quantVendida = {2};
        venda = new Vendas(new Date(), produtosVendidos, quantVendida, null, vendedor);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testVendaSemVendedor() {
        Produtos[] produtosVendidos = {produto1};
        Integer[] quantVendida = {2};
        venda = new Vendas(new Date(), produtosVendidos, quantVendida, cliente, null);
    }

}
