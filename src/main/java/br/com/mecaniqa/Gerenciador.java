package br.com.mecaniqa;

import java.util.ArrayList;

public class Gerenciador {

    public static No raizPecas = null;
    public static No raizServicos = null;
    public static No raizClientes = null;
    public static No raizOS = null;

    private static int proximoCodigoPeca = 1;
    private static int proximoCodigoServico = 1;


    public static void inserirPeca(Peca p) {
        if (p.codigo == 0) {
            p.codigo = proximoCodigoPeca++;
        } else if (p.codigo >= proximoCodigoPeca) { 
            proximoCodigoPeca = p.codigo + 1;
        }
        raizPecas = inserirNo(raizPecas, p.codigo, p);
        System.out.println("Peça indexada na Árvore com sucesso! Código: " + p.codigo);
    }

    public static void inserirServico(Servico s) {
        if (s.codigo == 0) {
            s.codigo = proximoCodigoServico++;
        } else if (s.codigo >= proximoCodigoServico) {
            proximoCodigoServico = s.codigo + 1;
        }
        raizServicos = inserirNo(raizServicos, s.codigo, s);
        System.out.println("Serviço indexado na Árvore com sucesso! Código: " + s.codigo);
    }

    public static void listarPecas() {
        if (raizPecas == null) {
            System.out.println("Nenhuma peça cadastrada na árvore.");
            return;
        }
        exibirEmOrdem(raizPecas); 
    }

    public static void listarServicos() {
        if (raizServicos == null) {
            System.out.println("Nenhum serviço cadastrado na árvore.");
            return;
        }
        exibirEmOrdem(raizServicos);
    }

    public static Peca buscarPeca(int codigo) {
        Object obj = buscarNo(raizPecas, codigo);
        if (obj instanceof Peca) {
            return (Peca) obj;
        }
        return null; 
    }

    public static Servico buscarServico(int codigo) {
        Object obj = buscarNo(raizServicos, codigo);
        if (obj instanceof Servico) {
            return (Servico) obj;
        }
        return null;
    }

    public static void atualizarPeca(int codigo, Peca novosDados) {
        Peca p = buscarPeca(codigo);
        if (p == null) {
            System.out.println("Erro: Peça não encontrada na árvore.");
            return;
        }
        p.nome = novosDados.nome;
        p.fabricante = novosDados.fabricante;
        p.precoCusto = novosDados.precoCusto;
        p.precoVenda = novosDados.precoVenda;
        p.quantidadeEstoque = novosDados.quantidadeEstoque;
        System.out.println("Peça atualizada com sucesso!");
    }

    public static void atualizarServico(int codigo, Servico novosDados) {
        Servico s = buscarServico(codigo);
        if (s == null) {
            System.out.println("Erro: Serviço não encontrado na árvore.");
            return;
        }
        s.descricao = novosDados.descricao;
        s.tempoEstimadoMinutos = novosDados.tempoEstimadoMinutos;
        s.valorMaoDeObra = novosDados.valorMaoDeObra;
        System.out.println("Serviço atualizado com sucesso!");
    }

    public static void removerPeca(int codigo) {
        System.out.println("Aviso: A remoção de nós na AVL será implementada via inativação lógica.");
    }
    
    public static void removerServico(int codigo) {
         System.out.println("Aviso: A remoção de nós na AVL será implementada via inativação lógica.");
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
            System.out.println("Erro: ja existe um registro com o id " + id + " na arvore.");
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
