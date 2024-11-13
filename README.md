# Sistema de Controle de Estoque de uma Loja


## Cadastro de Produtos

**Descrição**: Permitir o registro de novos produtos, edição e exclusão de produtos no sistema,
incluindo todos os atributos necessários para sua gestão.

Requisitos Funcionais:
- Adicionar produtos ao estoque com os seguintes atributos:

        -> Nome (string): Nome do produto.

        -> Código (int ou string): Código único para identificação do produto.

        -> Categoria (Categoria): Categoria à qual o produto pertence (ex.: eletrônicos, vestuário).

        -> Quantidade em Estoque (int): Quantidade atual disponível no estoque.

        -> Preço (float): Preço unitário do produto.

        ->  Fornecedor (Fornecedor): Nome do fornecedor do produto.

- Editar informações do produto.
- Excluir produtos do sistema.
- Validação para garantir a unicidade do Código.

## Controle de Estoque
**Descrição**: Gerenciar a quantidade em estoque dos produtos e fornecer alertas quando a quantidade
atinge um valor mínimo.

Requisitos Funcionais:
- Atualização automática da quantidade em estoque ao registrar uma venda.
  
- Permitir a definição de uma Quantidade Mínima para cada produto, configurável para cada item.
  
- Emitir alertas para o administrador quando a Quantidade em Estoque de um produto estiver abaixo da Quantidade Mínima.
  
- Visualização do estado do estoque, com filtros por categorias e quantidades.

## Registro de Vendas
**Descrição**: Registrar todas as vendas realizadas e atualizar o estoque automaticamente.

Requisitos Funcionais:
- Registrar uma nova venda com os seguintes atributos:

        ->  Data da Venda (data): Data em que a venda foi realizada.

        -> Produtos Vendidos (lista de objetos): Lista contendo cada produto e a quantidade vendida.

        -> Quantidade Vendida (int): Número de unidades de cada produto vendido.

        -> Valor Total (float): Valor total da venda, calculado automaticamente com base na quantidade e no preço unitário.

        -> Cliente (Cliente): Cliente que comprou

        -> Vendedor (Vendedor): vendedor que efetuou a venda

- Atualização automática do estoque após cada venda.

- Armazenamento de um Histórico de Vendas com data, produtos vendidos e valor total.

## Relatórios
**Descrição**: Gerar relatórios de vendas e estoque, com opções de filtragem para ajudar na análise.

Requisitos Funcionais:
- Gerar relatórios de estoque e de vendas, incluindo:

        Relatório de Estoque: Listagem da quantidade atual de produtos em estoque, indicando produtos em quantidade mínima.

        Relatório de Vendas: Listagem das vendas realizadas, com data, produtos, quantidade e valor total.

- Filtros para relatórios:

         Por Produto: Listagem de vendas ou estoque de um produto específico.

         Por Categoria: Listagem de vendas ou estoque por categorias de produtos.

         Por Data: Permitir selecionar períodos específicos para análise.

         Por Volume de Vendas: Filtrar produtos mais vendidos.

- Exportar relatórios em formato legível (pode ser console ou arquivo de texto/CSV).

## Requisitos Técnicos e Validação

- Validação e Tratamento de Erros:

    Implementação de exceções para tratamento de erros, como:

        Tentativa de realizar vendas com quantidade superior ao disponível em estoque.

        Entrada inválida para campos (ex.: caracteres em campo numérico).

- Interface Amigável:

    Interface simples (console ou JOptionPane), com menus organizados para facilitar a navegação e a entrada de dados.


## Diagrama de Classe

![Diagrama de Classe](/.idea/images/diagramaClasse.png)

## Authors
- [Cauã Rogrigues](https://github.com/stelladesolaria)
- [Yasmin Ferreira](https://github.com/YasminFerreira0)

