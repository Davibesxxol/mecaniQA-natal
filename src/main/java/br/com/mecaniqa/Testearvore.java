package br.com.mecaniqa;

import br.com.mecaniqa.entidades.Cliente;
import br.com.mecaniqa.entidades.Gerenciador;
import br.com.mecaniqa.nos.No;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Testearvore {

    public static void main(String[] args) {

        System.out.println("=== QA-02: busca otimizada ===");

        int[] ids = {50, 30, 70};
        for (int id : ids) {
            Cliente c = new Cliente();
            c.codigo = id;
            c.nome = "Cliente " + id;
            c.telefone = "75999999999";
            c.email = "cliente" + id + "@email.com";
            Gerenciador.raizClientes = inserirNoTeste(Gerenciador.raizClientes, id, c);
        }

        Cliente encontrado = (Cliente) Gerenciador.buscarPorRaiz(Gerenciador.raizClientes, 30);
        System.out.println("Buscando o id 30: " + (encontrado != null ? encontrado.nome : "null"));
        System.out.println("Buscando o id 99 (nao existe): " + Gerenciador.buscarPorRaiz(Gerenciador.raizClientes, 99));

        System.out.println();
        System.out.println("=== Validacoes ===");
        Gerenciador.raizClientes = inserirNoTeste(Gerenciador.raizClientes, 30, new Cliente());
        Gerenciador.raizClientes = inserirNoTeste(Gerenciador.raizClientes, 80, null);

        System.out.println();
        System.out.println("=== Comparacao: br.com.mecaniqa.arvore x busca linear ===");

        int n = 100000;
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            lista.add(i);
        }
        Collections.shuffle(lista, new Random(42));

        No raizTeste = null;
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) {
            int id = lista.get(i);
            vetor[i] = id;
            raizTeste = inserirNoTeste(raizTeste, id, "registro " + id);
        }

        int buscas = 1000;
        Random sorteio = new Random(7);
        int[] alvos = new int[buscas];
        for (int i = 0; i < buscas; i++) {
            alvos[i] = lista.get(sorteio.nextInt(n));
        }

        long inicio = System.nanoTime();
        for (int alvo : alvos) {
            Gerenciador.buscarPorRaiz(raizTeste, alvo);
        }
        long tempoArvore = System.nanoTime() - inicio;

        inicio = System.nanoTime();
        for (int alvo : alvos) {
            for (int i = 0; i < n; i++) {
                if (vetor[i] == alvo) {
                    break;
                }
            }
        }
        long tempoLinear = System.nanoTime() - inicio;

        System.out.println(buscas + " buscas em " + n + " registros:");
        System.out.println("Arvore:       " + tempoArvore / 1000 + " microssegundos");
        System.out.println("Busca linear: " + tempoLinear / 1000 + " microssegundos");
    }

    private static No inserirNoTeste(No atual, int id, Object registro) {
        if (atual == null) {
            No novo = new No();
            novo.id = id;
            novo.registro = registro;
            novo.altura = 1;
            return novo;
        }

        if (id < atual.id) {
            atual.esquerda = inserirNoTeste(atual.esquerda, id, registro);
        } else if (id > atual.id) {
            atual.direita = inserirNoTeste(atual.direita, id, registro);
        } else {
            System.out.println("Erro: ja existe um registro com o id " + id + " na br.com.mecaniqa.arvore.");
            return atual;
        }

        atual.altura = Gerenciador.calcularAltura(atual);
        int fator = Gerenciador.fatorBalanceamento(atual);

        if (fator > 1) {
            if (Gerenciador.fatorBalanceamento(atual.esquerda) < 0) {
                atual.esquerda = Gerenciador.rotacaoEsquerda(atual.esquerda);
            }
            return Gerenciador.rotacaoDireita(atual);
        }

        if (fator < -1) {
            if (Gerenciador.fatorBalanceamento(atual.direita) > 0) {
                atual.direita = Gerenciador.rotacaoDireita(atual.direita);
            }
            return Gerenciador.rotacaoEsquerda(atual);
        }

        return atual;
    }
}