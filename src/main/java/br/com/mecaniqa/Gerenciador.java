package br.com.mecaniqa;

public class Gerenciador {

    // Boa, parabéns! O uso de arrays estáticos unidimensionais de tamanho fixo ajuda a realizar o
    // armazenamento de dados corretamente sem depender de Collections (java.util).
    public static Peca[] pecas = new Peca[100];
    public static Servico[] servicos = new Servico[50];

    public static int totalPecas = 0;
    public static int totalServicos = 0;

    // Boa, parabéns! Separar os contadores de código sequencial (proximoCodigoPeca/Servico) das variáveis
    // de quantidade (totalPecas/Servicos) evita códigos duplicados após remoções!
    private static int proximoCodigoPeca = 1;
    private static int proximoCodigoServico = 1;

    public static void inserirPeca(Peca p) {
        if (totalPecas >= pecas.length) {
            System.out.println("Erro: nao ha espaco disponivel para inserir a peca.");
            return; // bom uso de early return! Ajuda na legibilidade e evita aninhamento desnecessário.
        }
        p.codigo = proximoCodigoPeca;
        proximoCodigoPeca++;
        // Boa, parabéns! O uso da variável controladora totalPecas como índice realiza
        // a inserção em O(1) no primeiro espaço vazio.
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
        // Boa, parabéns! Inserção direta em O(1) usando totalServicos.
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
            // Dica: usar System.out.printf com %.2f deixaria os valores monetários sempre formatados com duas casas decimais.
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
        // Boa, parabéns! Busca linear limpa e correta, percorrendo apenas até totalPecas e retornando índice ou -1.
        for (int i = 0; i < totalPecas; i++) {
            if (pecas[i].codigo == codigo) {
                return i;
            }
        }
        return -1;
    }

    public static int buscarServico(int codigo) {
        // Boa, parabéns! Busca linear implementada corretamente.
        for (int i = 0; i < totalServicos; i++) {
            if (servicos[i].codigo == codigo) {
                return i;
            }
        }
        return -1;
    }

    public static void atualizarPeca(int codigo, Peca novosDados) {
        // Boa, parabéns! O uso da chamada da busca linear já implementada ajuda a
        // realizar a reutilização de código no processo de atualização, modificando os atributos públicos diretamente.
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
        // Boa, parabéns! Reutilização da busca linear e acesso direto aos atributos da struct.
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
        // Boa, parabéns! Reutiliza buscarPeca, desloca os elementos à esquerda (shift-left) sem deixar buracos,
        // limpa a última posição com null e decrementa totalPecas.
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
        // Boa, parabéns! Mesma lógica impecável de remoção com busca linear, shift-left e limpeza da última posição.
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

}