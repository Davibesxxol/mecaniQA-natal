package br.com.mecaniqa.grafo;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
    // A lista principal onde o índice é o ID da filial
    // E o valor é uma lista de caminhos que saem dela
    public List<List<Aresta>> listaAdjacencia;
    public int totalVertices;

    public Grafo(int numeroVertices) {
        this.totalVertices = numeroVertices;
        listaAdjacencia = new ArrayList<>(numeroVertices);

        // Inicializa as listas de vizinhos vazias para cada filial
        for (int i = 0; i < numeroVertices; i++) {
            listaAdjacencia.add(new ArrayList<>());
        }
    }

    // Garante a Mão Dupla (Grafo Não-Direcionado) exigida no Barema
    public void adicionarAresta(int origem, int destino, int peso) {
        // Rota de Ida: Origem -> Destino
        listaAdjacencia.get(origem).add(new Aresta(destino, peso));

        // Rota de Volta: Destino -> Origem
        listaAdjacencia.get(destino).add(new Aresta(origem, peso));
    }

    public void imprimirGrafo(List<String> nomesFiliais) {
        System.out.println("\n--- Mapa Logístico (Grafo em Memória) ---");
        for (int i = 0; i < totalVertices; i++) {
            System.out.print(nomesFiliais.get(i) + " conecta com: ");
            for (Aresta aresta : listaAdjacencia.get(i)) {
                System.out.print("[" + nomesFiliais.get(aresta.destino) + " | " + aresta.peso + "km] ");
            }
            System.out.println();
        }
    }
}