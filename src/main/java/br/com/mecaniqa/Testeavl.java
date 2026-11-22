package br.com.mecaniqa;

import br.com.mecaniqa.entidades.Gerenciador;
import br.com.mecaniqa.nos.No;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Testeavl {

    private static No raizTeste = null;

    public static void main(String[] args) {

        System.out.println("=== QA-04: rotacao simples a direita ===");

        No tres = novoNo(3);
        tres.esquerda = novoNo(2);
        tres.esquerda.esquerda = novoNo(1);
        tres.esquerda.altura = 2;
        tres.altura = 3;
        System.out.println("Fator de balanceamento do no 3 antes de rotacionar: " + Gerenciador.fatorBalanceamento(tres));

        raizTeste = null;
        inserirTodos(3, 2, 1);
        verificar("raiz virou o no 2", raizTeste.id == 2);
        verificar("filho esquerdo e o 1", raizTeste.esquerda.id == 1);
        verificar("filho direito e o 3", raizTeste.direita.id == 3);
        verificar("altura da raiz e 2", raizTeste.altura == 2);
        verificar("fator da raiz e 0", Gerenciador.fatorBalanceamento(raizTeste) == 0);

        System.out.println();
        System.out.println("=== Rotacao simples a esquerda (1, 2, 3) ===");
        raizTeste = null;
        inserirTodos(1, 2, 3);
        verificar("raiz virou o no 2", raizTeste.id == 2);
        verificar("arvore valida", arvoreValida());

        System.out.println();
        System.out.println("=== Rotacao esquerda-direita (3, 1, 2) ===");
        raizTeste = null;
        inserirTodos(3, 1, 2);
        verificar("raiz virou o no 2", raizTeste.id == 2);
        verificar("arvore valida", arvoreValida());

        System.out.println();
        System.out.println("=== Rotacao direita-esquerda (1, 3, 2) ===");
        raizTeste = null;
        inserirTodos(1, 3, 2);
        verificar("raiz virou o no 2", raizTeste.id == 2);
        verificar("arvore valida", arvoreValida());

        System.out.println();
        System.out.println("=== 100000 ids em ordem crescente (pior caso da BST sem balanceamento) ===");
        raizTeste = null;
        int n = 100000;
        for (int i = 1; i <= n; i++) {
            raizTeste = inserirBalanceado(raizTeste, i, "registro " + i);
        }
        verificar("arvore valida (ordem, alturas e fatores)", arvoreValida());
        System.out.println("Altura da raiz: " + raizTeste.altura + " (uma lista encadeada teria " + n + ")");
        verificar("busca do id 77777", "registro 77777".equals(Gerenciador.buscarPorRaiz(raizTeste, 77777)));

        System.out.println();
        System.out.println("=== 5000 ids aleatorios ===");
        raizTeste = null;
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            ids.add(i * 3);
        }
        Collections.shuffle(ids, new Random(42));
        for (int id : ids) {
            raizTeste = inserirBalanceado(raizTeste, id, "registro " + id);
        }
        verificar("arvore valida", arvoreValida());
    }

    private static No novoNo(int id) {
        No no = new No();
        no.id = id;
        no.registro = "registro " + id;
        no.altura = 1;
        return no;
    }

    private static void inserirTodos(int... ids) {
        for (int id : ids) {
            raizTeste = inserirBalanceado(raizTeste, id, "registro " + id);
        }
    }

    private static No inserirBalanceado(No atual, int id, Object registro) {
        if (atual == null) {
            No novo = new No();
            novo.id = id;
            novo.registro = registro;
            novo.altura = 1;
            return novo;
        }

        if (id < atual.id) {
            atual.esquerda = inserirBalanceado(atual.esquerda, id, registro);
        } else if (id > atual.id) {
            atual.direita = inserirBalanceado(atual.direita, id, registro);
        } else {
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

    private static boolean arvoreValida() {
        return validar(raizTeste, Long.MIN_VALUE, Long.MAX_VALUE) >= 0;
    }

    private static int validar(No no, long minimo, long maximo) {
        if (no == null) {
            return 0;
        }
        if (no.id <= minimo || no.id >= maximo) {
            return -1;
        }
        int alturaEsquerda = validar(no.esquerda, minimo, no.id);
        int alturaDireita = validar(no.direita, no.id, maximo);
        if (alturaEsquerda < 0 || alturaDireita < 0) {
            return -1;
        }
        if (Math.abs(alturaEsquerda - alturaDireita) > 1) {
            return -1;
        }
        if (no.altura != 1 + Math.max(alturaEsquerda, alturaDireita)) {
            return -1;
        }
        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    private static void verificar(String descricao, boolean ok) {
        System.out.println((ok ? "[OK]    " : "[FALHOU] ") + descricao);
    }
}