package controllers;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Vendedor;
import models.Cliente;
import persistence.VendaDAO;
import persistence.ProdutoDAOImpl;
import models.Vendas;
import models.Estoque;
import models.Produtos;

import persistence.EstoqueDAO;


public class  VendaController {
    private VendaDAO vendaDAO = new VendaDAO();
    private ClienteController clienteController = new ClienteController();
    private VendedorController vendedorController = new VendedorController();
    private ProdutoDAOImpl produtoDAO = new ProdutoDAOImpl();
    private EstoqueDAO estoqueDAO = new EstoqueDAO();
    private EstoqueController estoqueController = new EstoqueController();

    public void registrarVenda () {
        ImageIcon dataIcone = new ImageIcon("./.idea/images/data.png");
        ImageIcon produtoIcone = new ImageIcon("./.idea/images/vendaProduto.png");
        ImageIcon pagamentoIcone = new ImageIcon("./.idea/images/pagamento.png");
        ImageIcon vendaIcone = new ImageIcon("./.idea/images/venda.png");

        try{
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
            formatoData.setLenient(false);

            String dataVendaStr;
            Date dataVenda = null;

            while(dataVenda == null){
                dataVendaStr = String.valueOf(JOptionPane.showInputDialog(
                        null,
                        "Informe a data da venda (dd/MM/yyyy):",  // Mensagem
                        "Data da Venda",                          // Título da janela
                        JOptionPane.PLAIN_MESSAGE,                // Tipo da mensagem
                        dataIcone,                                    // Ícone personalizado
                        null,                                     // Opções (não usadas no input dialog)
                        ""                                        // Valor inicial (campo de texto vazio)
                ));

                if (dataVendaStr == null) {
                    JOptionPane.showMessageDialog(null, "Operação de venda cancelada. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (dataVendaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Data não informada. Tente novamente.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    continue;
                }

                try {
                    dataVenda = formatoData.parse(dataVendaStr);
                    break;
                } catch (ParseException e) {
                    JOptionPane.showMessageDialog(null, "Data inválida. Por favor, informe uma data no formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            Cliente cliente = clienteController.cadastraCliente();
            if (cliente == null) {
                // Cancelado no cadastro do cliente
                JOptionPane.showMessageDialog(null, "Operação de venda cancelada durante o cadastro do cliente. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Vendedor vendedor = vendedorController.cadastroVendedor();
            if (vendedor == null) {
                // Cancelado no cadastro do vendedor
                JOptionPane.showMessageDialog(null, "Operação de venda cancelada durante o cadastro do vendedor. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            //Adicionar Produto na venda
            List<Produtos> produtosVendidos = new ArrayList<>();
            List<Integer> quantidadesVendidas = new ArrayList<>();
            Double valorTotal = 0.0d;
            boolean continuarAdicionando = true;

            do {


                // Exibir lista de produtos disponíveis
                List<Estoque> estoques = estoqueDAO.listarTodos();
                if (estoques.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum produto disponível no estoque. Operação cancelada.", "Estoque Vazio", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                StringBuilder listaProdutos = new StringBuilder("Produtos disponíveis:\n");
                for (Estoque estoque : estoques) {
                    Produtos produto = estoque.getProduto();
                    listaProdutos.append("Código: ").append(produto.getCodigo())
                            .append(" | Nome: ").append(produto.getNome())
                            .append(" | Preço: R$ ").append(produto.getPreco())
                            .append(" | Quantidade em Estoque: ").append(estoque.getQuant())
                            .append("\n");
                }

                JOptionPane.showMessageDialog(null, listaProdutos.toString(), "Produtos Disponíveis", JOptionPane.INFORMATION_MESSAGE);


                //erro
                // Captura do código do produto
                String codigoProduto = JOptionPane.showInputDialog(
                        null,                                // Componente pai
                        "Informe o código do produto:",     // Mensagem
                        "Adicionar Produto",                 // Título da janela
                        JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                        produtoIcone,                       // Ícone (null para usar o padrão)
                        null,                               // Opções a serem exibidas (null para padrão)
                        ""                                  // Opção padrão selecionada
                ).toString().trim();  // Utilizando trim() para
                // remover espaços extras

                if (codigoProduto == null || codigoProduto.isEmpty()) break;

                try {
                    Produtos produto = estoqueDAO.buscarProdutoPorCodigo(codigoProduto);

                    if (produto == null) {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado. Verifique o código.", "Erro", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    String quantidadeStr = JOptionPane.showInputDialog(
                            null,                                // Componente pai
                            "Informe a quantidade para o produto: ",     // Mensagem
                            "Quantidade do Produto",                 // Título da janela
                            JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                            produtoIcone,                                // Ícone (null para usar o padrão)
                            null,                                // Opções a serem exibidas (null para padrão)
                            ""                                 // Opção padrão selecionada
                    ).toString();

                    if (quantidadeStr == null || quantidadeStr.isEmpty()) break;

                    int quantidade = Integer.parseInt(quantidadeStr);

                    if (EstoqueController.verificarEstoque(produto, quantidade)) {
                        EstoqueController.baixarEstoque(String.valueOf(produto), quantidade);
                        produtosVendidos.add(produto);
                        quantidadesVendidas.add(quantidade);
                        valorTotal += produto.getPreco() * quantidade;
                    } else {
                        JOptionPane.showMessageDialog(null, "Estoque insuficiente para o produto: " + produto.getNome(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Código ou quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

                int opcao = JOptionPane.showConfirmDialog(null, "Deseja adicionar mais produtos?", "Adicionar Produtos",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                continuarAdicionando = (opcao == JOptionPane.YES_OPTION);

            } while (continuarAdicionando);

            Vendas venda = new Vendas(dataVenda, produtosVendidos.toArray(new Produtos[0]), quantidadesVendidas.toArray(new Integer[0]),
                    valorTotal, cliente, vendedor);
            vendaDAO.registrarVenda(venda);

            JOptionPane.showOptionDialog(
                    null,                                                   // Componente pai
                    "Venda registrada com sucesso! Valor total: R$ " + valorTotal,  // Mensagem
                    "Sucesso",                                              // Título da janela
                    JOptionPane.DEFAULT_OPTION,                              // Tipo de opção padrão
                    JOptionPane.INFORMATION_MESSAGE,                         // Tipo de mensagem (informação)
                    vendaIcone,                                                   // Ícone (null para usar o padrão)
                    null,                                                   // Opções a serem exibidas (null para padrão)
                    null                                                    // Opção padrão selecionada
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
