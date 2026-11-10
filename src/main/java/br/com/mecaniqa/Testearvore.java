package br.com.mecaniqa;

import entidades.Cliente;
import entidades.Gerenciador;

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
            Gerenciador.inserir(id, c);
        }

        Cliente encontrado = (Cliente) Gerenciador.buscar(30);
        System.out.println("Buscando o id 30: " + encontrado.nome);
        System.out.println("Buscando o id 99 (nao existe): " + Gerenciador.buscar(99));

        System.out.println();
        System.out.println("=== Validacoes ===");
        Gerenciador.inserir(30, new Cliente());
        Gerenciador.inserir(80, null);

        System.out.println();
        System.out.println("=== Comparacao: br.com.mecaniqa.arvore x busca linear ===");

        int n = 100000;
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            lista.add(i);
        }
        Collections.shuffle(lista, new Random(42));

        Gerenciador.raiz = null;
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) {
            int id = lista.get(i);
            vetor[i] = id;
            Gerenciador.inserir(id, "registro " + id);
        }

        int buscas = 1000;
        Random sorteio = new Random(7);
        int[] alvos = new int[buscas];
        for (int i = 0; i < buscas; i++) {
            alvos[i] = lista.get(sorteio.nextInt(n));
        }

        long inicio = System.nanoTime();
        for (int alvo : alvos) {
            Gerenciador.buscar(alvo);
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

}
