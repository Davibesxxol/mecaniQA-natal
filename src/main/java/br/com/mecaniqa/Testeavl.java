package br.com.mecaniqa;

import entidades.Gerenciador;
import nos.No;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Testeavl {

    public static void main(String[] args) {

        System.out.println("=== QA-04: rotacao simples a direita ===");

        No tres = novoNo(3);
        tres.esquerda = novoNo(2);
        tres.esquerda.esquerda = novoNo(1);
        tres.esquerda.altura = 2;
        tres.altura = 3;
        System.out.println("Fator de balanceamento do no 3 antes de rotacionar: " + Gerenciador.fatorBalanceamento(tres));

        Gerenciador.raiz = null;
        inserirTodos(3, 2, 1);
        verificar("raiz virou o no 2", Gerenciador.raiz.id == 2);
        verificar("filho esquerdo e o 1", Gerenciador.raiz.esquerda.id == 1);
        verificar("filho direito e o 3", Gerenciador.raiz.direita.id == 3);
        verificar("altura da raiz e 2", Gerenciador.raiz.altura == 2);
        verificar("fator da raiz e 0", Gerenciador.fatorBalanceamento(Gerenciador.raiz) == 0);

        System.out.println();
        System.out.println("=== Rotacao simples a esquerda (1, 2, 3) ===");
        Gerenciador.raiz = null;
        inserirTodos(1, 2, 3);
        verificar("raiz virou o no 2", Gerenciador.raiz.id == 2);
        verificar("br.com.mecaniqa.arvore valida", arvoreValida());

        System.out.println();
        System.out.println("=== Rotacao esquerda-direita (3, 1, 2) ===");
        Gerenciador.raiz = null;
        inserirTodos(3, 1, 2);
        verificar("raiz virou o no 2", Gerenciador.raiz.id == 2);
        verificar("br.com.mecaniqa.arvore valida", arvoreValida());

        System.out.println();
        System.out.println("=== Rotacao direita-esquerda (1, 3, 2) ===");
        Gerenciador.raiz = null;
        inserirTodos(1, 3, 2);
        verificar("raiz virou o no 2", Gerenciador.raiz.id == 2);
        verificar("br.com.mecaniqa.arvore valida", arvoreValida());

        System.out.println();
        System.out.println("=== 100000 ids em ordem crescente (pior caso da BST sem balanceamento) ===");
        Gerenciador.raiz = null;
        int n = 100000;
        for (int i = 1; i <= n; i++) {
            Gerenciador.inserir(i, "registro " + i);
        }
        verificar("br.com.mecaniqa.arvore valida (ordem, alturas e fatores)", arvoreValida());
        System.out.println("Altura da raiz: " + Gerenciador.raiz.altura + " (uma lista encadeada teria " + n + ")");
        verificar("busca do id 77777", "registro 77777".equals(Gerenciador.buscar(77777)));

        System.out.println();
        System.out.println("=== 5000 ids aleatorios ===");
        Gerenciador.raiz = null;
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            ids.add(i * 3);
        }
        Collections.shuffle(ids, new Random(42));
        for (int id : ids) {
            Gerenciador.inserir(id, "registro " + id);
        }
        verificar("br.com.mecaniqa.arvore valida", arvoreValida());
    }

    private static No novoNo(int id) {
        No no = new No();
        no.id = id;
        no.registro = "registro " + id;
        return no;
    }

    private static void inserirTodos(int... ids) {
        for (int id : ids) {
            Gerenciador.inserir(id, "registro " + id);
        }
    }

    private static boolean arvoreValida() {
        return validar(Gerenciador.raiz, Long.MIN_VALUE, Long.MAX_VALUE) >= 0;
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