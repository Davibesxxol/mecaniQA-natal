package br.com.mecaniqa.entidades;

import br.com.mecaniqa.enums.ChaveOrdenacao;
import br.com.mecaniqa.nos.No;

import java.util.ArrayList;

public class Gerenciador {

    public static Peca[] pecas = new Peca[100];
    public static Servico[] servicos = new Servico[50];

    public static int totalPecas = 0;
    public static int totalServicos = 0;

    public static No raiz = null;

    public static ArrayList<Cliente> clientes = new ArrayList<>();
    public static ArrayList<Pedido> pedidos = new ArrayList<>();
    public static ArrayList<OrdemServico> ordens = new ArrayList<>();

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

    public static void carregarPeca(Peca p) {
        if (totalPecas >= pecas.length) {
            System.out.println("Erro: array de pecas cheio, peca " + p.codigo + " nao carregada.");
            return;
        }
        pecas[totalPecas] = p;
        totalPecas++;
        if (p.codigo >= proximoCodigoPeca) {
            proximoCodigoPeca = p.codigo + 1;
        }
    }

    public static void carregarServico(Servico s) {
        if (totalServicos >= servicos.length) {
            System.out.println("Erro: array de servicos cheio, servico " + s.codigo + " nao carregado.");
            return;
        }
        servicos[totalServicos] = s;
        totalServicos++;
        if (s.codigo >= proximoCodigoServico) {
            proximoCodigoServico = s.codigo + 1;
        }
    }

    public static void inserir(int id, Object registro) {
        if (registro == null) {
            System.out.println("Erro: nao e possivel indexar um registro nulo.");
            return;
        }
        raiz = inserirNo(raiz, id, registro);
    }

    private static No inserirNo(No atual, int id, Object registro) {
        if (atual == null) {
            No novo = new No();
            novo.id = id;
            novo.registro = registro;
            novo.altura = 1;
            return novo;
        }

        if (id < atual.id) {
            atual.esquerda = inserirNo(atual.esquerda, id, registro);
        } else if (id > atual.id) {
            atual.direita = inserirNo(atual.direita, id, registro);
        } else {
            System.out.println("Erro: ja existe um registro com o id " + id + " na br.com.mecaniqa.arvore.");
            return atual;
        }

        atual.altura = calcularAltura(atual);
        return balancear(atual);
    }

    public static Object buscar(int id) {
        return buscarNo(raiz, id);
    }

    private static Object buscarNo(No atual, int id) {
        if (atual == null) {
            return null;
        }
        if (id == atual.id) {
            return atual.registro;
        }
        if (id < atual.id) {
            return buscarNo(atual.esquerda, id);
        }
        return buscarNo(atual.direita, id);
    }

    public static void exibirEmOrdem(No raiz) {
        if (raiz == null) {
            return;
        }
        exibirEmOrdem(raiz.esquerda);
        exibirRegistro(raiz);
        exibirEmOrdem(raiz.direita);
    }

    private static void exibirRegistro(No no) {
        Object registro = no.registro;

        if (registro instanceof Peca p) {
            System.out.println("Codigo: " + no.id + " | Peca: " + p.nome + " | Fabricante: " + p.fabricante
                    + " | Preco: R$" + p.precoVenda);
        } else if (registro instanceof Servico s) {
            System.out.println("Codigo: " + no.id + " | Servico: " + s.descricao + " | Tempo (min): "
                    + s.tempoEstimadoMinutos + " | Mao de obra: R$" + s.valorMaoDeObra);
        } else if (registro instanceof Cliente c) {
            System.out.println("Codigo: " + no.id + " | Cliente: " + c.nome + " | Telefone: " + c.telefone
                    + " | Email: " + c.email);
        } else if (registro instanceof OrdemServico os) {
            System.out.println("Codigo: " + no.id + " | OS | Status: " + os.status + " | Servicos: "
                    + os.quantidadeServicos() + " | Valor total: R$" + os.valorTotal());
        } else if (registro instanceof Pedido pedido) {
            System.out.println("Codigo: " + no.id + " | Pedido | Itens: " + pedido.itens.size()
                    + " | Total: R$" + pedido.calcularTotal());
        } else {
            System.out.println("Codigo: " + no.id + " | " + registro);
        }
    }

    private static int alturaDe(No no) {
        if (no == null) {
            return 0;
        }
        return no.altura;
    }

    public static int calcularAltura(No no) {
        return 1 + Math.max(alturaDe(no.esquerda), alturaDe(no.direita));
    }

    public static int fatorBalanceamento(No no) {
        if (no == null) {
            return 0;
        }
        return alturaDe(no.esquerda) - alturaDe(no.direita);
    }

    private static No balancear(No no) {
        int fator = fatorBalanceamento(no);

        if (fator > 1) {
            if (fatorBalanceamento(no.esquerda) < 0) {
                return rotacaoEsquerdaDireita(no);
            }
            return rotacaoDireita(no);
        }

        if (fator < -1) {
            if (fatorBalanceamento(no.direita) > 0) {
                return rotacaoDireitaEsquerda(no);
            }
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public static No rotacaoDireita(No y) {
        No x = y.esquerda;
        No meio = x.direita;

        x.direita = y;
        y.esquerda = meio;

        y.altura = calcularAltura(y);
        x.altura = calcularAltura(x);

        return x;
    }

    public static No rotacaoEsquerda(No x) {
        No y = x.direita;
        No meio = y.esquerda;

        y.esquerda = x;
        x.direita = meio;

        x.altura = calcularAltura(x);
        y.altura = calcularAltura(y);

        return y;
    }

    public static No rotacaoEsquerdaDireita(No no) {
        no.esquerda = rotacaoEsquerda(no.esquerda);
        return rotacaoDireita(no);
    }

    public static No rotacaoDireitaEsquerda(No no) {
        no.direita = rotacaoDireita(no.direita);
        return rotacaoEsquerda(no);
    }

}