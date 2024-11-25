package controllers;

import models.Produtos;
import models.Categoria;
import models.Fornecedor;
import models.Estoque;
import persistence.ProdutoDAOImpl;
import persistence.EstoqueDAO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private FornecedorController fornecedorController = new FornecedorController();
    private final ProdutoDAOImpl produtoDAO = new ProdutoDAOImpl();
    private EstoqueDAO estoqueDAO = new EstoqueDAO();


    ImageIcon produtoIcone = new ImageIcon("./.idea/images/produto.png");
    ImageIcon sucessoProduto = new ImageIcon("./.idea/images/sucessoProduto.png");
    ImageIcon excluirProduto = new ImageIcon("./.idea/images/produtoExcluido.png");
    // Adicionar produto
    public void adicionarProduto() {

        try {
            String nome = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe o nome do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String codigo = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe o codigo do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            Categoria categoria = escolherCategoria();
            if (categoria == null) return; // Cancelamento de operação

            Fornecedor fornecedor = fornecedorController.cadastraFornecedor();
            if (fornecedor == null) {
                // Cancelado no cadastro do fornecedor
                JOptionPane.showMessageDialog(null,
                        "Operação de cadastro cancelada. Retornando ao menu principal.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String precoStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe o preco do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String quantidadeStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe a quantidade em estoque do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String quantMinimaStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Informe a quantidade minima do estoque do produto:",
                    "Cadastro de Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            // Cancelamento de operação
            if (nome == null || codigo == null || precoStr == null ||
                    quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de cadastro de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || codigo.isEmpty() ||
                    precoStr.isEmpty() || quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Convertendo os dados inseridos
            Double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);
            int quantMinima = Integer.parseInt(quantMinimaStr);

            // Criando o objeto Produto
            Produtos produto = new Produtos(codigo, nome, categoria, fornecedor, preco, quantidade, quantMinima);

            boolean produtoAdicionado = produtoDAO.adicionarProduto(produto);
            if (!produtoAdicionado) {
                JOptionPane.showMessageDialog(null, "Produto com código já existente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Estoque estoque = new Estoque(quantidade, quantMinima, produto);
            estoqueDAO.adicionarEstoque(estoque);


            // Exibindo mensagem detalhada
            JOptionPane.showMessageDialog(
                    null,
                    String.format(
                            "Produto adicionado com sucesso!\n\n" +
                                    "Código: %s\n" +
                                    "Nome: %s\n" +
                                    "Categoria: %s\n" +
                                    "Fornecedor: %s\n" +
                                    "Preço: R$ %.2f\n" +
                                    "Quantidade: %d\n" +
                                    "Quantidade Mínima: %d",
                            produto.getCodigo(),
                            produto.getNome(),
                            produto.getCategoria(),
                            produto.getFornecedor().getNome(),
                            produto.getPreco(),
                            produto.getQuantEstoque(),
                            produto.getQuantMinima()
                    ),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE,
                    sucessoProduto
            );


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erro de conversão de número: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo auxiliar para selecionar a categoria
    private Categoria escolherCategoria() {
        Categoria[] categorias = Categoria.values();
        String[] nomesCategorias = new String[categorias.length];
        for (int i = 0; i < categorias.length; i++) {
            nomesCategorias[i] = categorias[i].name();
        }

        String categoriaSelecionada = (String) JOptionPane.showInputDialog(
                null,
                "Escolha a categoria do produto:",
                "Seleção de Categoria",
                JOptionPane.QUESTION_MESSAGE,
                produtoIcone,
                nomesCategorias,
                nomesCategorias[0]
        );

        if (categoriaSelecionada == null) {
            JOptionPane.showMessageDialog(null, "Operação cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return Categoria.valueOf(categoriaSelecionada);
    }



    // Editar produto (com entrada via JOptionPane)
    public void editarProduto() {
        try {
            String codigo = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite o código do produto a ser editado:",
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            if (codigo == null || codigo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação de edição de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            Produtos produtoExistente = produtoDAO.buscarProdutoPorCodigo(codigo); // Buscar produto no banco de dados
            if (produtoExistente == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Entrada dos novos dados para o produto
            String nome = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite o novo nome do produto:"+ produtoExistente.getNome(),
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            Categoria novaCategoria = escolherCategoria(); // Obtendo nova categoria
            if (novaCategoria == null) return; // Cancelamento de operação

            String fornecedorNome = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite o novo fornecedor do produto:"+ produtoExistente.getFornecedor().getNome(),
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String precoStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite o novo preço do produto:"+ produtoExistente.getPreco(),
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String quantidadeStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite a nova quantidade em estoque:"+ produtoExistente.getQuantEstoque(),
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            String quantMinimaStr = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite a nova quantidade mínima do produto:"+ produtoExistente.getQuantMinima(),
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    produtoIcone,                                // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            // Cancelamento de operação
            if (nome == null || fornecedorNome == null || precoStr == null || quantidadeStr == null || quantMinimaStr == null) {
                JOptionPane.showMessageDialog(null, "Operação de edição de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Verificando se os campos não estão vazios
            if (nome.isEmpty() || fornecedorNome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty() || quantMinimaStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verificando se as entradas para números são válidas
            double preco;
            int quantidade, quantMinima;
            try {
                preco = Double.parseDouble(precoStr);
                quantidade = Integer.parseInt(quantidadeStr);
                quantMinima = Integer.parseInt(quantMinimaStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira valores válidos para preço, quantidade e quantidade mínima.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Fornecedor fornecedor = new Fornecedor(fornecedorNome);

            // Atualizando os dados do produto
            produtoExistente.setNome(nome);
            produtoExistente.setCategoria(novaCategoria); // Atualizando categoria
            produtoExistente.setFornecedor(fornecedor);  // Atualizando fornecedor
            produtoExistente.setPreco(preco);
            produtoExistente.setQuantEstoque(quantidade);
            produtoExistente.setQuantMinima(quantMinima);

            // Editando o produto no banco de dados
            boolean produtoAtualizado = produtoDAO.atualizarProduto(produtoExistente);
            if (!produtoAtualizado) {
                JOptionPane.showMessageDialog(null, "Erro ao editar o produto. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }


            // Exibindo mensagem detalhada
            JOptionPane.showMessageDialog(
                    null,
                    String.format(
                            "Produto editado com sucesso!\n\n" +
                                    "Código: %s\n" +
                                    "Nome: %s\n" +
                                    "Categoria: %s\n" +
                                    "Fornecedor: %s\n" +
                                    "Preço: R$ %.2f\n" +
                                    "Quantidade: %d\n" +
                                    "Quantidade Mínima: %d",
                            produtoExistente.getCodigo(),
                            produtoExistente.getNome(),
                            produtoExistente.getCategoria(),
                            produtoExistente.getFornecedor().getNome(),
                            produtoExistente.getPreco(),
                            produtoExistente.getQuantEstoque(),
                            produtoExistente.getQuantMinima()
                    ),
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE,
                    sucessoProduto
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void excluirProduto() {
        try {
            String codigo = String.valueOf(JOptionPane.showInputDialog(
                    null,                                // Componente pai
                    "Digite o código do produto a ser excluído:",
                    "Editar Produtos",
                    JOptionPane.PLAIN_MESSAGE,           // Tipo de mensagem (mensagem simples)
                    excluirProduto,                                //
                    // Ícone (null para usar o padrão)
                    null,                                // Opções a serem exibidas (null para padrão)
                    null                                 // Opção padrão selecionada
            ));

            if (codigo == null || codigo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Operação de exclusão de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            Produtos produtoExistente = produtoDAO.buscarProdutoPorCodigo(codigo); // Buscar produto no banco de dados
            if (produtoExistente == null) {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmar exclusão
            int confirmacao = JOptionPane.showConfirmDialog(
                    null,
                    "Tem certeza de que deseja excluir o produto: " + produtoExistente.getNome() + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Operação de exclusão de produto cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Remover o produto do banco de dados e do estoque
            boolean produtoRemovido = produtoDAO.removerProduto(codigo);
            if (produtoRemovido) {
                estoqueDAO.removerEstoque(produtoExistente); //
                // Remover estoque associado ao produto

                JOptionPane.showMessageDialog(
                        null,
                        "Produto excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE,
                        excluirProduto
                );
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao excluir o produto. Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


}
