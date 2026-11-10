package br.com.mecaniqa;

import grafo.Dijkstra;
import grafo.Grafo;
import grafo.MapaLogistico;

public class TesteGrafo {
    public static void main(String[] args) {
        System.out.println("=== TESTE OAT 4: MAPA LOGÍSTICO E DIJKSTRA ===");

        Grafo mapa = MapaLogistico.carregarGrafoDoCsv("rotas.br.com.mecaniqa.csv");

        if (mapa != null) {
            // Imprime o grafo para validar a mão dupla (ida e volta)
            mapa.imprimirGrafo(MapaLogistico.listaFiliais);

            // 2. Testa o Algoritmo de Dijkstra
            // Tenta calcular a rota do Centro de Distribuição até à Filial Tomba
            String origem = "Centro_Distribuicao";
            String destino = "Filial_Tomba";

            if (MapaLogistico.mapaFiliais.containsKey(origem) && MapaLogistico.mapaFiliais.containsKey(destino)) {
                int idOrigem = MapaLogistico.mapaFiliais.get(origem);
                int idDestino = MapaLogistico.mapaFiliais.get(destino);

                Dijkstra.calcularMenorCaminho(mapa, idOrigem, idDestino, MapaLogistico.listaFiliais);
            } else {
                System.out.println("Erro: Origem ou Destino não encontrados no mapa.");
            }

        } else {
            System.out.println("O grafo não foi criado. Verifique se o rotas.br.com.mecaniqa.csv existe na raiz do projeto.");
        }
    }
}