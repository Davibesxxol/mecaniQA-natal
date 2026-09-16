package br.com.mecaniqa;

public class Gerenciador {

    public static Peca[] pecas = new Peca[100];
    public static Servico[] servicos = new Servico[50];

    public static int totalPecas = 0;
    public static int totalServicos = 0;

    private static int proximoCodigoPeca = 1;
    private static int proximoCodigoServico = 1;

    public static void inserirPeca(Peca p) {
        if (totalPecas >= pecas.length) {
            System.out.println("Erro: nao ha espaco disponivel para inserir a peca.");
            return;
        }
        p.codigo = proximoCodigoPeca;
        proximoCodigoPeca++;
        pecas[totalPecas] = p;
        totalPecas++;
        System.out.println("Peca cadastrada com sucesso! Codigo gerado: " + p.codigo);
    }

    public static void inserirServico(Servico s) {
        if (totalServicos >= servicos.length) {
            System.out.println("Erro: nao ha espaco disponivel para inserir o servico.");
            return;
        }
        s.codigo = proximoCodigoServico;
        proximoCodigoServico++;
        servicos[totalServicos] = s;
        totalServicos++;
        System.out.println("Servico cadastrado com sucesso! Codigo gerado: " + s.codigo);
    }

    public static void listarPecas() {
        if (totalPecas == 0) {
            System.out.println("Nenhuma peca cadastrada.");
            return;
        }
        for (int i = 0; i < totalPecas; i++) {
            Peca p = pecas[i];
            System.out.println(
                    "Codigo: " + p.codigo +
                            " | Nome: " + p.nome +
                            " | Fabricante: " + p.fabricante +
                            " | Preco Custo: R$" + p.precoCusto +
                            " | Preco Venda: R$" + p.precoVenda +
                            " | Estoque: " + p.quantidadeEstoque
            );
        }
    }

    public static void listarServicos() {
        if (totalServicos == 0) {
            System.out.println("Nenhum servico cadastrado.");
            return;
        }
        for (int i = 0; i < totalServicos; i++) {
            Servico s = servicos[i];
            System.out.println(
                    "Codigo: " + s.codigo +
                            " | Descricao: " + s.descricao +
                            " | Tempo estimado (min): " + s.tempoEstimadoMinutos +
                            " | Valor mao de obra: R$" + s.valorMaoDeObra
            );
        }
    }

    public static int buscarPeca(int codigo) {
        for (int i = 0; i < totalPecas; i++) {
            if (pecas[i].codigo == codigo) {
                return i;
            }
        }
        return -1;
    }

    public static int buscarServico(int codigo) {
        for (int i = 0; i < totalServicos; i++) {
            if (servicos[i].codigo == codigo) {
                return i;
            }
        }
        return -1;
    }

    public static void atualizarPeca(int codigo, Peca novosDados) {
        int indice = buscarPeca(codigo);

        if (indice == -1) {
            System.out.println("Erro: peca com codigo " + codigo + " nao encontrada.");
            return;
        }

        pecas[indice].nome = novosDados.nome;
        pecas[indice].fabricante = novosDados.fabricante;
        pecas[indice].precoCusto = novosDados.precoCusto;
        pecas[indice].precoVenda = novosDados.precoVenda;
        pecas[indice].quantidadeEstoque = novosDados.quantidadeEstoque;

        System.out.println("Peca atualizada com sucesso!");
    }

    public static void atualizarServico(int codigo, Servico novosDados) {
        int indice = buscarServico(codigo);

        if (indice == -1) {
            System.out.println("Erro: servico com codigo " + codigo + " nao encontrado.");
            return;
        }

        servicos[indice].descricao = novosDados.descricao;
        servicos[indice].tempoEstimadoMinutos = novosDados.tempoEstimadoMinutos;
        servicos[indice].valorMaoDeObra = novosDados.valorMaoDeObra;

        System.out.println("Servico atualizado com sucesso!");
    }

    public static void removerPeca(int codigo) {
        int indice = buscarPeca(codigo);

        if (indice == -1) {
            System.out.println("Erro: peca com codigo " + codigo + " nao encontrada.");
            return;
        }

        for (int i = indice; i < totalPecas - 1; i++) {
            pecas[i] = pecas[i + 1];
        }

        pecas[totalPecas - 1] = null;
        totalPecas--;
        System.out.println("Peca removida com sucesso!");
    }

    public static void removerServico(int codigo) {
        int indice = buscarServico(codigo);

        if (indice == -1) {
            System.out.println("Erro: servico com codigo " + codigo + " nao encontrado.");
            return;
        }

        for (int i = indice; i < totalServicos - 1; i++) {
            servicos[i] = servicos[i + 1];
        }

        servicos[totalServicos - 1] = null;
        totalServicos--;
        System.out.println("Servico removido com sucesso!");
    }

    public static void ordenarPecas(ChaveOrdenacao chave) {
        for (int i = 0; i < totalPecas - 1; i++) {
            for (int j = 0; j < totalPecas - 1 - i; j++) {

                boolean trocar;
                if (chave == ChaveOrdenacao.NOME) {
                    trocar = pecas[j].nome.compareTo(pecas[j + 1].nome) > 0;
                } else {
                    trocar = pecas[j].codigo > pecas[j + 1].codigo;
                }

                if (trocar) {
                    Peca temp = pecas[j];
                    pecas[j] = pecas[j + 1];
                    pecas[j + 1] = temp;
                }
            }
        }
    }

    public static void ordenarServicos(ChaveOrdenacao chave) {
        for (int i = 0; i < totalServicos - 1; i++) {
            for (int j = 0; j < totalServicos - 1 - i; j++) {

                boolean trocar;
                if (chave == ChaveOrdenacao.NOME) {
                    trocar = servicos[j].descricao.compareTo(servicos[j + 1].descricao) > 0;
                } else {
                    trocar = servicos[j].codigo > servicos[j + 1].codigo;
                }

                if (trocar) {
                    Servico temp = servicos[j];
                    servicos[j] = servicos[j + 1];
                    servicos[j + 1] = temp;
                }
            }
        }
    }

}
