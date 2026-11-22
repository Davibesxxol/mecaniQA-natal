package br.com.mecaniqa.grafo;

import br.com.mecaniqa.entidades.OrdemServico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

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

    public static void calcularMenorCaminho(Grafo mapa,
                                            int idOrigem,
                                            int idDestino,
                                            List<String> nomesFiliais) {
        calcularMenorCaminho(mapa, idOrigem, idDestino, nomesFiliais, null, null);
    }

    public static void calcularMenorCaminho(Grafo mapa,
                                            int idOrigem,
                                            int idDestino,
                                            List<String> nomesFiliais,
                                            Integer idOrdemServico,
                                            OrdemServico ordemServico) {
        int n = mapa.totalVertices;
        int[] distancias = new int[n];
        int[] predecessores = new int[n];
        boolean[] visitados = new boolean[n];

        for (int i = 0; i < n; i++) {
            distancias[i] = Integer.MAX_VALUE;
            predecessores[i] = -1;
            visitados[i] = false;
        }
        distancias[idOrigem] = 0;

        PriorityQueue<NoFila> fila = new PriorityQueue<>();
        fila.add(new NoFila(idOrigem, 0));

        while (!fila.isEmpty()) {
            NoFila atual = fila.poll();
            int u = atual.vertice;

            if (visitados[u]) {
                continue;
            }
            visitados[u] = true;

            if (u == idDestino) {
                break;
            }

            for (Aresta vizinho : mapa.listaAdjacencia.get(u)) {
                int v = vizinho.destino;
                int peso = vizinho.peso;

                if (!visitados[v]
                        && distancias[u] != Integer.MAX_VALUE
                        && distancias[u] + peso < distancias[v]) {
                    distancias[v] = distancias[u] + peso;
                    predecessores[v] = u;
                    fila.add(new NoFila(v, distancias[v]));
                }
            }
        }

        System.out.println("\n=== MOTOR DE ROTAS (DIJKSTRA) ===");
        if (distancias[idDestino] == Integer.MAX_VALUE) {
            System.out.println("Não existe rota disponível entre "
                    + nomesFiliais.get(idOrigem) + " e " + nomesFiliais.get(idDestino));
            return;
        }

        System.out.println("Custo Total Mínimo: " + distancias[idDestino] + " km");

        List<String> caminho = new ArrayList<>();
        int passoAtual = idDestino;
        while (passoAtual != -1) {
            caminho.add(nomesFiliais.get(passoAtual));
            passoAtual = predecessores[passoAtual];
        }
        Collections.reverse(caminho);

        System.out.println("Rota Exata de Entrega: " + String.join(" -> ", caminho));

        if (idOrdemServico != null) {
            System.out.println("\n--- MANIFESTO / ORDEM DE SERVIÇO (ÁRVORE AVL) ---");
            System.out.println("Código da OS consultada: " + idOrdemServico);
            if (ordemServico != null) {
                System.out.println("Status: " + ordemServico.status
                        + " | Serviços: " + ordemServico.quantidadeServicos()
                        + " | Valor total: R$" + ordemServico.valorTotal());
            } else {
                System.out.println("Ordem de Serviço não encontrada na Árvore AVL.");
            }
        }
    }
}