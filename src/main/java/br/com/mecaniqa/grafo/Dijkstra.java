package br.com.mecaniqa.grafo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    // Classe auxiliar para a Tabela Heap (Fila de Prioridade)
    // Garante que o vértice mais próximo seja sempre processado primeiro (O(log V))
    static class NoFila implements Comparable<NoFila> {
        int vertice;
        int distancia;

        public NoFila(int vertice, int distancia) {
            this.vertice = vertice;
            this.distancia = distancia;
        }

        @Override
        public int compareTo(NoFila outro) {
            return Integer.compare(this.distancia, outro.distancia);
        }
    }

    public static void calcularMenorCaminho(Grafo mapa, int idOrigem, int idDestino, List<String> nomesFiliais) {
        int n = mapa.totalVertices;
        int[] distancias = new int[n];
        int[] predecessores = new int[n]; // Vetor para rastrear o caminho exato
        boolean[] visitados = new boolean[n];

        // 1. Inicialização: Tudo infinito, exceto a origem
        for (int i = 0; i < n; i++) {
            distancias[i] = Integer.MAX_VALUE;
            predecessores[i] = -1; // -1 significa que ainda não tem predecessor
            visitados[i] = false;
        }
        distancias[idOrigem] = 0;

        PriorityQueue<NoFila> fila = new PriorityQueue<>();
        fila.add(new NoFila(idOrigem, 0));

        // 2. Laço Principal (Processamento dos Vértices)
        while (!fila.isEmpty()) {
            NoFila atual = fila.poll();
            int u = atual.vertice;

            if (visitados[u]) continue;
            visitados[u] = true;

            // Otimização: Se já chegou ao destino final, pode parar a procura
            if (u == idDestino) break;

            // 3. Relaxamento das Arestas (Analisar vizinhos)
            for (Aresta vizinho : mapa.listaAdjacencia.get(u)) {
                int v = vizinho.destino;
                int peso = vizinho.peso;

                // Se achou um caminho mais curto para 'v' passando por 'u'
                if (!visitados[v] && distancias[u] != Integer.MAX_VALUE
                        && distancias[u] + peso < distancias[v]) {

                    distancias[v] = distancias[u] + peso;
                    predecessores[v] = u; // Regista de onde viemos
                    fila.add(new NoFila(v, distancias[v]));
                }
            }
        }

        // 4. Exibir Relatório Final no Ecrã
        System.out.println("\n=== MOTOR DE ROTAS (DIJKSTRA) ===");
        if (distancias[idDestino] == Integer.MAX_VALUE) {
            System.out.println("Não existe rota disponível entre " + nomesFiliais.get(idOrigem) + " e " + nomesFiliais.get(idDestino));
            return;
        }

        System.out.println("Custo Total Mínimo: " + distancias[idDestino] + " km");

        // 5. Reconstruir a Rota Exata (de trás para a frente)
        List<String> caminho = new ArrayList<>();
        int passoAtual = idDestino;
        while (passoAtual != -1) {
            caminho.add(nomesFiliais.get(passoAtual));
            passoAtual = predecessores[passoAtual];
        }
        Collections.reverse(caminho); // Inverte para ficar da Origem -> Destino

        System.out.println("Rota Exata de Entrega: " + String.join(" -> ", caminho));
    }
}